/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CASUAL;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Jeremy
 */
public class ElevateUAC {
    
    private static Log log = new Log();

    public static String batName = "elevate.bat";

    public static boolean checkForUAC() {
        File dummyFile = new File("C:/foo.bar"); // Windows default is drive C: I'll figure out how to determine it programatically later
        dummyFile.deleteOnExit();

        try {
            try (FileWriter fw = new FileWriter(dummyFile, true)) {
            }
        } catch (IOException ex) {//we cannot UAC must be on
            return true;
        }
        return false;
    }

    public void doElevate() {
        //create batch file in temporary directory as we have access to it regardless of UAC status
        File file = new File(Statics.TempFolder + batName);
        file.deleteOnExit();
        createBatchFile(file);
        runBatchFile();
    }

    private String getJarLocation() {
        return getClass().getProtectionDomain().getCodeSource().getLocation().getPath().substring(1);
    }

    private void runBatchFile() {

        Runtime runtime = Runtime.getRuntime();
        String[] cmd = new String[]{"cmd.exe", "/C", Statics.TempFolder + batName + " java -jar " + getJarLocation()};
        try {
            runtime.exec(cmd);
        } catch (Exception ex) {
            log.errorHandler(ex);
        }
    }

    private void createBatchFile(File file) {
        try {
            try (FileWriter fw = new FileWriter(file, true)) {
                fw.write(
                        "@echo Set objShell = CreateObject(\"Shell.Application\") > %temp%\\sudo.tmp.vbs\r\n"
                        + "@echo args = Right(\"%*\", (Len(\"%*\") - Len(\"%1\"))) >> %temp%\\sudo.tmp.vbs\r\n"
                        + "@echo objShell.ShellExecute \"%1\", args, \"\", \"runas\" >> %temp%\\sudo.tmp.vbs\r\n"
                        + "@cscript %temp%\\sudo.tmp.vbs\r\n"
                        + "del /f %temp%\\sudo.tmp.vbs\r\n");
            }
        } catch (IOException ex) {
            log.errorHandler(ex);
        }
    }
    
}