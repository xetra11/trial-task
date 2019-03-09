package com.xetra11.trial.documentmerger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;

/***************************************
 * Author: xetra11                     
 * Datum: 3/9/2019                      
 * Funktion/Komponente: -              
 * Beschreibung: -                     
 * API: -                              
 **************************************/
@Slf4j
public class ResultDocumentGenerator implements DocumentGenerator {
  @Override
  public void generate(File filePath, Object result) {
    try {
      FileUtils.forceMkdirParent(filePath);
    } catch (IOException e) {
      log.error("could not force created folder", e);
    }
    ObjectMapper mapper = new ObjectMapper();
    try {
      String json = mapper.writeValueAsString(result);
      try (PrintWriter printWriter = new PrintWriter(filePath)) {
        printWriter.println(json);
      } catch (FileNotFoundException e) {
        log.error("saving result document has failed", e);
      }
    } catch (JsonProcessingException e) {
      log.error("processing result object into json string failed", e);
    }
  }
}
