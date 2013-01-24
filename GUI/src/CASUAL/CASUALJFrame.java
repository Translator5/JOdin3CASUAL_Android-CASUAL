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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.MissingResourceException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.ImageIcon;
import org.jdesktop.application.Application;

/**
 *
 * @author adam
 */
public final class CASUALJFrame extends javax.swing.JFrame {

    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;
    String NonResourceFileName;
    Log Log = new Log();
    FileOperations FileOperations = new FileOperations();
    private String ComboBoxValue = "";

    /**
     * Creates new form CASUALJFrame2
     */
    public CASUALJFrame() {
        initComponents();


        //Statics.GUI=this;
        enableControls(false);
        Statics.ProgressBar = this.ProgressBar;

        ProgressArea.setContentType("text/html");
        Statics.ProgressPane = CASUALJFrame.ProgressArea;
        Statics.initDocument();

        ProgressArea.setText(ProgressArea.getText() + Statics.PreProgress);



        populateFields();
        org.jdesktop.application.ResourceMap resourceMap = Application.getInstance().getContext().getResourceMap(CASUALJFrame.class);

        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int count = 0; count < busyIcons.length; count++) {
            busyIcons[count] = resourceMap.getIcon("StatusBar.busyIcons[" + count + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                StatusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });

        idleIcon = resourceMap.getIcon("/" + "StatusBar.idleIcon");
        StatusAnimationLabel.setIcon(idleIcon);
        Log.level3("OMFGWOOT");
        Log.level1(FileOperations.readTextFromResource(Statics.ScriptLocation + "Overview.txt"));

        
        Log.level2("Deploying ADB");
        new CASUALDeployADB().runAction();
  
        Log.level3("Searching for scripts");
        prepareScripts();

