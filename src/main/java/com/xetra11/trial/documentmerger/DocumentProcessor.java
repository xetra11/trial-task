package com.xetra11.trial.documentmerger;

import com.xetra11.trial.documentmerger.helper.DocumentToString;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/***************************************
 * Author: xetra11                     
 * Datum: 3/9/2019                      
 * Funktion/Komponente: -              
 * Beschreibung: -                     
 * API: -                              
 **************************************/
@Service
@Slf4j
public class DocumentProcessor {

  private DocumentCombiner giatToCoahCombiner;

  @Autowired
  public DocumentProcessor(@Qualifier("GIATAToCOAHCombiner") DocumentCombiner giatToCoahCombiner) {
    this.giatToCoahCombiner = giatToCoahCombiner;
  }


  public void merge() {
    File resultFile = new File("dist/test/result.json");

    List<Document> combinedDocuments = Arrays.asList(new String[]{"3956", "162838"}).stream()
            .map(this::getPair)
            .map(pair -> giatToCoahCombiner.combine(pair))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());

    combinedDocuments.forEach(document -> {
      String xml = DocumentToString.docToString(document);
      JSONObject jsonObject = XML.toJSONObject(xml);
      log.debug(jsonObject.toString(4));
    });

  }

  private DocumentPair getPair(String id) {
    try {
      File coahFile = new File(getClass().getClassLoader().getResource(id + "-coah.xml").toURI());
      File giataFile = new File(getClass().getClassLoader().getResource(id + "-giata.xml").toURI());
      return new DocumentPair(coahFile, giataFile);
    } catch (URISyntaxException e) {
      log.error("could not create a document pair for {}", id, e);
    }
    return null;
  }
}
