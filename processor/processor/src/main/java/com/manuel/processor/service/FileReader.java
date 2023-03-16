package com.manuel.processor.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class FileReader {
    private String path;
    private static String filePath;
    private static int lineasValidas = 0;
    private static int lineasNoValidas = 0;
    private static boolean resultadoValidacion;
    public FileReader() {
    }
    public FileReader(String path) {
        this.path = path;
    }
    public String getPath() {
        return path;
    }
    public static void lectura(String path) {
        //Este metodo recibe la direccion del archivo, dependiendo la extension de este
        //se abre el metodo correspondiente.
        //La direccion del archivo se almacena en una variable estatica para poder
        //utilizarla en los metodos estaticos de lectura.
        filePath = path;
        if (path.contains(".csv")) {
            lecturaCsv();
        } else if (path.contains(".xlsx")) {
            lecturaXlsx();
        }
    }
    public static void lecturaCsv() {
        //Metodo para leer archivos .csv, se convierten las lineas en una list de string
        //separada por comas para tener la informacion de cada columna, hallamos los index
        //de las columnas que nos interesan y se envia el string concatenado de:
        //extension del archivo, columna Email, columna Date Of Birth, columna Job Title.

        try {
            BufferedReader reader = new BufferedReader(new java.io.FileReader(filePath));
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
    public static void lecturaXlsx() {
        //Lectura para archivos .xlsx con apache poi.
        //se abre el archivo, se recorre la primera fila para hallar la ubicacion
        //de las columnas que nos interesan y luego se recorren el resto de filas.
        //en cada ciclo, se envia una peticion con un string con la siguiente forma:
        //extensionArchivo, Columna Injury Location, Columna Report Type.
        //no se envia toda la linea sino lo que nos interesa verificar.
        try {
            int indexInjury = 0, indexReport = 0;
            InputStream input = new FileInputStream(filePath);
            Workbook wb = new XSSFWorkbook(input);
            Sheet sheet = wb.getSheetAt(0);
            Iterator<Row> iterator = sheet.iterator();
            DataFormatter formatter = new DataFormatter();
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
            //hallando los indexs de las columnas que nos interesan
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                String contenidoCelda = formatter.formatCellValue(cell);
                if (contenidoCelda.equals("Injury Location")) {
                    indexInjury = cell.getColumnIndex();
                } else if (contenidoCelda.equals("Report Type")) {
                    indexReport = cell.getColumnIndex();
                }
            }
            //Recorriendo el resto de filas.
            while (iterator.hasNext()) {
                nextRow = iterator.next();
                String lineaValidar = "xlsx," + formatter.formatCellValue(nextRow.getCell(indexInjury)) + "," + formatter.formatCellValue(nextRow.getCell(indexReport));
                resultadoValidacion = enviarPeticion(lineaValidar);
                contador(resultadoValidacion);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public static boolean enviarPeticion(String line) {
        //Metodo para realizar una peticion post al microservicio encargador de validar las lineas.
        //Se utiliza restTemplate, se hace un request de tipo String y se recibe un booleano con
        //El resultado de la validacion, luego se retorna esa resputas.
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(line, headers);
        boolean response = restTemplate.postForObject("http://localhost:8090/api/v1/validar", request, Boolean.class);
        return response;
    }
    public static void contador(boolean aux){
        //Metodo para contar la lineas validas y no validas.
        if(aux){
            lineasValidas++;
        } else{
            lineasNoValidas++;
        }
    }
    public static int getLineasValidas() {
        return lineasValidas;
    }
    public static int getLineasNoValidas() {
        return lineasNoValidas;
    }
    public static void setLineasValidas(int lineasValidas) {
        FileReader.lineasValidas = lineasValidas;
    }
    public static void setLineasNoValidas(int lineasNoValidas) {
        FileReader.lineasNoValidas = lineasNoValidas;
    }
}
