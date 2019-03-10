package com.xetra11.trial.documentmerger.helper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/***************************************
 * Author: xetra11                     
 * Datum: 3/10/2019                      
 * Funktion/Komponente: -              
 * Beschreibung: -                     
 * API: -                              
 **************************************/
@Service
@Slf4j
public class ImageFetcher {
  public static byte[] fetchImage(String URL){
    log.debug("fetching image for {}", URL);
    RestTemplate restTemplate = new RestTemplate();
    return restTemplate.getForObject(URL, byte[].class);
  }
}
