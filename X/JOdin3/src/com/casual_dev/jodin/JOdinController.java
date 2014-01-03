/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.casual_dev.jodin;

import CASUAL.CASUALMessageObject;
import CASUAL.OSTools;
import CASUAL.Statics;
import CASUAL.caspac.Caspac;
import CASUAL.caspac.Script;
import CASUAL.communicationstools.heimdall.HeimdallTools;
import CASUAL.communicationstools.heimdall.odin.CorruptOdinFileException;
import CASUAL.communicationstools.heimdall.odin.Odin;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author adam
 */
public class JOdinController implements Initializable, CASUAL.iCASUALUI {

    @FXML
    Button testButton;

    @FXML
    private Label passFailLabel;
    @FXML
    private AnchorPane displaySurface;
    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private Button button3;
    @FXML
    private Label messageTitle;
    @FXML
    private TextArea messageDisplay;

    @FXML
    private AnchorPane mainSurface;

    private Stage stage;
    @FXML
    private Rectangle passFailBox;
    @FXML
    private ProgressBar progress;
    @FXML
    private TextField connectedIndicator;

    @FXML
    private CheckBox autoReboot;
    @FXML
    private CheckBox repartition;

    @FXML
    private Button pitSelection;
    @FXML
    private TextField pitLocation;

    @FXML
    private CheckBox bootloaderFlash;
    @FXML
    private Button bootloaderSelection;
    @FXML
    private TextField bootloaderLocation;

    @FXML
    private CheckBox pdaFlash;
    @FXML
    private Button pdaSelection;
    @FXML
    private TextField pdaLocation;

    @FXML
    private CheckBox phoneFlash;
    @FXML
    private Button phoneSelection;
    @FXML
    private TextField phoneLocation;

    @FXML
    private CheckBox cscFlash;
    @FXML
    private Button cscSelection;
    @FXML
    private TextField cscLocation;

    @FXML
    private TextArea messageBox;

    @FXML
    private Button start;
    @FXML
    private Button reset;
    @FXML
    private Button exit;

    @FXML
    Button reportProblem;

