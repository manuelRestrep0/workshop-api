package com.manuel.processor.file;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArchivoCsv extends Archivo implements ArchivoMetodos{

    public ArchivoCsv() {
        super();
    }

    @Override
    public void lectura(String path) {
        try {
            boolean resultadoValidacion;
            BufferedReader reader = new BufferedReader(new java.io.FileReader(path));
            String line = reader.readLine();
            List<String> parts = new ArrayList<>(Arrays.asList(line.split(",")));
            int indexEmail = parts.indexOf("Email");
            int indexDate = parts.indexOf("Date of birth");
            int indexJob = parts.indexOf("Job Title");
            //Aqui se recorren todas las lineas del archivo
            while ((line = reader.readLine()) != null) {
                parts = Arrays.asList(line.split(","));
                String lineaValidar = "csv," + parts.get(indexEmail) + "," + parts.get(indexDate) + "," + parts.get(indexJob);
                resultadoValidacion = enviarPeticion(lineaValidar);
                contador(resultadoValidacion);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public String toString() {
        return "ArchivoCsv{" +
                "lineasValidas=" + lineasValidas +
                ", lineasNoValidas=" + lineasNoValidas +
                '}';
    }
}
