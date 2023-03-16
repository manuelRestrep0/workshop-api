package com.manuel.processor;

import com.manuel.processor.FileReader;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcessorService {

    private List<Integer> resultados;

    public ProcessorService() {
    }

    public ProcessorService(List<Integer> resultados) {
        this.resultados = resultados;
    }

    public List<Integer> getResultados() {
        return resultados;
    }
    public String leerArchivo(String path){
        FileReader.lectura(path);
        String primeraLinea="Funciono";
        return primeraLinea;
    }

}