    @FXML
    TextField inputText;
    @FXML
    Button donate;
    boolean ready = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CASUAL.Statics.GUI = this;
        ready = true;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                deviceDisconnected();
                checkFilesCheckboxes();
                displaySurface.setVisible(false);
                inputText.setPromptText("Enter Text Here");
                new CASUAL.CASUALConnectionStatusMonitor().start(new CASUAL.communicationstools.heimdall.HeimdallTools());
            }
        });
        t.start();
    }

    @FXML
    private void installDriverButton() {
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                boolean x = new HeimdallTools().installDriver();
                new CASUALMessageObject("All Done>>>All done.\n\nReport: " + (x == true ? "Sucessful!" : "No Changes") + "\n\nIf you continue to have problems,"+(OSTools.isMac()?" ensure you have removed Samsung Kies from your computer.  You should also":"")+" reboot the device and the computer. ").showInformationMessage();
            }
        });
        t.start();

    }

    File initialDir = new File(System.getProperty("user.dir"));

    private String showFileChooser(String title) {
        String s;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.setInitialDirectory(initialDir);
        File f = fileChooser.showOpenDialog(JOdinMain.stage);

        if (f == null) {
            return "";
        } else {
            initialDir = f.getParentFile();
            return f.getAbsolutePath();
        }
    }

    @FXML
    private void pitPressed() {
        pitLocation.setText(showFileChooser("Select PIT"));
        checkFilesCheckboxes();
    }

    @FXML
    private void bootloaderPressed() {
        bootloaderLocation.setText(showFileChooser("Select Bootloader"));
        checkFilesCheckboxes();
        if (!bootloaderLocation.getText().equals("")) {
            bootloaderFlash.setSelected(true);
        }
    }

    @FXML
    private void pdaPressed() {
        this.pdaLocation.setText(showFileChooser("Select PDA"));
        checkFilesCheckboxes();
        if (!pdaLocation.getText().equals("")) {
            pdaFlash.setSelected(true);
        }
    }

    @FXML
    private void phonePressed() {
        this.phoneLocation.setText(showFileChooser("Select Phone"));
        checkFilesCheckboxes();
        if (!phoneLocation.getText().equals("")) {
            phoneFlash.setSelected(true);
        }
    }

    @FXML
    private void cscPressed() {
        this.cscLocation.setText(showFileChooser("Select CSC"));
        checkFilesCheckboxes();
        if (!cscLocation.getText().equals("")) {
            cscFlash.setSelected(true);
        }
    }

    public void completePass() {
        passFailLabel.setText("PASS");
        passFailBox.setFill(Paint.valueOf("green"));

    }

    public void completeFail() {
        passFailLabel.setText("FAIL");
        passFailBox.setFill(Paint.valueOf("red"));
    }

    private void checkFilesCheckboxes() {
        if (bootloaderLocation.getText().equals("")) {
            bootloaderFlash.setSelected(false);
            bootloaderFlash.setDisable(true);
            bootloaderLocation.setDisable(true);
        } else {
            bootloaderFlash.setDisable(false);
            bootloaderLocation.setDisable(false);
        }
        if (pdaLocation.getText().equals("")) {
            pdaFlash.setSelected(false);
            pdaFlash.setDisable(true);
            pdaLocation.setDisable(true);
        } else {
            pdaFlash.setDisable(false);
            pdaLocation.setDisable(false);
        }
        if (phoneLocation.getText().equals("")) {
            phoneFlash.setSelected(false);
            phoneFlash.setDisable(true);
            phoneLocation.setDisable(true);
        } else {
            phoneFlash.setDisable(false);
            phoneLocation.setDisable(false);
        }
        if (cscLocation.getText().equals("")) {
            cscFlash.setSelected(false);
            cscFlash.setDisable(true);
            cscLocation.setDisable(true);
        } else {
            cscFlash.setDisable(false);
            cscLocation.setDisable(false);
        }
        if (pitLocation.getText().equals("")) {
            pitLocation.setDisable(true);
        } else {
            pitLocation.setDisable(false);
        }
    }

    private void exit() {
        System.exit(0);
    }

    @FXML
    private void reset() {
        this.pitLocation.setText("");
        this.bootloaderLocation.setText("");
        this.pdaLocation.setText("");
        this.phoneLocation.setText("");
        this.cscLocation.setText("");
        this.autoReboot.setSelected(true);
        this.repartition.setSelected(false);
        this.messageBox.setText("");
        checkFilesCheckboxes();
    }

    @Override
    public boolean isReady() {
        return ready;
    }

    @Override
    public void setReady(boolean ready) {
        this.ready = ready;
    }

    boolean dummyGUI = false;

    @Override
    public boolean isDummyGUI() {
        return false;
    }

    @FXML
    private void displaySurface(final String[] message, final String[] buttonText,final boolean textbox) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mainSurface.setEffect(new GaussianBlur());
                if (textbox){
                    inputText.setVisible(true);
                } else {
                    inputText.setVisible(false);
                }
                returnValue = null;
                button1.setVisible(true);
                if (buttonText.length > 0) {
                    button1.setText(buttonText[0]);
                } else {
                    button1.setVisible(false);
                }
                if (buttonText.length >= 2) {
                    button2.setText(buttonText[1]);
                    button2.setVisible(true);
                } else {
                    button2.setVisible(false);
                }
                if (buttonText.length >= 3) {
                    button3.setText(buttonText[2]);
                    button3.setVisible(true);
                } else {
                    button3.setVisible(false);
                }

                messageTitle.setText(message[0]);
                messageDisplay.setText(message[1]);
                displaySurface.setOpacity(0);
                displaySurface.setVisible(true);
                FadeTransition ft = new FadeTransition(Duration.millis(500), displaySurface);
                ft.setFromValue(0);
                ft.setToValue(100);
                ft.setCycleCount(1);
                ft.setAutoReverse(true);
                ft.play();
            }
        });
    }
    static int i;

    private void hideDisplaySurface() {
        mainSurface.setEffect(null);
        synchronized (lock) {
            lock.notify();
        }
        inputText.setVisible(false);
        this.displaySurface.setVisible(false);

    }

    @FXML
    void button1ClickAction() {
        if (inputText.getText().equals("")) {
            returnValue = "0";
        } else {
            returnValue = inputText.getText();
            inputText.setText("");
        }

        this.hideDisplaySurface();
    }

    @FXML
    private void button2ClickAction() {
        returnValue = "1";
        this.hideDisplaySurface();
    }

    @FXML
    private void button3ClickAction() {
        returnValue = "2";
        this.hideDisplaySurface();
    }
    String returnValue;
    final Object lock = new Object();

    @Override
    public String displayMessage(CASUALMessageObject messageObject) {
        String[] message = new String[]{messageObject.title, messageObject.messageText};
        returnValue = null;
        switch (messageObject.messageType) {
            case CASUAL.iCASUALUI.INTERACTION_ACTION_REUIRED:
                displaySurface(message, new String[]{"I did it", "I didn' do it"},false);
                break;
            case CASUAL.iCASUALUI.INTERACTION_COMMAND_NOTIFICATION:
                displaySurface(message, new String[]{"OK"},false);
                break;
            case CASUAL.iCASUALUI.INTERACTION_INPUT_DIALOG:
                displaySurface(message, new String[]{"OK","Cancel"},true);
                break;
            case CASUAL.iCASUALUI.INTERACTION_SHOW_ERROR:
                displaySurface(message, new String[]{"OK"},false);
                break;
            case CASUAL.iCASUALUI.INTERACTION_SHOW_INFORMATION:
                displaySurface(message, new String[]{"OK"},false);
                break;
            case CASUAL.iCASUALUI.INTERACTION_SHOW_YES_NO:
                displaySurface(message, new String[]{"Yes", "no"},false);
                break;
            case CASUAL.iCASUALUI.INTERACTION_TIME_OUT:
                displaySurface(message, new String[]{"Ok"},false);
                break;
            case CASUAL.iCASUALUI.INTERACTION_USER_CANCEL_OPTION:
                displaySurface(message, new String[]{"OK", "Cancel"},false);
                break;
            case CASUAL.iCASUALUI.INTERACTION_USER_NOTIFICATION:
                displaySurface(message, new String[]{"OK"},false);
                break;

        }
        try {
            while (returnValue == null) {
                synchronized (lock) {
                    lock.wait();
                }
            }
            return returnValue;
        } catch (InterruptedException ex) {
            return returnValue;
        }

    }

    @Override
    public void dispose() {
        this.exit();
    }

    private void disableControls(final boolean b) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                pitSelection.setDisable(b);
                bootloaderSelection.setDisable(b);
                pdaSelection.setDisable(b);
                phoneSelection.setDisable(b);
                cscSelection.setDisable(b);
                start.setDisable(b);
                reset.setDisable(b);
                exit.setDisable(b);
            }
        });
    }

    @FXML
    @Override
    public void StartButtonActionPerformed() {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                disableControls(true);
                if (pitLocation.getText().isEmpty()) {

                    String pitMessage = "Do you want me to obtain a PIT for you?>>>This application requires what is known as a` 'PIT file'.  The PIT file tells the application where to place files on your device.  If you don't have a PIT file, we can obtain one from your device";
                    boolean obtainPit = new CASUALMessageObject(pitMessage).showYesNoOption();
                    if (obtainPit) {
                        new CASUALMessageObject("You will need to restart your device in Download Mode.>>>In order to obtain a PIT file, the device will be rebooted.  Once it reboots, you will need to put it back into download mode.\n\nPro-Tip: hold the download mode combination and press OK to dismiss this dislog to reboot into download mode immediately").showInformationMessage();
                        HeimdallTools ht = new HeimdallTools();
                        String newPit = Statics.getTempFolder() + "part.pit";
                        ht.run(new String[]{"download-pit", "--output", newPit}, 10000, true);
                        File f = new File(newPit);
                        if (f.exists()) {
                            pitLocation.setText(f.getAbsolutePath());
                            disableControls(false);
                            new CASUALMessageObject("Got it!>>>We obtained the PIT file and everything is ready to flash\n\n Click the start button again when you're ready. ").showInformationMessage();
                            return;
                        } else {
                            new CASUALMessageObject("Could not obtain pit.>>>We could not obtain the pit file. We tried, but it didn't work. ").showErrorDialog();
                            disableControls(false);
                            return;
                        }

                    } else {
                        new CASUALMessageObject("We can't continue without a PIT>>>We cannot continue without a PIT file.  Please select one or let CASUAL do it for you.").showInformationMessage();
                        disableControls(false);
                        return;
                    }
                }
                ArrayList<File> list = new ArrayList<>();
                if (bootloaderFlash.isSelected()) {
                    String location = bootloaderLocation.getText();
                    list.add(new File(location));
                }
                if (pdaFlash.isSelected()) {
                    String location = pdaLocation.getText();
                    list.add(new File(location));
                }
                if (phoneFlash.isSelected()) {
                    String location = phoneLocation.getText();
                    list.add(new File(location));
                }
                if (cscFlash.isSelected()) {
                    String location = cscLocation.getText();
                    list.add(new File(location));
                }
                try {
                    Odin odin = new Odin(new File(pitLocation.getText()));
                    String[] flashList = odin.getHeimdallFileParametersFromOdinFile(Statics.getTempFolder(), list.toArray(new File[list.size()]));
                    ArrayList<String> flashCommand = new ArrayList<>();
                    flashCommand.add("flash");
                    flashCommand.add("--PIT");
                    flashCommand.add(pitLocation.getText());
                    if (repartition.isSelected()) {
                        flashCommand.add("--repartition");
                    }
                    flashCommand.addAll(Arrays.asList(flashList));
                    if (!autoReboot.isSelected()) {
                        flashCommand.add("--no-reboot");
                    }
                    messageBox.appendText(new HeimdallTools().getBinaryLocation());
                    String[] runCommand = flashCommand.toArray(new String[flashCommand.size()]);
                    System.out.println("heimdall Command:");
                    System.out.println(new HeimdallTools().getBinaryLocation());

                    CASUAL.CASUALConnectionStatusMonitor.stop();
                    HeimdallTools ht = new HeimdallTools();
                    if (!ht.isConnected()) {
                        messageBox.appendText("\nWaiting for device");
                    }
                    String s = ht.run(runCommand, 9999999, true);
                    messageBox.appendText(s);
                    if (s.endsWith("successful\n"
                            + "\n"
                            + "Ending session...\n"
                            + "Rebooting device...\n"
                            + "Releasing device interface...\n") || !s.contains("failed")) {
                        completePass();
                    } else {
                        completeFail();
                    }
                    CASUAL.CASUALConnectionStatusMonitor.resumeAfterStop();

                } catch (FileNotFoundException ex) {
                    new CASUALMessageObject("We can't continue without a PIT>>>The PIT file was corrupt. We cannot continue without a PIT file.  Please select one or let CASUAL do it for you.").showInformationMessage();
                    pitLocation.setText("");
                    disableControls(false);

                    return;
                } catch (CorruptOdinFileException ex) {
                    new CASUALMessageObject("Corrupt File>>> A corrupted file was detected.  In order to continue, you must select a valid Odin File").showInformationMessage();
                    bootloaderLocation.setText("");
                    pdaLocation.setText("");
                    phoneLocation.setText("");
                    cscLocation.setText("");
                    disableControls(false);

                }

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(JOdinController.class.getName()).log(Level.SEVERE, null, ex);
                }
                disableControls(false);
            }
        });
        t.start();

    }

    @Override
    public boolean setControlStatus(boolean status) {
        this.start.setDisable(!status);
        return !start.isDisabled();
    }

    @Override
    public boolean getControlStatus() {
        return !start.isDisabled();
    }

    Caspac caspac;

    @Override
    public void setCASPAC(Caspac caspac) {
        this.caspac = caspac;
    }

    @Override
    public void setInformationScrollBorderText(String title) {
        this.messageBox.appendText(title + "\n");
    }

    @Override
    public void setProgressBar(int value) {

        progress.setProgress(value / max);
    }

    int max = 100;

    @Override
    public void setProgressBarMax(int value) {
        max = value;
    }

    @Override
    public void setScript(Script s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setStartButtonText(String text) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setStatusLabelIcon(String Icon, String Text) {
        messageBox.appendText(Text + "\n");
    }

    @Override
    public void setStatusMessageLabel(String text) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setWindowBannerImage(BufferedImage icon, String text) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setWindowBannerText(String text) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setVisible(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deviceConnected(String mode) {
        connectedIndicator.setText("Connected");
        connectedIndicator.setStyle("-fx-background-color: lime;");

    }

    @Override
    public void deviceDisconnected() {
        connectedIndicator.setText("Disconnected");
        connectedIndicator.setStyle("-fx-background-color: red;");

    }

    @Override
    public void deviceMultipleConnected(int numberOfDevicesConnected) {
        connectedIndicator.setText("Disconnected");
    }

    @Override
    public void setBlocksUnzipped(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @FXML
    private void sendReport() {
        Thread t=new Thread( new Runnable(){
            @Override
            public void run(){
                
                    try {
                        new CASUAL.network.Pastebin().doPosting();
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(JOdinController.class.getName()).log(Level.SEVERE, null, ex);

                    } catch (IOException ex) {
                        Logger.getLogger(JOdinController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (URISyntaxException ex) {
                        Logger.getLogger(JOdinController.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
        });
        t.setName("reporting error");
        t.start();
    }
 
    @FXML
    private void donatePressed(){
        CASUAL.network.LinkLauncher ll = new CASUAL.network.LinkLauncher("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=WHZEN3FV6SKAA");
        ll.launch();
    }
}