        Log.level3("Updating Scripts for UI");
        comboBoxUpdate();
        //TODO: Uncompress zip if needed



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
        jDesktopPane1 = new javax.swing.JDesktopPane();
        WindowBanner = new javax.swing.JLabel();
        comboBoxScriptSelector = new javax.swing.JComboBox();
        startButton = new javax.swing.JButton();
        DonateButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        ProgressBar = new javax.swing.JProgressBar();
        StatusAnimationLabel = new javax.swing.JLabel();
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
        FileChooser1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FileChooser1ActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle(java.util.ResourceBundle.getBundle("CASUAL/resources/CASUALApp").getString("Application.title") +java.util.ResourceBundle.getBundle("CASUAL/resources/CASUALApp").getString("Application.revision"));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowClosed(java.awt.event.WindowEvent evt) {
                stopADB(evt);
            }
        });

        WindowBanner.setFont(new java.awt.Font("Ubuntu", 0, 36)); // NOI18N
        WindowBanner.setText("NARZ or picture of some sort");

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
        comboBoxScriptSelector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxScriptSelectorActionPerformed(evt);
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

        StatusAnimationLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/CASUAL/resources/icons/idle-icon.png"))); // NOI18N

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
                .addContainerGap()
                .addComponent(StatusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(StatusAnimationLabel)
                .addGap(6, 6, 6))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(StatusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(StatusAnimationLabel)
                            .addGap(4, 4, 4)))
                    .addComponent(ProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addComponent(WindowBanner, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(comboBoxScriptSelector, javax.swing.GroupLayout.Alignment.LEADING, 0, 511, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(startButton, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(DonateButton))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(WindowBanner, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
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
        Log.level0("");
        Log.level3("StartButtonActionPerformed() Script Activated");
        Log.level3("Script known as " + this.comboBoxScriptSelector.getSelectedItem().toString() + " is running");
        this.busyIconTimer.start();
        Statics.DeviceMonitor.DeviceCheck.stop();
        enableControls(false);
        String script = comboBoxScriptSelector.getSelectedItem().toString();
        String diskLocation;
        diskLocation = Statics.getScriptLocationOnDisk(script);


        //check for updates
        if (!(diskLocation.length() == 0)) {
            Statics.TargetScriptIsResource = false;
            NonResourceFileName = Statics.getScriptLocationOnDisk(script);
        }

        //execute
        if (Statics.TargetScriptIsResource) {
            new CASUALScriptParser().executeSelectedScriptResource(script);
        } else {
            new CASUALScriptParser().executeSelectedScriptFile(NonResourceFileName, script);
        }
        this.busyIconTimer.stop();
        //enableControls(true);
    }
    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        this.StartButtonActionPerformed();
    }//GEN-LAST:event_startButtonActionPerformed

    private void MenuItemShowDeveloperPaneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemShowDeveloperPaneActionPerformed
        CASUALDeveloperInstructions CDI = new CASUALDeveloperInstructions();
        CDI.setVisible(true);
    }//GEN-LAST:event_MenuItemShowDeveloperPaneActionPerformed

    private void MenuItemOpenScriptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemOpenScriptActionPerformed

        String FileName;
        int returnVal = FileChooser1.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                FileName = FileChooser1.getSelectedFile().getCanonicalPath();
                NonResourceFileName = this.getFilenameWithoutExtension(FileName);
                Log.level1("Description for " + NonResourceFileName);
                Log.level1(FileOperations.readFile(NonResourceFileName + ".txt"));
                this.comboBoxScriptSelector.setSelectedItem(NonResourceFileName);
                Statics.SelectedScriptFolder = Statics.TempFolder + new File(NonResourceFileName).getName();
                Log.level0("Delete this debug line in MenuItemOpenScriptActionPerformed()");
                //TODO: Do this in the background
                if (new FileOperations().verifyFileExists(NonResourceFileName.toString() + ".zip")) {
                    new Unzip().unzipFile(NonResourceFileName.toString() + ".zip", Statics.SelectedScriptFolder);
                }
                Statics.ScriptLocation = Statics.SelectedScriptFolder;
                comboBoxScriptSelector.setEditable(true);
                ComboBoxValue = getFilenameWithoutExtension(FileName);
                comboBoxScriptSelector.setSelectedItem(ComboBoxValue);
                comboBoxScriptSelector.setEditable(false);
                Statics.TargetScriptIsResource = false;

            } catch (IOException ex) {
                Logger.getLogger(CASUALJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_MenuItemOpenScriptActionPerformed

    private void MenuItemShowAboutBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemShowAboutBoxActionPerformed
        Statics.TargetScriptIsResource = true;
        CASUALAboutBox CAB = new CASUALAboutBox();
        CAB.setVisible(true);
    }//GEN-LAST:event_MenuItemShowAboutBoxActionPerformed

    private void FileChooser1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FileChooser1ActionPerformed
        /*
         * Log.level1("Description for " +
         * jComboBox1.getSelectedItem().toString());
         * Log.level1(FileOperations.readTextFromResource(Statics.ScriptLocation+jComboBox1.getSelectedItem().toString()+".txt"));
         * Statics.SelectedScriptFolder=Statics.TempFolder+jComboBox1.getSelectedItem().toString();
         */
    }//GEN-LAST:event_FileChooser1ActionPerformed

    private void MenuItemExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_MenuItemExitActionPerformed

    private void DonateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DonateButtonActionPerformed
        TimeOutOptionPane timeOutOptionPane = new TimeOutOptionPane();
        int DResult = timeOutOptionPane.showTimeoutDialog(
                60, //timeout
                null, //parentComponent
                "This application was developed by " + Statics.DeveloperName + " using CASUAL framework.\n"
                + "Donations give developers a tangeble reason to continue quality software development\n",
                "Donate to the developers", //DisplayTitle
                TimeOutOptionPane.OK_OPTION, // Options buttons
                TimeOutOptionPane.INFORMATION_MESSAGE, //Icon
                new String[]{"Donate To CASUAL", "Donate To " + Statics.DonateButtonText}, // option buttons
                "No"); //Default{
        if (DResult == 0) {
            launchLink("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=ZYM99W5RHRY3Y");
        } else if (DResult == 1) {
            launchLink(Statics.DeveloperDonateLink);

        }

    }//GEN-LAST:event_DonateButtonActionPerformed

    private void comboBoxScriptSelectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxScriptSelectorActionPerformed
        Statics.TargetScriptIsResource = true;

    }//GEN-LAST:event_comboBoxScriptSelectorActionPerformed

    private void comboBoxScriptSelectorPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_comboBoxScriptSelectorPopupMenuWillBecomeInvisible
        Log.level3("hiding script selector TargetScript: "+comboBoxScriptSelector.getSelectedItem().toString());
        if (comboBoxScriptSelector.getSelectedItem().toString().contains(Statics.Slash)) {
            Statics.TargetScriptIsResource = false;
        } else {
            Statics.TargetScriptIsResource = true;

        }
        comboBoxUpdate();
    }//GEN-LAST:event_comboBoxScriptSelectorPopupMenuWillBecomeInvisible

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        CASUALLog CASUALLogJFrame = new CASUALLog();
        CASUALLogJFrame.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void stopADB(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_stopADB
        this.startStopTimer(false);

    }//GEN-LAST:event_stopADB

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.startStopTimer(false);
        new Shell().sendShellCommand(new String[]{Statics.AdbDeployed, "kill-server"});

    }//GEN-LAST:event_formWindowClosing
    boolean buttonEnableStage = false;
    private void startButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_startButtonMouseClicked

    }//GEN-LAST:event_startButtonMouseClicked

    private void startButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_startButtonMouseExited
    }//GEN-LAST:event_startButtonMouseExited

    private void StatusLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_StatusLabelMouseClicked
        if (buttonEnableStage) {
            startButton.setEnabled(buttonEnableStage);
            this.comboBoxScriptSelector.setEnabled(buttonEnableStage);
            this.startButton.setText(java.util.ResourceBundle.getBundle("SCRIPTS/build").getString("Window.ExecuteButtonText"));
            
        }
        if (!startButton.isEnabled() && !Statics.MasterLock) {
            startButton.setText("Click again to enable all controls");
            buttonEnableStage = true;
            
            
            
        }
    }//GEN-LAST:event_StatusLabelMouseClicked

    private void StatusLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_StatusLabelMouseExited
        this.startButton.setText(java.util.ResourceBundle.getBundle("SCRIPTS/build").getString("Window.ExecuteButtonText"));
        buttonEnableStage = false;
        // TODO add your handling code here:
    }//GEN-LAST:event_StatusLabelMouseExited
    private void comboBoxUpdate() {
        Log.level2("From Resource: " + Statics.TargetScriptIsResource);
        Log.level1("--" + comboBoxScriptSelector.getSelectedItem().toString() + "--");
        if (Statics.TargetScriptIsResource) {
            Log.level1(FileOperations.readTextFromResource(Statics.ScriptLocation + comboBoxScriptSelector.getSelectedItem().toString() + ".txt") + "\n");
        } else {
            Log.level1(FileOperations.readFile(comboBoxScriptSelector.getSelectedItem().toString() + ".txt") + "\n");
        }
        String ZipResource;
        Statics.SelectedScriptFolder = Statics.TempFolder + comboBoxScriptSelector.getSelectedItem().toString();
        if (Statics.TargetScriptIsResource) {
            ZipResource = Statics.ScriptLocation + comboBoxScriptSelector.getSelectedItem().toString() + ".zip";
        } else {
            ZipResource = comboBoxScriptSelector.getSelectedItem().toString() + ".zip";
        }

        if (getClass().getResource(ZipResource) != null) {
            Log.level3("Extracting archive....");

            Unzip Unzip = new Unzip();
            try {
                Unzip.UnZipResource(ZipResource.toString(), Statics.SelectedScriptFolder);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(CASUALJFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(CASUALJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Log.level3("Exiting comboBoxUpdate()");
    }

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
    private javax.swing.JProgressBar ProgressBar;
    private javax.swing.JLabel StatusAnimationLabel;
    private javax.swing.JLabel StatusLabel;
    private javax.swing.JLabel WindowBanner;
    private javax.swing.JComboBox comboBoxScriptSelector;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton startButton;
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

    private void prepareScripts() {
        try {
            Statics.MasterLock = true;
            listScripts();
            Statics.MasterLock = false;
        } catch (IOException ex) {
            Log.level0("ListScripts() could not find any entries");
            Logger.getLogger(CASUALJFrame.class.getName()).log(Level.SEVERE, null, ex);

        }


    }

    private String getFilenameWithoutExtension(String FileName) {

        if (FileName.endsWith(".scr")) {
            FileName = FileName.replace(".scr", "");
        }
        return FileName;

    }
    private boolean DeviceTimerState = false;

    public void startStopTimer(boolean StateCommanded) {
        if (StateCommanded && !DeviceTimerState) {
            Statics.DeviceMonitor.DeviceCheck.start();
        } else if (!StateCommanded && DeviceTimerState) {
            Statics.DeviceMonitor.DeviceCheck.start();
        }
    }

    public void setStatusMessageLabel(String text) {
        this.StatusLabel.setText(text);
    }

    private void listScripts() throws IOException {

        CodeSource Src = CASUAL.CASUALApp.class.getProtectionDomain().getCodeSource();
        int Count = 0;
        ArrayList<String> list = new ArrayList<String>();
        if (Src != null) {
            Statics.setMasterLock(true);
            URL jar = Src.getLocation();
            ZipInputStream Zip = new ZipInputStream(jar.openStream());
            ZipEntry ZEntry;
            Log.level3("Picking Jar File:" + jar.getFile());
            while ((ZEntry = Zip.getNextEntry()) != null) {

                String EntryName = ZEntry.getName();
                if (EntryName.endsWith(".scr")) {
                    list.add(EntryName);
                }
            }
            Statics.scriptLocations = new String[list.size()];
            Statics.scriptNames = new String[list.size()];
            for (int n = 0; n < list.size(); n++) {
                String EntryName = ((String) list.get(n)).replaceFirst("SCRIPTS/", "").replace(".scr", "");
                Log.level3("Found: " + EntryName);
                Statics.scriptNames[n] = EntryName;
                comboBoxScriptSelector.addItem(EntryName);
                Count++;
            }

            if (Count == 0) {
                md5sumTestScripts();
                Log.level0("No Scripts found. Using Test Script.");
                comboBoxScriptSelector.addItem("Test Script");
                Statics.scriptLocations = new String[]{""};
                Statics.scriptNames = new String[]{"Test Script"};
            }
            Statics.MasterLock = false;

        }


    }
    
    //for use in IDE only
    private void md5sumTestScripts(){
        System.out.println("We are in "+System.getProperty("user.dir"));
        String scriptsPath=System.getProperty("user.dir")+Statics.Slash+"src"+Statics.Slash+"SCRIPTS"+Statics.Slash;
        String meta=scriptsPath+"Test Script.meta";
        System.out.println("We are targeting "+meta);
        String fileContents=new FileOperations().readFile(meta);
        String[] fileLines=fileContents.split("\\n");
        String writeOut="";
        for (int i = 0; i<fileLines.length; i++){
            String line=StringOperations.removeLeadingSpaces(fileLines[i]);
            if (line.matches("(\\S{32,})(\\s\\s)(.*\\..*)" )){
                System.out.println(line);
                String[] md5File=line.split("  ");
                String newMD5=new MD5sum().md5sum(scriptsPath+md5File[1]);
                Log.level3("updating md5 to "+newMD5);
                writeOut=writeOut+newMD5+"  "+md5File[1]+"\n";
            } else {
                writeOut=writeOut+ line+"\n";
            }
                 
        }    
            try {
                 new FileOperations().overwriteFile(writeOut, meta);
            } catch (IOException ex) {
                Logger.getLogger(CASUALJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
   
        
        
    }

    public void enableControls(boolean status) {
        if (!Statics.MasterLock) {
            startButton.setEnabled(status);
            comboBoxScriptSelector.setEnabled(status);
            Log.level3("Controls Enabled status: " + status);
        } else {
            Log.level3("Control Change requested but Statics.MasterLock is set.");
        }
    }

    private void populateFields() {

        try {
            this.startButton.setText(java.util.ResourceBundle.getBundle("SCRIPTS/build").getString("Window.ExecuteButtonText"));
            this.setTitle(java.util.ResourceBundle.getBundle("SCRIPTS/build").getString("Window.Title") + " - " + this.getTitle());
            if (java.util.ResourceBundle.getBundle("SCRIPTS/build").getString("Window.UsePictureForBanner").equals("true")) {
                WindowBanner.setText("");
                WindowBanner.setIcon(createImageIcon("/SCRIPTS/" + java.util.ResourceBundle.getBundle("SCRIPTS/build").getString("Window.BannerPic"), java.util.ResourceBundle.getBundle("SCRIPTS/build").getString("Window.BannerText")));


            } else {
                this.WindowBanner.setText(java.util.ResourceBundle.getBundle("SCRIPTS/build").getString("Window.BannerText"));
            }
            Statics.DeveloperName = java.util.ResourceBundle.getBundle("SCRIPTS/build").getString("Developer.Name");
            Statics.DonateButtonText = java.util.ResourceBundle.getBundle("SCRIPTS/build").getString("Developer.DonateToButtonText");
            Statics.UseSound = java.util.ResourceBundle.getBundle("SCRIPTS/build").getString("Audio.Enabled");
            Statics.DeveloperDonateLink = java.util.ResourceBundle.getBundle("SCRIPTS/build").getString("Developer.DonateLink");
        } catch (MissingResourceException ex) {
            Log.level0("Could not find build.prop");
            Log.level0(ex.toString());
        }
    }
}

