package com.xetra11.trial.documentmerger;

import com.thoughtworks.xstream.XStream;
import com.xetra11.trial.documentmerger.helper.XStreamMapConverter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

/***************************************
 * Author: xetra11                     
 * Datum: 3/9/2019                      
 * Funktion/Komponente: -              
 * Beschreibung: -                     
 * API: -                              
 **************************************/
@Service
@Slf4j
public class COAHDocumentAppender implements DocumentAppender {
  @Override
  public void apply(@NotNull File fileToAppend, @NonNull Map<String, List<Object>> appendTo, @NotNull DocumentType type) {
    Objects.requireNonNull(fileToAppend, "filepath is mandatory");
    Objects.requireNonNull(type, "type is mandatory");

    XStream xmlParser = new XStream();
    xmlParser.registerConverter(new XStreamMapConverter());
    xmlParser.alias("content", Map.class);
    try {
      String coahFileAsXML = FileUtils.readFileToString(fileToAppend, Charset.forName("UTF-8"));
      Map<String, List<Object>> coahFileAsMap = (HashMap<String, List<Object>>) xmlParser.fromXML(coahFileAsXML);
      ((List<Object>) appendTo.get("coah")).add(coahFileAsMap);
      log.info("added {} to coah element", fileToAppend.getName());
      log.debug("coah file map structure: {}", coahFileAsMap);
    } catch (IOException e) {
      log.error("reading coah xml file as string failed", e);
    }

  }
}
