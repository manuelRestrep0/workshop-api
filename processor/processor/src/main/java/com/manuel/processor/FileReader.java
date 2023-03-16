package com.manuel.processor;

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
    private static BufferedReader lines;

    public FileReader() {
    }

    public FileReader(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public static void lectura(String path){
        filePath = path;
        if(path.contains(".csv")){
            lecturaCsv();

        } else if(path.contains(".xlsx")){
            //lectura archivo .xlsx
            lecturaXlsx();

        }
    }

    public static void lecturaCsv(){
        try {
            BufferedReader reader = new BufferedReader(new java.io.FileReader(filePath));
            String line = reader.readLine();
            List<String> parts = new ArrayList<>(Arrays.asList(line.split(",")));
            int indexEmail = parts.indexOf("Email");
            int indexDate = parts.indexOf("Date of birth");
            int indexJob = parts.indexOf("Job Title");
            //Aca debe enviar las lineas al otro servicio.
            while((line = reader.readLine()) != null){
                parts = Arrays.asList(line.split(","));
                String lineaValidar = "csv,"+parts.get(indexEmail)+","+parts.get(indexDate)+","+parts.get(indexJob);
                enviarPeticion(lineaValidar);
            }
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    public static void lecturaXlsx(){
        try{
            int indexInjury=0, indexReport=0;
            InputStream input = new FileInputStream(filePath);
            Workbook wb = new XSSFWorkbook(input);
            Sheet sheet = wb.getSheetAt(0);
            Iterator<Row> iterator = sheet.iterator();
            DataFormatter formatter = new DataFormatter();
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
            //hallando los indexs
            while(cellIterator.hasNext()){
                Cell cell = cellIterator.next();
                String contenidoCelda = formatter.formatCellValue(cell);
                if(contenidoCelda.equals("Injury Location")){
                    indexInjury = cell.getColumnIndex();
                } else if(contenidoCelda.equals("Report Type")){
                    indexReport = cell.getColumnIndex();
                }
            }
            while(iterator.hasNext()){
                nextRow = iterator.next();
                String lineaValidar = "xlsx,"+formatter.formatCellValue(nextRow.getCell(indexInjury))+","+formatter.formatCellValue(nextRow.getCell(indexReport));
                enviarPeticion(lineaValidar);

            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    public static void enviarPeticion(String line){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(line,headers);
        boolean response = restTemplate.postForObject("http://localhost:8090/api/v1/validar",request,Boolean.class);
        System.out.println(response);
    }
}
