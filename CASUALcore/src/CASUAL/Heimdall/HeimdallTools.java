/*HeimdallTools provides tools for use with Heimdall
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
package CASUAL.Heimdall;

import CASUAL.CASUALMessageObject;
import CASUAL.CASUALScriptParser;
import CASUAL.Log;
import CASUAL.OSTools;
import CASUAL.Shell;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 * Provides tools to work with Heimdall in CASUAL.
 *
 * @author Adam Outler adamoutler@gmail.com
 * @author Jeremy Loper jrloper@gmail.com
 */
public class HeimdallTools {

    Shell shell = new Shell();
    Log log = new Log();
    int heimdallRetries = 0;

    /**
     * Status for decision making based on return value from heimdall.
     *
     * SUCCESS: Heimdall executed sucessfully
     *
     * HALTED: Heimdall encountered a failure which cannot be recovered.
     *
     * CONTINUE: Heimdall did not execute sucessfully, but we can try again.
     *
     * MAXIMUMRETRIESEXCEEDED: We've tried to continue four times now.
     *
     */
    private enum STATUS {

        SUCCESS, CONTINUE, HALTED, MAXIMUMRETRIESEXCEEDED
    }

    /**
     * do nothing until a heimdall device is detected
     */
    public void doHeimdallWaitForDevice() {
        String detectCommand[] = new String[]{HeimdallTools.getHeimdallCommand(), "detect"};

        log.level2Information("@waitingForDownload");
        String shellReturn = "";
        Timer connectionTimer = new Timer(60000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                new CASUALMessageObject("@interactionDownloadModeNotDetected").showTimeoutDialog(60, null, javax.swing.JOptionPane.OK_OPTION, 2, new String[]{"I did it"}, 0);
            }
        });
        connectionTimer.start();
        //Start timer  wait(90000) and recommend changing USB ports
        while (!shellReturn.contains("Device detected")) {
            log.progress(".");
            sleepForOneSecond();
            shellReturn = shell.silentShellCommand(detectCommand);
        }
        connectionTimer.stop();
        if (OSTools.isWindows()) {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException ex) {
            }
        }
        log.level3Verbose("detected!");
    }

    /**
     * performs an elevated heimdall command
     *
     * @param command heimdall command to be executed
     * @return result from heimdall
     */
    private String doElevatedHeimdallShellCommand(String[] command) {
        log.level4Debug("Executing ELEVATED Heimdall Command:  " + displayArray(command));
        String returnval = shell.elevateSimpleCommandWithMessage(command, "CASUAL uses root to work around Heimdall permissions.  Hit cancel if you know root is not required to access your device.");
        return returnval;
    }

    private String[] appendHeimdallCommandToStartOfArray(String[] command) {
        String[] cmd = new String[command.length + 1];
        //insert heimdall command at position 0
        cmd[0] = getHeimdallCommand();
        System.arraycopy(command, 0, cmd, 1, command.length);
        return cmd;
    }

    /**
     * performs a heimdall command
     *
     * @param command value from heimdall command
     * @return
     */
    public String doHeimdallShellCommand(String[] command) {
        log.level4Debug("Executing " + displayArray(command));
        command = appendHeimdallCommandToStartOfArray(command);
        int timesRun = 0;
        String shellRead; //value from command 
        String returnValue = ""; //concatinated shellRead values from repeats
        //make 4 attempts
        while (timesRun <= 4) {
            //we don't elevate Windows or mac.  
            //Mac should not need it as an installer is run with a kext included
            //Windows uses a driver
            if (timesRun < 1 || OSTools.isWindows() || OSTools.isMac()) {
                shellRead = shell.liveShellCommand(command, true);

            } else { // three attempts elevated for Linux LibUSB interface.
                shellRead = doElevatedHeimdallShellCommand(command);
            }
            //append last read value to return value and increment retries
            returnValue = returnValue + shellRead;
            timesRun++;
            //get the status
            String result = checkHeimdallErrorStatus(shellRead);
            STATUS status = processStatusFromResult(result, timesRun);

            //make a decision based on status result
            switch (status) {
                case SUCCESS: //great
                    log.level2Information("@heimdallWasSucessful");
                    return returnValue;
                case HALTED: //Epic heimdall failure
                    doErrorReport(command, result);
                    return returnValue;
                case MAXIMUMRETRIESEXCEEDED: //This has looped 4 times.
                    log.level0Error("@maximumRetries");
                    doErrorReport(command, result);
                    //TODO: uninstall drivers, reinstall with CADI and try once more.
                    new CASUALScriptParser().executeOneShotCommand("$HALT $ECHO cyclic error.");
                    return returnValue;
                case CONTINUE: //doover

                    break;
                default: //this should never happen
                    break;
            }
        }
        return returnValue;

    }

    private STATUS processStatusFromResult(String result, int retries) {

        if (retries == 4) {
            return STATUS.MAXIMUMRETRIESEXCEEDED;
        }
        if (result.contains("Script halted")) {
            return STATUS.HALTED;
        } else if (result.equals("")) {
            return STATUS.SUCCESS;
        } else if (result.contains("; Attempting to continue")) {
            return STATUS.CONTINUE;
        }
        return STATUS.CONTINUE;

    }

    private void doErrorReport(String[] command, String result) {
        log.level0Error("@heimdallErrorReport");
        log.level0Error(displayArray(command));
        log.level0Error("@heimdallErrorReport");
        log.level0Error(result);
        log.level0Error("@heimdallErrorReport");
        CASUALScriptParser cLang = new CASUALScriptParser();
        cLang.executeOneShotCommand("$HALT $SENDLOG");
    }

    /**
     * checks if Heimdall threw an error
     *
     * @param logFromHeimdallCommand CASUAL log output
     * @return containing halted if cannot continue or continue if it can
     *
     * @author Jeremy Loper jrloper@gmail.com
     */
    public String checkHeimdallErrorStatus(String logFromHeimdallCommand) {

        if (logFromHeimdallCommand.startsWith("Usage:")) {
            return "invalid command; Attempting to continue for test purposes";
        }
        for (String code : epicFailures) {
            if (logFromHeimdallCommand.contains(code)) {
                return "Heimdall epic uncontinuable error; Script halted";
            }
        }

        //Critical Failure, stop
        for (String code : errFail) { //halt
            if (logFromHeimdallCommand.contains(code)) {
                if (heimdallRetries <= 3) {  //only loop thrice
                    new CASUALMessageObject("@interactionRestartDownloadMode").showActionRequiredDialog();
                    return "Heimdall continuable error; Attempting to continue";
                } else {
                    return "Heimdall uncontinuable error; Script halted";
                }
            }

        }

        if (logFromHeimdallCommand.contains("Failed to detect compatible download-mode device")) {
            if (new CASUALMessageObject("@interactionUnableToDetectDownloadMode").showUserCancelOption() == 0) {
                return "Heimdall uncontinuable error; Script halted";
            }
            return "Heimdall continuable error; Attempting to continue";
        }

        if (logFromHeimdallCommand.contains(" failed!")) {
            if (logFromHeimdallCommand.contains("Claiming interface failed!")) {
                new CASUALMessageObject(null, "@interactionRestartDownloadMode").showActionRequiredDialog();
                return "Heimdall failed to claim interface; Attempting to continue";
            }

            if (logFromHeimdallCommand.contains("Setting up interface failed!")) {
                return "Heimdall failed to setup an interface; Attempting to continue";
            }

            if (logFromHeimdallCommand.contains("Protocol initialisation failed!")) {
                CASUALScriptParser cLang = new CASUALScriptParser();
                cLang.executeOneShotCommand("$HALT $ECHO A random error occurred while attempting initial communications with the device.\nYou will need disconnect USB and pull your battery out to restart your device.\nDo the same for CASUAL.");
                return "Heimdall failed to initialize protocol; Attempting to continue";
            }

            if (logFromHeimdallCommand.contains("upload failed!")) {
                return "Heimdall failed to upload; Attempting to continue";
            }
        }

        if (logFromHeimdallCommand.contains("Flash aborted!")) {
            return "Heimdall aborted flash; Attempting to continue";
        }

        if (logFromHeimdallCommand.contains("libusb error")) {
            int startIndex = logFromHeimdallCommand.lastIndexOf("libusb error");
            if (logFromHeimdallCommand.charAt(startIndex + 1) == ':') {
                startIndex = +3;
            }
            while (logFromHeimdallCommand.charAt(startIndex) != '\n') {
                if (logFromHeimdallCommand.charAt(startIndex) == '-') {
                    switch (logFromHeimdallCommand.charAt(startIndex + 1)) {
                        case '1': {
                            switch (logFromHeimdallCommand.charAt(startIndex + 2)) {
                                case '0': {// -10
                                    return "'LIBUSB_ERROR_INTERRUPTED' Error not handled; Attempting to continue";
                                }
                                case '1': {// -11
                                    return "'LIBUSB_ERROR_NO_MEM' Error not handled; Attempting to continue";
                                }
                                case '2': {// -12 
                                    if (OSTools.isWindows()) {
                                        new HeimdallInstall().installWindowsDrivers();
                                    }
                                    return "'LIBUSB_ERROR_NOT_SUPPORTED'; Attempting to continue";
                                }
                                default: {// -1
                                    return "'LIBUSB_ERROR_IO' Error not Handled; Attempting to continue";
                                }
                            }
                        }
                        case '2': {// -2
                            return "'LIBUSB_ERROR_INVALID_PARAM' Error not handled; Attempting to continue";
                        }
                        case '3': {// -3
                            return "'LIBUSB_ERROR_ACCESS' Error not handled; Attempting to continue";
                        }
                        case '4': {// -4
                            return "'LIBUSB_ERROR_NO_DEVICE' Error not handled; Attempting to continue";
                        }
                        case '5': {// -5
                            return "'LIBUSB_ERROR_NOT_FOUND' Error not handled; Attempting to continue";
                        }
                        case '6': {// -6
                            return "'LIBUSB_ERROR_BUSY' Error not handled; Attempting to continue";
                        }
                        case '7': {// -7
                            return "'LIBUSB_ERROR_TIMEOUT'; Attempting to continue";
                        }
                        case '8': {// -8
                            return "'LIBUSB_ERROR_OVERFLOW' Error not handled; Attempting to continue";
                        }
                        case '9': {
                            if (logFromHeimdallCommand.charAt(startIndex + 2) == 9) {// -99
                                return "'LIBUSB_ERROR_OTHER' Error not handled; Attempting to continue";
                            } else {//-9
                                return "'LIBUSB_ERROR_PIPE'; Attempting to continue";
                            }
                        }
                        default: {
                            return "'LIBUSB_ERROR_OTHER' Error not handled; Script halted";
                        }
                    }
                }
                startIndex++;
            }
        }
        return "";
    }

    /**
     * gets the command to run heimdall
     *
     * @return string path to heimdall
     */
    public static String getHeimdallCommand() {

        if (OSTools.isLinux()) {
            return "heimdall";

        } else if (OSTools.isWindows()) {
            return HeimdallInstall.heimdallDeployed;

        } else {
            Shell shell = new Shell();
            String cmd = "/usr/local/bin/heimdall";
            String check = shell.silentShellCommand(new String[]{cmd});
            //we got the file
            if (check.equals("")) {
                cmd = "/usr/bin/heimdall";
                check = shell.silentShellCommand(new String[]{cmd});
                //try different things 
                if (check.equals("CritError!!!")) {
                    cmd = "/bin/heimdall";
                    check = shell.silentShellCommand(new String[]{cmd});
                    if (check.equals("CritError!!!")) {
                        cmd = "heimdall";
                        check = shell.silentShellCommand(new String[]{cmd});
                        if (check.equals("CritError!!!")) {
                            return "";
                        }
                        return cmd;
                    }
                    return cmd;
                }
                return cmd;
            }
            return cmd;
        }
    }

    private void sleepForOneSecond() {
        try {
            Thread.sleep(1000);
            log.progress(".");
        } catch (InterruptedException ex) {
            log.errorHandler(ex);
        }
    }
    final static String[] errFail = {"Failed to end phone file transfer sequence!",//FAIL
        "Failed to end modem file transfer sequence!",//FAIL
        "Failed to confirm end of file transfer sequence!",//FAIL
        "Failed to request dump!",//FAIL
        "Failed to receive dump size!",//FAIL
        "Failed to request dump part ",//FAIL
        "Failed to receive dump part ",//FAIL
        "Failed to send request to end dump transfer!",//FAIL
        "Failed to receive end dump transfer verification!",//FAIL
        "Failed to initialise file transfer!",//FAIL
        "Failed to begin file transfer sequence!",//FAIL
        "Failed to confirm beginning of file transfer sequence!",//FAIL
        "Failed to send file part packet!",//FAIL
        "Failed to request device info packet!",//FAIL
        "Failed to initialise PIT file transfer!",//FAIL
        "Failed to confirm transfer initialisation!",//FAIL
        "Failed to send PIT file part information!",//FAIL
        "Failed to confirm sending of PIT file part information!",//FAIL
        "Failed to send file part packet!",//FAIL
        "Failed to receive PIT file part response!",//FAIL
        "Failed to send end PIT file transfer packet!",//FAIL
        "Failed to confirm end of PIT file transfer!",//FAIL
        "Failed to request receival of PIT file!",//FAIL
        "Failed to receive PIT file size!",//FAIL
        "Failed to request PIT file part ",//FAIL
        "Failed to receive PIT file part ",//TODO: FAIL  BAD USB CABLE
        "Failed to send request to end PIT file transfer!",//FAIL
        "Failed to receive end PIT file transfer verification!",//FAIL
        "Failed to download PIT file!",//TODO: FAIL  BAD USB CABLE
        "Failed to send end session packet!",//FAIL
        "Failed to receive session end confirmation!",//FAIL
        "Failed to send reboot device packet!",//FAIL
        "Failed to receive reboot confirmation!",//FAIL
        "Failed to begin session!",//FAIL
        "Failed to send file part size packet!",//FAIL
        "Failed to complete sending of data: ",//FAIL
        "Failed to complete sending of data!",//FAIL
        "Failed to unpack device's PIT file!",//FAIL
        "Failed to retrieve device description",//FAIL
        "Failed to retrieve config descriptor",//FAIL
        "Failed to find correct interface configuration",//FAIL
        "Failed to read PIT file.",//FAIL
        "Failed to open output file ",//FAIL
        "Failed to write PIT data to output file.",//FAIL
        "Failed to open file ",//FAIL
        "Failed to send total bytes device info packet!",//FAIL
        "Failed to receive device info response!",//FAIL
        "Expected file part index: ",//FAIL
        "Expected file part index: ",//FAIL
        "No partition with identifier ",//FAIL
        "Could not identify the PIT partition within the specified PIT file.",//FAIL
        "Unexpected file part size response!",//FAIL
        "Unexpected device info response!",//FAIL
        "Attempted to send file to unknown destination!",//FAIL
        "The modem file does not have an identifier!",//FAIL			
        "Incorrect packet size received - expected size = ",//FAIL
        "does not exist in the specified PIT.",//FAIL
        "Partition name for ",
        "Failed to send data: ",//FAIL
        "Failed to send data!",
        "Failed to receive file part response!",//NO FAIL
        "Failed to unpack received packet.",//NO FAIL
        "Unexpected handshake response!",//NO FAIL
        "Failed to receive handshake response."
    };
    final static String[] epicFailures = {"ERROR: No partition with identifier"
    };

    private String displayArray(String[] command) {
        StringBuilder sb = new StringBuilder();
        for (String cmd : command) {
            sb.append("\"").append(cmd).append("\" ");
        }
        return sb.toString();
    }
}