/*UnZip unzips files
 *Copyright (C) 2013  Adam Outler
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package CASUAL;

import CASUAL.Log;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 *
 * @author adam
 */
public class Unzip {

    static int BUFFER = 4096;
    final ZipFile zip;
    /**
     *
     */
    public Enumeration<? extends ZipEntry> zipFileEntries;

    /**
     * Unzip class is used to create a wrapper for unziping .zip files.
     * <p>
     * The File f will be converted to a ZipFile, and all other operations will
     * be preformed on this ZipFile.
     *
     * @param f java file object to be unziped.
     * @throws ZipException
     * @throws IOException
     * @see ZipFile
     */
    public Unzip(File f) throws ZipException, IOException {
        this.zip = new ZipFile(f);
        try {
            this.zipFileEntries = zip.entries();
        } catch (Exception e) {
            new Log().errorHandler(e);
        }
    }

    /**
     * Unzip class is used to create a wrapper for unziping .zip files.
     * <p>
     * The String f will be converted into a file, then that file will be
     * converted into a ZipFile.
     *
     * @param f String location of file to be unziped.
     * @throws ZipException
     * @throws IOException
     * @see ZipFile
     */
    public Unzip(String f) throws ZipException, IOException {
        this.zip = new ZipFile(new File(f));
        try {
            this.zipFileEntries = zip.entries();
        } catch (Exception e) {
            new Log().errorHandler(e);
        }
    }


    /**
     * Unzips the ZipFile that was specified in the constructor of the class.
     *
     * @param outputFolder folder to be unzipped to
     * @throws ZipException
     * @throws IOException
     * @see CASUAL.Unzip#Unzip(File)
     */
    public void unzipFile(String outputFolder) throws ZipException, IOException {
        unzipFileToFolder(outputFolder);
    }

    private void unzipFileToFolder(String outputFolder) throws ZipException, IOException {
        new Log().level4Debug("Unzipping " + zip.toString());

        String newPath = outputFolder + System.getProperty("file.separator");
        new File(newPath).mkdir();
        zipFileEntries = zip.entries();
        // Process each entry
        while (zipFileEntries.hasMoreElements()) {
            // grab a zip file entry
            ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();
            String currentEntry = entry.getName();
            File destFile = new File(newPath, currentEntry);
            //destFile = new File(newPath, destFile.getName());
            File destinationParent = destFile.getParentFile();
            // create the parent directory structure if needed
            destinationParent.mkdirs();
            if (!entry.isDirectory()) {
                new Log().level3Verbose("unzipping " + entry.toString());
                writeFromZipToFile(zip, entry, newPath);
            } else if (entry.isDirectory()) {
                new Log().level4Debug(newPath + entry.getName());
                new File(newPath + entry.getName()).mkdirs();
            }
        }
    }

    /**
     * Unzips a resource.
     * <p>
     * Within a java package there is a folder called resources, used to store
     * things such as internalized strings, sounds, and other important static
     * files. This function takes in the name of the resource and then outputs
     * it into the output folder.
     *
     * @param zipResource name of the java resource to be unzipped
     * @param outputFolder folder to unzip to
     * @throws FileNotFoundException
     * @throws IOException
     * @see java.lang.Class#getResource(String)
     *
     */
    public static void unZipResource(String zipResource, String outputFolder) throws FileNotFoundException, IOException {
        InputStream zStream;
        zStream = new CASUALMain().getClass().getResourceAsStream(zipResource);
        unZipInputStream(zStream, outputFolder);
        zStream.close();
    }

