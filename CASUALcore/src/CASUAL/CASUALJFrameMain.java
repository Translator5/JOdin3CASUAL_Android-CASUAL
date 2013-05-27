/*CASUALJFrame provides UI for CASUAL. 
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

import java.awt.Toolkit;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

/**
 *
 * @author adam
 */
public final class CASUALJFrameMain extends javax.swing.JFrame {

    
    //TODO: integrate How to enable development mode for Jelly Bean  and other devices
    private int busyIconIndex = 0;
    String nonResourceFileName;
    Log log = new Log();
    FileOperations fileOperations = new FileOperations();
    private String ComboBoxValue = "";

    /**
     * Creates new form CASUALJFrame2
     */
    public CASUALJFrameMain() {

        initComponents();
        enableControls(false);


        ProgressArea.setContentType("text/html");
        Statics.ProgressPane = CASUALJFrameMain.ProgressArea;
        Statics.initDocument();

        ProgressArea.setText(Statics.PreProgress + ProgressArea.getText());

        log.level4Debug("OMFGWOOT GUI running!");
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);
        if (Statics.TargetScriptIsResource) {
            if (!Statics.dumbTerminalGUI) {
                log.level2Information(fileOperations.readTextFromResource(Statics.ScriptLocation + "-Overview.txt") + "\n");
            }
        } else {
            if (!Statics.dumbTerminalGUI) {
                log.level2Information(fileOperations.readFile("-Overview.txt") + "\n");
            }
        }


        log.level4Debug("Searching for scripts");

        for (String script : Statics.scriptNames) {
            comboBoxScriptSelectorAddNewItem(script);
        }
        if (comboBoxScriptSelector.getItemCount() == 1) {
            comboBoxScriptSelector.setVisible(false);
        }

        log.level4Debug("Updating Scripts for UI");
        updateSelectedFromGUI();

