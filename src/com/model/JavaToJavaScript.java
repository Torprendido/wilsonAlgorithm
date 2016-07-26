package com.model;

import com.control.Matrix;
import com.control.Wilson;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class JavaToJavaScript {

    private final Stage stage;
    private Matrix mat;
    private Wilson wil;

    public JavaToJavaScript(Stage stage) {
        this.stage = stage;
    }

    public String openFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Cargar Archivo");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                mat = new Matrix(file);
                return file.getAbsolutePath();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
                return "No se seleccionó ningún archivo";
            }
        } else {
            return "No se seleccionó ningun archivo";
        }
    }

    public void saveFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Archivo");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.setInitialFileName("new_File");
        File file = fileChooser.showSaveDialog(stage);
        try {
            if (file.createNewFile()) {
                BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                for (Object[] obs : wil.getNewSample()) {
                    for (int i = 1; i < obs.length; i ++) {
                        bw.write(obs[i].toString());
                        bw.write(" ");
                    }
                    bw.write("\n");
                }
                bw.close();
            } else {
                System.out.println("no se pudo crear o ya existe el archivo");
            }
        } catch (IOException ex) {

        }
    }

    public String getRows() {
        if (mat != null) {
            StringBuilder append = new StringBuilder();
            for (Object[] i : mat.getMatrixArray()) {
                append.append("<tr class='chois'>");
                for (Object j : i) {
                    append.append("<th>");
                    append.append(j);
                    append.append("</th>");
                }
                append.append("</tr>");
            }
            return append.toString();
        } else {
            return "";
        }
    }

    public String getHeadRow(String htmlClass) {
        if (mat != null) {
            StringBuilder append = new StringBuilder();
            append.append("<tr class='");
            append.append(htmlClass);
            append.append("'>");
            append.append("<th>");
            append.append("#");
            append.append("</th>");
            for (int i = 1; i < mat.getMatrixArray()[0].length - 1; i++) {
                append.append("<th>");
                append.append("x");
                append.append(i);
                append.append("</th>");
            }
            append.append("<th>");
            append.append("Etiqueta");
            append.append("</th>");
            append.append("</tr>");
            return append.toString();
        } else {
            return "";
        }
    }

    public String getWilsonRows(double k) {
        wil = new Wilson(mat.getMatrixArray(), (int) k);
        StringBuilder append = new StringBuilder();
        for (Object[] i : wil.getNewSample()) {
            append.append("<tr class='rowsWilson'>");
            for (Object j : i) {
                append.append("<th>");
                append.append(j);
                append.append("</th>");
            }
            append.append("</tr>");
        }
        return append.toString();
    }

    public void print(String arg) {
        System.out.println(arg);
    }

}
