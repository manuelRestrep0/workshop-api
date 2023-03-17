package com.manuel.processor.file;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class Archivo {
    protected int lineasValidas;
    protected int lineasNoValidas;

    public Archivo(int lineasValidas, int lineasNoValidas) {
        this.lineasValidas = lineasValidas;
        this.lineasNoValidas = lineasNoValidas;
    }
    public Archivo() {

    }
    public boolean enviarPeticion(String line){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(line, headers);
        boolean response = restTemplate.postForObject("http://localhost:8090/api/v1/validar", request, Boolean.class);
        return response;
    }
    public void contador(boolean aux){
        //Metodo para contar la lineas validas y no validas.
        if(aux){
            this.lineasValidas++;
        } else{
            this.lineasNoValidas++;
        }
    }


}
