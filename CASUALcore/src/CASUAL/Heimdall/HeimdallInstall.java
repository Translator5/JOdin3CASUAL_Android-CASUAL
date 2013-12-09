/*
 * Copyright (c) 2011 Adam Outler
 * Permission is hereby granted, free of charge, to any person obtaining a copy 
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights 
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell 
 * copies of the Software, and to permit persons to whom the Software is 
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package CASUAL.Heimdall;

import CASUAL.CASUALMain;
import CASUAL.CASUALMessageObject;
import CASUAL.FileOperations;
import CASUAL.Log;
import CASUAL.OSTools;
import CASUAL.Shell;
import CASUAL.Statics;
import CASUAL.WindowsDrivers;
import CASUAL.network.CASUALUpdates;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Heimdall Install provides methods to install Heimdall under the CASUAL
 * environment
 *
 * @author Adam Outler adamoutler@gmail.com
 */
public class HeimdallInstall {
    /**
     * Heimdall version.
     */
    public static final String heimdallVersion = "140"; //primary version string
    public static String heimdallResource = ""; //location to heimdall set from final values above
    /**
     * heimdall for Debian Linux 64-bit.
     */
    public static final String heimdallLinuxamd64 = "/CASUAL/Heimdall/resources/heimdall_amd64.deb";
    /**
     * Heimdall for Windows.
     */
    public static final String heimdallWin = "/CASUAL/Heimdall/resources/heimdall.exe";
    /**
     * Heimdall for Debian Linux ARMv6.
     */
    public static final String heimdallLinuxARMv6 = "/CASUAL/Heimdall/resources/heimdall_armv6.deb";
    /**
     * Heimdall for Mac dmg file.
     */
    public static final String heimdallMac = "/CASUAL/Heimdall/resources/heimdall-mac.dmg";
    public static String heimdallDeployed = ""; //location of heimdall once deployed
    public static String heimdallStaging = Statics.getTempFolder() + "heimdallStage"; //location for heimdall files while deploying on Linux
    /**
     * Heimdall for Windows resource must be in same folder as exe.
     */
    public static final String heimdallWin2 = "/CASUAL/Heimdall/resources/libusb-1.0.dll";
    /**
     * heimdall for Debian Linux x86.
     */
    public static final String heimdallLinuxi386 = "/CASUAL/Heimdall/resources/heimdall_i386.deb";
    //heimdall
    //TODO: remove all thse and Heimdall Install/HeimdallTools with getters set by, or triggering installation ;
    public static boolean isHeimdallDeployed = false; //if fastboot has been deployed
    /**
     * Heimdall for Windows resource must be in same folder as exe.
     */
    public static final String msvcr110dll = "/CASUAL/Heimdall/resources/msvcr110.dll";
    /**
     * Heimdall for Windows resource must be in same folder as exe.
     */
    public static final String msvcp110dll = "/CASUAL/Heimdall/resources/msvcp110.dll";
    public static String[] installedHeimdallVersion; //attempt to get from running heimdall blindly, then .replace("v","").split(.)
    public static String[] resourceHeimdallVersion; //get resource version[] from "/CASUAL/Heimdall/resources/HeimdallVersion".replace("v","").split(.) ;

    final String[] WindowsDriverBlanket = {"18D1", "04E8", "0B05", "0BB4", "22B8", "054C", "2080"};
    /**
     * Vendor ID detected
     */
    public String VID = "";
    /**
     * Device ID detected
     */
    public String PID = "";

    /**
     * deploys heimdal
     *
     * @return true if deployed
     */
    public boolean deployHeimdallForWindows() {
        FileOperations fo = new FileOperations();
        heimdallResource = heimdallWin2;
        fo.copyFromResourceToFile(heimdallResource, Statics.getTempFolder() + "libusb-1.0.dll");
        heimdallResource = heimdallWin;
        heimdallDeployed = Statics.getTempFolder() + "heimdall.exe";
        fo.copyFromResourceToFile(heimdallResource, heimdallDeployed);
        fo.copyFromResourceToFile(msvcp110dll, Statics.getTempFolder() + "msvcp110.dll");
        fo.copyFromResourceToFile(msvcr110dll, Statics.getTempFolder() + "msvcr110.dll");

        log.level4Debug("deployHeimdallForWindows- verifying Heimdall deployment");
        if (checkHeimdall()) { //try with redist files
            isHeimdallDeployed = true;
            log.level4Debug("heimdall install sucessful");
            return true;
        }

        log.level4Debug("Verifying Heimdall deployment after Visual C++ Redistributable installation");
        if (checkHeimdall()) {
            log.level0Error("@heimdallCouldNotBeDeployed");
            return false;
        } else {
            log.level4Debug("heimdall install sucessful");
            return true;
        }
    }

