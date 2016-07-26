package com.control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Matrix {

    private String matrixType;
    private File file = null;
    private ArrayList<ArrayList<Object>> matrix;
    private BufferedReader buffer;
    private int high, wide;

    public Matrix(String pathFile) throws IOException {
        file = new File(pathFile);
        readFile();
        fillMatrix();
    }

    public Matrix(File file) throws IOException {
        this.file = file;
        readFile();
        fillMatrix();
    }

    public int getHigh() {
        return high;
    }

    public int getWide() {
        return wide;
    }

    public String matrixType() {
        return matrixType;
    }

    private void readFile() throws FileNotFoundException, IOException {
        buffer = new BufferedReader(new FileReader(file));
        matrixType = buffer.readLine();
        switch (matrixType) {
            case "E":
                break;
            case "C":
                break;
            case "CO":
                break;
            default:
                throw new IOException("Tipo de matriz desconocida: " + matrixType);
        }

    }

    private void fillMatrix() throws IOException {
        String text;
        StringTokenizer st;
        ArrayList<Object> row;
        String element;
        Object value = null;
        matrix = new ArrayList<>();
        int linea = 2;
        while ((text = buffer.readLine()) != null) {
            st = new StringTokenizer(text, ", \t");
            row = new ArrayList();
            row.add(linea - 1);
            while (st.hasMoreTokens()) {
                element = st.nextElement().toString();
                try {
                    if (matrixType.compareTo("E") == 0)
                        value =  Double.parseDouble(element);
                    else if (matrixType.compareTo("C") == 0)
                        value = element;
                    else if (matrixType.compareTo("CO") == 0)
                        try {
                            value = Double.parseDouble(element);
                        } catch (NumberFormatException ex) {
                            value = element;
                        }
                    row.add(value);
                } catch (NumberFormatException ex) {
                    throw new IOException("El elemneto \"" + element
                            + "\" no coincide con el tipo de matriz: linea " + linea + " -> " + matrixType());
                }
            }
            try {
                if (row.size() != matrix.get(0).size()) {
                    throw new IOException("El numero de columnas no coincide: linea " + linea);
                }
            } catch (IndexOutOfBoundsException ex) {}
            matrix.add(row);
            linea++;
        }
        buffer.close();
        wide = !matrix.isEmpty() ? matrix.get(0).size() : 0;
        high = matrix.size();
    }

    public ArrayList<ArrayList<Object>> getMatrix() {
        return matrix;
    }

    public Object[][] getMatrixArray() {
        Object[][] array = new Object[high][wide];
        for (int i = 0; i < high; i++)
            array[i] =  matrix.get(i).toArray();
        return array;
    }

//    public static void main(String[] args) {
//        try {
//            Matrix mat = new Matrix("/home/torpre/test");
//            Object[][] array = mat.getMatrixArray();
//            for (Object[] i : array) {
//                for (Object j : i) {
//                    System.out.print(j + " ");
//                }
//                System.out.println();
//            }
//        } catch (IOException ex) {
//            System.out.println(ex.getMessage());
//        }
//    }
}
