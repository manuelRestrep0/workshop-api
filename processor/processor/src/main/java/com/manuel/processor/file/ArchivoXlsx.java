package com.manuel.processor.file;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class ArchivoXlsx extends Archivo implements ArchivoMetodos{
    public ArchivoXlsx() {
    }
    @Override
    public void lectura(String path) {
        try {
            boolean resultadoValidacion;
            int indexInjury = 0, indexReport = 0;
            InputStream input = new FileInputStream(path);
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
    @Override
    public String toString() {
        return "ArchivoXlsx{" +
                "lineasValidas=" + lineasValidas +
                ", lineasNoValidas=" + lineasNoValidas +
                '}';
    }
}