    /**
     * checks if heimdall is deployed
     *
     * @return true if heidmall version returns anything
     */
    public boolean checkHeimdall() {
        boolean retval = !new Shell().silentShellCommand(new String[]{HeimdallTools.getHeimdallCommand(), "version"}).equals("");
        return retval;
    }

    private boolean installLinuxHeimdall() {

        FileOperations fo = new FileOperations();
        String arch = OSTools.checkLinuxArch();
//Linux64
        if (arch.contains("x86_64")) {
            heimdallResource = heimdallLinuxamd64;
            fo.copyFromResourceToFile(heimdallResource, heimdallStaging);
            fo.setExecutableBit(heimdallStaging);
            shell.elevateSimpleCommandWithMessage(new String[]{"dpkg", "-i", heimdallStaging}, "Permissions escillation required to install Heimdall");
            heimdallDeployed = "heimdall";
            if (checkHeimdallVersion()) {
                return true;
            } else {
                heimdallDeployed = "";
                return false;
            }
//Linux32
        } else if (arch.contains("i686")) {
            heimdallResource = heimdallLinuxi386;
            fo.copyFromResourceToFile(heimdallResource, heimdallStaging);
            fo.setExecutableBit(heimdallStaging);
            shell.elevateSimpleCommandWithMessage(new String[]{"dpkg", "-i", heimdallStaging}, "Install Heimdall");
            heimdallDeployed = "heimdall";
            if (checkAndDeployHeimdall()) {
                return true;
            } else {
                heimdallDeployed = "";
                return false;
            }
        } else {
            new Log().level0Error("@incompatibleWithHeimdal");
            return false;
        }

//Windows
    }
    FileOperations FileOperations = new FileOperations();
    Log log = new Log();
    Shell shell = new Shell();

    /**
     * installs heimdall
     *
     * @return true if heimdall was detected
     */
    public boolean installHeimdall() {
        //if ( installedHeimdallVersion.length==2 && REGEX FOR STRING NUMBERS ONLY){ isHeimdallDeployed=true;
        if (isHeimdallDeployed) { //if heimdall is installed, return true
            return true;
        } else { //attempt to correct the issue
            if (OSTools.isLinux()) {
                return installLinuxHeimdall();
            } else if (OSTools.isWindows()) {
                if (checkHeimdallVersion()) {
                    return true;
                } else {
                    if (checkAndDeployHeimdall()) {
                        return true;
                    }
                    heimdallDeployed = "";
                    return false;
                }

                //Mac          
            } else if (OSTools.isMac()) {

                heimdallDeployed = HeimdallTools.getHeimdallCommand();
                String retval = new Shell().silentShellCommand(new String[]{HeimdallTools.getHeimdallCommand()});
                if (retval.contains("CritError!!!")) {
                    installHeimdallMac();
                }
                if (checkHeimdallVersion()) {
                    return true;
                } else {
                    heimdallDeployed = "";
                    return false;
                }
            }
        }
        return true;
    }

    private void installHeimdallMac() {
        if (OSTools.isMac()) {
            String exec = "";
            try {
                exec = new CASUALUpdates().CASUALRepoDownload("https://android-casual.googlecode.com/svn/trunk/repo/heimdall.properties");

            } catch (FileNotFoundException ex) {
                new Log().errorHandler(ex);
            } catch (InterruptedException ex) {
                new Log().errorHandler(ex);
            } catch (IOException ex) {
                log.errorHandler(ex);
            }
            new Shell().liveShellCommand(new String[]{"open", "-W", exec}, true);
            new CASUALMessageObject("@interactionUnplugItAndPlugItBackIn").showErrorDialog();
        }
    }

