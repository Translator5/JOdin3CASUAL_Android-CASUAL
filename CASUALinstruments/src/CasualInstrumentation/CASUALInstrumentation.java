/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CasualInstrumentation;

import CASUAL.instrumentation.Instrumentation;
import CASUAL.misc.MandatoryThread;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author adamoutler
 */
public class CASUALInstrumentation extends Application implements CASUAL.instrumentation.InstrumentationInterface {

    static CASUALInstrumentationFXMLController doc;

    @SuppressWarnings("unchecked")
    public CASUALInstrumentation() {
        this.list = new ArrayList<>();
        observableList = FXCollections.observableList(list);

    }

    @Override
    @SuppressWarnings("unchecked") // method uses the MandatoryThread.toString() method This is handled by javafx. 
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("CASUALInstrumentationFXML.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();

        Instrumentation.instrumentation = this;
        doc.running.<MandatoryThread>setItems(observableList);
        Thread.sleep(1000);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                CASUAL.CASUALMain.main(new String[]{});
            }
        });
        t.start();
        new CASUALInstrumentationTimer().start();

        //new CASUALInstrumentationTimer();
    }
    List<MandatoryThread> list;
    ObservableList<MandatoryThread> observableList;

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void updateStatus(final String status) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                doc.ta.appendText(status + "\n");
            }
        });

    }

    @Override
    public void trackThread(final MandatoryThread thread) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                observableList.add(thread);

                thread.waitFor();
                //observableList.remove(thread);//javaFX operations should go here
            }
        });

    }


 

    public void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            Logger.getLogger(CASUALInstrumentation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
