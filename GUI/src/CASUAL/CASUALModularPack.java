/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CASUAL;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipException;

/**
 *
 * @author adam
 */

public class CASUALModularPack {

        void loadCASUALPackFileForCommandLineOnly(String pack) {
        Unzip unzip = new Unzip();
        String activeScript="";    
        try {
            //todo start adb deployment thread
            File f=new File(pack);
            if ( ! f.exists()){
                new Log().level0("File Not Found "+pack); 
                System.exit(1);
            } else {
                System.out.println("-----CASPAC MODE-----\nCASPAC: "+f.getAbsolutePath());
            }
            //begin unziping CASPAC
            Enumeration zippedFiles =unzip.getZipFileEntries(f);
            while (zippedFiles.hasMoreElements()){
                Object entry = zippedFiles.nextElement(); //get the object and begin examination
                if (entry.toString().equals("-build.properties")){
                    new CASUALPackageData().setPropertiesFromInputStream(unzip.streamFileFromZip(f, entry));
                } else if (entry.toString().equals("-Overview.txt")){
                    System.out.print("\n"+new FileOperations().readTextFromStream(unzip.streamFileFromZip(f, entry))+"\n");
                    //TODO: allow specification of which script here and take no action unless specified script is called. 
                } else if (entry.toString().endsWith(".scr")){
                    Statics.ScriptLocation=Statics.TempFolder+StringOperations.replaceLast(entry.toString(),".scr", "")+Statics.Slash;
                    activeScript=Statics.ScriptLocation+".scr";
                    new FileOperations().makeFolder(Statics.ScriptLocation);
                    new FileOperations().writeStreamToFile(unzip.streamFileFromZip(f, entry),Statics.ScriptLocation+entry.toString());
                } else if (entry.toString().endsWith(".zip")){
                    Statics.ScriptLocation=Statics.TempFolder+ StringOperations.replaceLast(entry.toString(),".zip", "")+Statics.Slash;
                    new FileOperations().makeFolder(Statics.ScriptLocation);
                    unzip.unZipInputStream(unzip.streamFileFromZip(f, entry),Statics.ScriptLocation);
                } else if (entry.toString().endsWith(".txt")){
                    System.out.print("\n"+new FileOperations().readTextFromStream(unzip.streamFileFromZip(f, entry))+"\n");
                }
                //DONE with extraction and preparation of zip
            }
            //TODO join adb thread
            //TODO pump InputStream(activeScript) into CASUALScriptParser
            
            System.exit(0);
        } catch (ZipException ex) {
            new Log().errorHandler(ex);
            new Log().level0("Zip File is corrupt. cannot continue.");
            System.exit(1);
        } catch (IOException ex) {
            new Log().errorHandler(ex);
            new Log().level0("There was a problem reading the file.");
            System.exit(1);        
        }
        
        System.out.println();
    }
        
        
        
}