    /**
     * Installs windows drivers
     *
     * @return always returns true
     * @WTF always returns true?
     */
    public boolean installWindowsDrivers() {
        //install drivers
        //CASUALJFrameWindowsDriverInstall HID = new CASUALJFrameWindowsDriverInstall();
        //HID.setVisible(true);
        /*
         * @WTF
         */
        new WindowsDrivers(0).installDriverBlanket(null);
        return true;
        /*log.level2Information("@installingCADI"); //Add Newline
         new Log().level3Verbose("Driver Problems suck. Lemme make it easy.\n"
         + "We're going to install drivers now.  Lets do it.\n"
         + "THIS PROCESS CAN TAKE UP TO 5 MINTUES.\nDURING THIS TIME YOU WILL NOT SEE ANYTHING.\nBE PATIENT!");

         String exec = "";
         try {
         if (new FileOperations().verifyResource(Statics.WinDriverResource)) {
         exec = Statics.TempFolder + "CADI.exe";
         new FileOperations().copyFromResourceToFile(Statics.WinDriverResource, exec);
         } else {
         exec = new CASUALUpdates().CASUALRepoDownload("https://android-casual.googlecode.com/svn/trunk/repo/driver.properties");
         }
         } catch (IOException | InterruptedException ex) {
         log.level0Error("@problemWithOnlineRepo");
         }
         //verify MD5
         String driverreturn = new Shell().sendShellCommand(new String[]{"cmd.exe", "/C", "\"" + exec + "\""});*/
        /*
         * 
         * TODO: Here we need to parse return from CADI
         * 
         * Access is denied is likely a non-priviliged account otherwise access would be granted.
         * 
         * 
         * UNSUPPORTED DEVICE for Galaxy S1  Loops forever   Why is GS1 not supported?
         [DEBUG]deployHeimdallForWindows- verifying Heimdall deployment
         [DEBUG]heimdall install sucessful
         Waiting for Downoad Mode device...[VERBOSE]detected!
         [INFO]Executing Heimdall command.
         [VERBOSE]Performing standard Heimdall commandclose-pc-screen
         [DEBUG]###executing real-time command: C:\Users\adam\AppData\Local\Temp\adamTEMPCASUAL6EDFD949\heimdall.exe###
         Heimdall v1.4.0

         Copyright (c) 2010-2013, Benjamin Dobell, Glass Echidna
         http://www.glassechidna.com.au/

         This software is provided free of charge. Copying and redistribution is
         encouraged.

         If you appreciate this software and you would like to support future
         development please consider donating:
         http://www.glassechidna.com.au/donate/

         Initialising connection...
         Detecting device...
         ERROR: Failed to access device. libusb error: -12
         [ERROR]
         Drivers are Required Launching CADI.
         CASUAL Automated Driver Installer by jrloper.
         Installing Drivers now
         [VERBOSE]Driver Problems suck. Lemme make it easy.
         We're going to install drivers now.  Lets do it.
         THIS PROCESS CAN TAKE UP TO 5 MINTUES.
         DURING THIS TIME YOU WILL NOT SEE ANYTHING.
         BE PATIENT!
         [DEBUG]Attempting to write C:\Users\adam\AppData\Local\Temp\adamTEMPCASUAL6EDFD949\CADI.exe
         [DEBUG]File verified.
         [DEBUG]###executing: cmd.exe###
         [INFO]

         [INFO]
         [Heimdall Error Report] Detected:
         'LIBUSB_ERROR_NOT_SUPPORTED'; Attempting to continue
         [/Heimdall Error Report]


         [VERBOSE]Performing standard Heimdall commandclose-pc-screen
         [DEBUG]###executing real-time command: C:\Users\adam\AppData\Local\Temp\adamTEMPCASUAL6EDFD949\heimdall.exe###
         Heimdall v1.4.0
         ....
         * ...
         * ...

         [ERROR]Maximum retries exceeded. Shutting down Parser.
         [DEBUG]HALT RECEIVED    * 
         * 
         */
        /*log.level2Information(driverreturn);
         if (driverreturn.contains("CritError")) {
         return false;
         } else {
         return true;
         }*/
    }

    /**
     * displays a message to the user that Windows permissions were not
     * obtainable
     */
    public void displayWindowsPermissionsMessageAndExit() {
        if (OSTools.isWindows()) {
            new CASUALMessageObject("@interactionwindowsRunAsMessage" + getClass().getProtectionDomain().getCodeSource().getLocation().getPath().toString()).showErrorDialog();
        }
        CASUALMain.shutdown(0);
    }

    void runWinHeimdallInstallationProcedure() {
        installWindowsDrivers();
    }

    /**
     * checks and deploys heimdall
     *
     * @return true if deployed
     */
    public boolean checkAndDeployHeimdall() {

        //deploys heimdall for Windows, launches checks for all other OS's. 
        if (isHeimdallDeployed) {
            return true;
        } else {
            if (OSTools.isWindows()) {
                return deployHeimdallForWindows();
            } else {

                if (checkHeimdallVersion()) {
                    return true;
                } else {
                    return installHeimdall();
                }

            }
        }
    }

    /**
     * checks the heimdall version against version expected from Statics
     *
     * @return true if version is good
     */
    public boolean checkHeimdallVersion() {
        String heimdallCommand;
        if (heimdallDeployed.equals("")) {
            heimdallCommand = HeimdallTools.getHeimdallCommand();
        } else {
            heimdallCommand = heimdallDeployed;
        }
        String[] command = {heimdallCommand, "version"};
        String Version = new Shell().silentShellCommand(command);
        if (!Version.contains("CritError!!!")) {
            Version = Version.replaceAll("\n", "").replaceAll("v", "");
            if (Version.contains(" ")) {
                Version = Version.split(" ")[0];
            }
            Version = Version.replaceAll("\\.", "");
            if (Version.length() == 2) {
                Version = Version + 0;
            }
        } else {
            return false;
        }
        char[] digits = Version.toCharArray();
        int commandLineVersion = Integer.parseInt(new String(digits));
        int resourceVersion = Integer.parseInt(heimdallVersion);

        if (commandLineVersion >= resourceVersion) {
            heimdallDeployed = heimdallCommand;
            isHeimdallDeployed = true;
            return true;
        } else {
            return false;
        }
    }
}