        setStartButtonText(CASUALapplicationData.buttonText);
        setTitle(CASUALapplicationData.title);
        if (CASUALapplicationData.usePictureForBanner) {
            setWindowBannerText("");
            setWindowBannerImage("/SCRIPTS/" + CASUALapplicationData.bannerPic, CASUALapplicationData.bannerText);
        } else {
            setWindowBannerText(CASUALapplicationData.bannerText);
        }
        Statics.lockGUIformPrep = false;
        if (Statics.dumbTerminalGUI) {
            setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        }
        if (!CASUALapplicationData.AlwaysEnableControls){
            enableControls(true);
        }
        Statics.GUIIsAvailable=true;

    }

    /*
     * Timer for adb devices
     */
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        FileChooser1 = new javax.swing.JFileChooser();
        windowBanner = new javax.swing.JLabel();
        comboBoxScriptSelector = new javax.swing.JComboBox<>();
        startButton = new javax.swing.JButton();
        DonateButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        progressBar = new javax.swing.JProgressBar();
        StatusLabel = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane3 = new javax.swing.JScrollPane();
        ProgressArea = new javax.swing.JTextPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        MenuItemOpenScript = new javax.swing.JMenuItem();
        MenuItemExit = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        MenuItemShowDeveloperPane = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        MenuItemShowAboutBox = new javax.swing.JMenuItem();

        FileChooser1.setDialogTitle("Select a CASUAL \"scr\" file");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        windowBanner.setFont(new java.awt.Font("Ubuntu", 0, 36)); // NOI18N
        windowBanner.setText("NARZ or picture of some sort");

        comboBoxScriptSelector.setEnabled(false);
        comboBoxScriptSelector.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                comboBoxScriptSelectorPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        startButton.setText("Do It!");
        startButton.setEnabled(false);
        startButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                startButtonMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                startButtonMouseExited(evt);
            }
        });
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        DonateButton.setText("Donate");
        DonateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DonateButtonActionPerformed(evt);
            }
        });

        StatusLabel.setFont(new java.awt.Font("Ubuntu", 0, 20)); // NOI18N
        StatusLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CASUAL/resources/icons/DeviceDisconnected.png"))); // NOI18N
        StatusLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                StatusLabelMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                StatusLabelMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(StatusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jSeparator1))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(StatusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jScrollPane3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Important Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Abyssinica SIL", 0, 12))); // NOI18N

        ProgressArea.setText("<html><a href=\"http://www.w3schools.com\">Visit W3Schools.com!</a>");
        ProgressArea.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jScrollPane3.setViewportView(ProgressArea);

        jMenu1.setText("File");

        MenuItemOpenScript.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.SHIFT_MASK));
        MenuItemOpenScript.setText("Open CASUAL script");
        MenuItemOpenScript.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemOpenScriptActionPerformed(evt);
            }
        });
        jMenu1.add(MenuItemOpenScript);

        MenuItemExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        MenuItemExit.setText("Exit");
        MenuItemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemExitActionPerformed(evt);
            }
        });
        jMenu1.add(MenuItemExit);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("About");

        MenuItemShowDeveloperPane.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        MenuItemShowDeveloperPane.setText("Developing A Script");
        MenuItemShowDeveloperPane.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemShowDeveloperPaneActionPerformed(evt);
            }
        });
        jMenu2.add(MenuItemShowDeveloperPane);

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Show Log");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);

        MenuItemShowAboutBox.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        MenuItemShowAboutBox.setText("About");
        MenuItemShowAboutBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemShowAboutBoxActionPerformed(evt);
            }
        });
        jMenu2.add(MenuItemShowAboutBox);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(windowBanner, javax.swing.GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(comboBoxScriptSelector, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(startButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(DonateButton))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(windowBanner, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboBoxScriptSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(startButton)
                    .addComponent(DonateButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void StartButtonActionPerformed() {
        log.level0Error("");
        log.level4Debug("StartButtonActionPerformed() Script Activated");
        log.level4Debug("Script known as " + this.comboBoxScriptSelector.getSelectedItem().toString() + " is running");

        Statics.casualConnectionStatusMonitor.DeviceCheck.stop();
        enableControls(false);
        String script = comboBoxScriptSelector.getSelectedItem().toString();
        String diskLocation;
        diskLocation = Statics.getScriptLocationOnDisk(script);


        log.level4Debug("disk location for script resources " + diskLocation);
        //check for updates
        if (!(diskLocation.length() == 0)) {
            Statics.TargetScriptIsResource = false;
            nonResourceFileName = Statics.getScriptLocationOnDisk(script);
        }

        //execute
        if (Statics.TargetScriptIsResource) {
            log.level4Debug("Loading internal resource: " + script);
            new CASUALScriptParser().loadResourceAndExecute(script, true);
        } else {
            log.level4Debug("Loading from file: " + script);
            new CASUALScriptParser().loadFileAndExecute(nonResourceFileName, script, true);
        }

    }

    public void setProgressBar(int value) {
        progressBar.setValue(value);
    }

    public void setProgressBarMax(int value) {
        progressBar.setMaximum(value);
    }

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        this.StartButtonActionPerformed();
    }//GEN-LAST:event_startButtonActionPerformed

    private void MenuItemShowDeveloperPaneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemShowDeveloperPaneActionPerformed
        CASUALJFrameDeveloperInstructions CDI = new CASUALJFrameDeveloperInstructions();
        CDI.setVisible(true);
    }//GEN-LAST:event_MenuItemShowDeveloperPaneActionPerformed

    private void MenuItemOpenScriptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemOpenScriptActionPerformed

        String FileName;
        FileChooser1.setDialogTitle("Select a CASUAL \"scr\" file");
        FileChooser1.setFileFilter(new SCRCustomFilter());
        int returnVal = FileChooser1.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                this.enableControls(false);
                FileName = FileChooser1.getSelectedFile().getCanonicalPath();
                nonResourceFileName = this.getFilenameWithoutExtension(FileName);
                log.level2Information("Description for " + nonResourceFileName);
                log.level2Information(fileOperations.readFile(nonResourceFileName + ".txt"));
                this.comboBoxScriptSelector.setSelectedItem(nonResourceFileName);
                Statics.SelectedScriptFolder = Statics.TempFolder + new File(nonResourceFileName).getName() + Statics.Slash;
                log.level0Error("Delete this debug line in MenuItemOpenScriptActionPerformed()");
                if (new FileOperations().verifyFileExists(nonResourceFileName.toString() + ".zip")) {
                    new Unzip().unzipFile(nonResourceFileName.toString() + ".zip", Statics.SelectedScriptFolder);
                }
                Statics.ScriptLocation = Statics.SelectedScriptFolder;
                comboBoxScriptSelector.setEditable(true);
                ComboBoxValue = getFilenameWithoutExtension(FileName);
                comboBoxScriptSelector.setSelectedItem(ComboBoxValue);
                comboBoxScriptSelector.setEditable(false);
                Statics.TargetScriptIsResource = false;
                this.enableControls(true);

            } catch (IOException ex) {
                log.errorHandler(ex);
            }

        }
    }//GEN-LAST:event_MenuItemOpenScriptActionPerformed

    private void MenuItemShowAboutBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemShowAboutBoxActionPerformed
        Statics.TargetScriptIsResource = true;
        CASUALJFrameAboutBox CAB = new CASUALJFrameAboutBox();
        CAB.setVisible(true);
    }//GEN-LAST:event_MenuItemShowAboutBoxActionPerformed

    private void MenuItemExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemExitActionPerformed
        CASUALApp.shutdown(0);
    }//GEN-LAST:event_MenuItemExitActionPerformed

    private void DonateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DonateButtonActionPerformed
        setWindowBannerImage("SCRIPTS/" + CASUALapplicationData.bannerPic, CASUALapplicationData.bannerText);
        CASUALInteraction timeOutOptionPane = new CASUALInteraction();
        int DResult = timeOutOptionPane.showTimeoutDialog(
                60, //timeout
                null, //parentComponent
                "This application was developed by " + CASUALapplicationData.developerName + " using CASUAL framework.\n"
                + "Donations give developers a tangeble reason to continue quality software development\n",
                "Donate to the developers", //DisplayTitle
                CASUALInteraction.OK_OPTION, // Options buttons
                CASUALInteraction.INFORMATION_MESSAGE, //Icon
                new String[]{"Donate To CASUAL", "Donate To " + CASUALapplicationData.DontateButtonText}, // option buttons
                "No"); //Default{
        if (DResult == 0) {
            launchLink("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=ZYM99W5RHRY3Y");
        } else if (DResult == 1) {
            launchLink(CASUALapplicationData.donationLink);
        }
    }//GEN-LAST:event_DonateButtonActionPerformed

    public String comboBoxGetSelectedItem() {
        return (String) comboBoxScriptSelector.getSelectedItem();
    }

    public void comboBoxScriptSelectorAddNewItem(String item) {
        comboBoxScriptSelector.addItem(item);
    }

    private void comboBoxScriptSelectorPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_comboBoxScriptSelectorPopupMenuWillBecomeInvisible


        log.level4Debug("hiding script selector TargetScript: " + comboBoxScriptSelector.getSelectedItem().toString());

        if ((!Statics.dumbTerminalGUI) && comboBoxScriptSelector.getSelectedItem().toString().contains(Statics.Slash)) {
            Statics.TargetScriptIsResource = false;
        } else {
            Statics.TargetScriptIsResource = true;

        }
        this.enableControls(false);
        Statics.lockGUIunzip = true;
        Thread t = updateSelectedFromGUI();
        try {
            t.join();
        } catch (InterruptedException ex) {
            new Log().errorHandler(ex);
        }
        Statics.lockGUIunzip = false;
        this.enableControls(true);

    }//GEN-LAST:event_comboBoxScriptSelectorPopupMenuWillBecomeInvisible

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        CASUALJFrameLog CASUALLogJFrame = new CASUALJFrameLog();
        CASUALLogJFrame.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        Statics.casualConnectionStatusMonitor.DeviceCheck.stop();
        new Shell().sendShellCommand(new String[]{Statics.AdbDeployed, "kill-server"});

    }//GEN-LAST:event_formWindowClosing
    boolean buttonEnableStage = false;
    private void startButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_startButtonMouseClicked
    }//GEN-LAST:event_startButtonMouseClicked

    private void startButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_startButtonMouseExited
    }//GEN-LAST:event_startButtonMouseExited
    public Thread updateSelectedFromGUI() {
        log.level3Verbose("From Resource: " + Statics.TargetScriptIsResource);
        log.level2Information("--" + comboBoxScriptSelector.getSelectedItem().toString() + "--");
        if (Statics.TargetScriptIsResource) {
            if (!Statics.dumbTerminalGUI) {
                log.level2Information(fileOperations.readTextFromResource(Statics.ScriptLocation + comboBoxScriptSelector.getSelectedItem().toString() + ".txt") + "\n");
            }
        } else {
            if (!Statics.dumbTerminalGUI) {
                log.level2Information(fileOperations.readFile(comboBoxScriptSelector.getSelectedItem().toString() + ".txt") + "\n");
            }
        }
        Statics.SelectedScriptFolder = Statics.TempFolder + comboBoxScriptSelector.getSelectedItem().toString() + Statics.Slash;
        //set the ZipResource
        //final String ZipResource = Statics.TargetScriptIsResource ? (Statics.ScriptLocation + comboBoxScriptSelector.getSelectedItem().toString() + ".zip") : (comboBoxScriptSelector.getSelectedItem().toString() + ".zip");

        return new CASUALTools().prepareCurrentScript(comboBoxScriptSelector.getSelectedItem().toString());
    }

    private void StatusLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_StatusLabelMouseClicked
        if (buttonEnableStage) {
            startButton.setEnabled(buttonEnableStage);
            this.comboBoxScriptSelector.setEnabled(buttonEnableStage);
            this.startButton.setText(java.util.ResourceBundle.getBundle("SCRIPTS/-build").getString("Window.ExecuteButtonText"));

        }
        if (!startButton.isEnabled() && !Statics.lockGUIformPrep) {
            startButton.setText("Click again to enable all controls");
            buttonEnableStage = true;



        }
    }//GEN-LAST:event_StatusLabelMouseClicked

    private void StatusLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_StatusLabelMouseExited
        this.startButton.setText(java.util.ResourceBundle.getBundle("SCRIPTS/-build").getString("Window.ExecuteButtonText"));
        buttonEnableStage = false;
    }//GEN-LAST:event_StatusLabelMouseExited

    private static void launchLink(String Link) {
        LinkLauncher LinkLauncher = new LinkLauncher();
        LinkLauncher.launchLink(Link);
    }
    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton DonateButton;
    private javax.swing.JFileChooser FileChooser1;
    private javax.swing.JMenuItem MenuItemExit;
    private javax.swing.JMenuItem MenuItemOpenScript;
    private javax.swing.JMenuItem MenuItemShowAboutBox;
    private javax.swing.JMenuItem MenuItemShowDeveloperPane;
    public static javax.swing.JTextPane ProgressArea;
    private javax.swing.JLabel StatusLabel;
    private javax.swing.JComboBox<String> comboBoxScriptSelector;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JButton startButton;
    private javax.swing.JLabel windowBanner;
    // End of variables declaration//GEN-END:variables

    public void setStatusLabelIcon(String Icon, String Text) {
        StatusLabel.setIcon(createImageIcon(Icon, Text));
    }

    protected ImageIcon createImageIcon(String path, String description) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    private String getFilenameWithoutExtension(String FileName) {

        if (FileName.endsWith(".scr")) {
            FileName = FileName.replace(".scr", "");
        }
        return FileName;

    }

    public void setStatusMessageLabel(String text) {
        this.StatusLabel.setText(text);
    }

    
    public boolean getControlStatus(){
        return (startButton.isEnabled() && comboBoxScriptSelector.isEnabled());
    }
    public boolean enableControls(boolean status) {
        //LockOnADBDisconnect tells CASUAL to disregard ADB status.
        boolean bypassLock= CASUALapplicationData.AlwaysEnableControls;
        if ( bypassLock ) {
            status=true; //if LockOnADBDisconnect is false then just enable controls
            startButton.setEnabled(status);
            comboBoxScriptSelector.setEnabled(status);
            return true;
        }
        if  (!Statics.lockGUIformPrep && !Statics.lockGUIunzip && !Statics.scriptRunLock) {
            startButton.setEnabled(status);
            comboBoxScriptSelector.setEnabled(status);
            log.level4Debug("Controls Enabled status: " + status);
        } else {
            log.level4Debug("Control Change requested but Statics.MasterLock is set.");
        }
        return (checkGUIStatus(status)) ? true: false;
    }
    private boolean checkGUIStatus(boolean expectedStatus){
        if (Statics.GUIIsAvailable) { 
            if (expectedStatus == Statics.GUI.getControlStatus()){
                return true; //expected true = actually true;
            } else {
                return false; 
            }
        } else if (Statics.useGUI) {  //if gui is not available yet
            return false; 
        }
        return true; //gui is not used for this CASUAL.
        
    }
    public void setWindowBannerText(String text) {
        windowBanner.setText(text);
    }

    public void setStartButtonText(String text) {
        startButton.setText(text);
    }

    public void setWindowBannerImage(String icon, String text) {
        windowBanner.setIcon(new ImageIcon(getClass().getResource(icon), ""));

    }
}