    /**
     * Unzips an InputStream.
     * <p>
     * Takes in an InputStream, converts it to a ZipInputStream, and then
     * iterates through all of the ZipEntries, writing each one of them to a
     * file with the name provided by the ZipEntry.
     *
     * @param zStream input stream to unzip
     * @param outputFolder output folder to unzip to
     * @throws FileNotFoundException
     * @throws IOException
     * @see InputStream
     * @see ZipEntry
     * @see ZipInputStream
     * @see ZipFile
     */
    public static void unZipInputStream(InputStream zStream, String outputFolder) throws FileNotFoundException, IOException {

        ZipInputStream ZipInput;
        ZipInput = new ZipInputStream(zStream);
        ZipEntry ZipEntryInstance;
        while ((ZipEntryInstance = ZipInput.getNextEntry()) != null) {
            new Log().level3Verbose("Unzipping " + ZipEntryInstance.getName());
            File EntryFile = new File(outputFolder + System.getProperty("file.separator") + ZipEntryInstance.getName());
            if (ZipEntryInstance.isDirectory()) {
                EntryFile.mkdirs();
                continue;
            }
            File EntryFolder = new File(EntryFile.getParent());
            if (!EntryFolder.exists()) {
                EntryFolder.mkdirs();
            }
            int currentByte;
            // establish buffer for writing file
            byte data[] = new byte[BUFFER];
            String currentEntry = ZipEntryInstance.getName();
            File DestFile = new File(outputFolder + System.getProperty("file.separator"), currentEntry);
            FileOutputStream FileOut = new FileOutputStream(DestFile);
            BufferedInputStream BufferedInputStream = new BufferedInputStream(ZipInput);
            BufferedOutputStream Destination;
            Destination = new BufferedOutputStream(FileOut);
            int numberOfCycles=(int)(ZipEntryInstance.getSize()/BUFFER);
            boolean updateGUI=false;
            if (Statics.GUI!=null && Statics.guiReady){
                updateGUI=true;
                Statics.GUI.setProgressBarMax(numberOfCycles);
            }
            int currentCycle=0;
            while ((currentByte = BufferedInputStream.read(data, 0, BUFFER)) != -1) {
                Destination.write(data, 0, currentByte);
                if (updateGUI) Statics.GUI.setProgressBar(++currentCycle);
            }
            Destination.flush();
            Destination.close();
        }
        new Log().level3Verbose("Unzip Complete");
    }

    /**
     * Closes the zip file
     * <p>
     * Should be called after all file operations have been completed in Unzip
     */
    public void close() {
        try {
            zip.close();
        } catch (IOException ex) {
            new Log().errorHandler(ex);
        }
    }

    /**
     * Deploys a single file from a zip.
     * <p>
     * Takes in an ZipEntry, and writes that single zip entry out to a folder
     *
     * @param entry entry file to deploy
     * @param outputFolder folder to be deployed to
     * @return location of entry deployed
     * @throws ZipException
     * @throws IOException
     */
    public String deployFileFromZip(Object entry, String outputFolder) throws ZipException, IOException {

        ZipEntry zipEntry = new ZipEntry((ZipEntry) entry);
        writeFromZipToFile(zip, zipEntry, outputFolder);
        zip.close();
        return outputFolder + entry.toString();
    }

    /**
     * Gets a stream of a specified file from a zip.
     * <p>
     * Static method used to stream a file form a zip that is not an Unzip
     * object.
     *
     * @param zipFile file to stream from
     * @param entry entry to stream
     * @return stream of file
     * @throws ZipException
     * @throws IOException
     */
    public static BufferedInputStream streamFileFromZip(File zipFile, Object entry) throws ZipException, IOException {
        ZipFile zip = new ZipFile(zipFile);
        return new BufferedInputStream(zip.getInputStream((ZipEntry) entry));
    }

    private void writeFromZipToFile(ZipFile zip, ZipEntry entry, String filePathToWrite) throws IOException, FileNotFoundException {
        //if (Static)
        BufferedInputStream is;
        is = new BufferedInputStream(zip.getInputStream(entry));
        int currentByte;
        // establish buffer for writing file
        byte data[] = new byte[BUFFER];
        // write the current file to disk
        FileOutputStream fos = new FileOutputStream(new File(filePathToWrite + entry));
        BufferedOutputStream dest;
        dest = new BufferedOutputStream(fos, BUFFER);
        // read and write until last byte is encountered
        while ((currentByte = is.read(data, 0, BUFFER)) != -1) {
            dest.write(data, 0, currentByte);
        }
        dest.flush();
        dest.close();
        is.close();
    }

    /**
     * Retrieves a BufferedInputStream for a specific zip entry in a file.
     *
     * @param entry ZipEntry that is to be pulled from ZipFile to Stream.
     * @return BufferedInputStream of the specified ZipEntry.
     * @throws ZipException
     * @throws IOException
     * @see ZipEntry
     * @see ZipFile
     * @see BufferedInputStream
     */
    public BufferedInputStream streamFileFromZip(Object entry) throws ZipException, IOException {
        return new BufferedInputStream(zip.getInputStream((ZipEntry) entry));
    }

    /**
     * Takes in a ZipEntry as an object and returns the string of the
     * corresponding file name for the entry.
     *
     * @param entry the ZipEntry Object
     * @return name of the file contained in the
     * @see ZipEntry#getName()
     */
    public String getEntryName(Object entry) {
        ZipEntry zipEntry = (ZipEntry) entry;
        String name = zipEntry.getName();
        return name;

    }

    /**
     * Takes in an ZipEntry as an object and returns the ZipEntry for the
     * Object.
     *
     * @param entry the ZipEntry Object
     * @return the ZipEntry
     * @see ZipEntry
     */
    public ZipEntry getEntry(Object entry) {
        return (ZipEntry) entry;
    }
}
