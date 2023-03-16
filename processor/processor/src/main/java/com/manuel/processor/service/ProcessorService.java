package com.manuel.processor.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcessorService {

    public ProcessorService() {
    }

    public String leerArchivo(String path){
        //Se recibe la direccion del archivo, se llama el metodo de lectura, luego de esto
        //Se convierten los resultados en un String para retornarlo al Controller.
        //Se reinician los valores de las lineas validas y no validas y se retorna el String resultado.
        FileReader.lectura(path);
        String resultado = "Lineas validas: "+FileReader.getLineasValidas()+".  Lineas no validas: "+FileReader.getLineasNoValidas();
        FileReader.setLineasValidas(0);
        FileReader.setLineasNoValidas(0);
        return resultado;
    }

}
