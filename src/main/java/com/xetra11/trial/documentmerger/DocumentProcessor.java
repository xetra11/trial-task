package com.xetra11.trial.documentmerger;

import com.xetra11.trial.documentmerger.helper.DocumentToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
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
  private final JSONObject rootJson;
  private DocumentCombiner giatToCoahCombiner;

  @Autowired
  public DocumentProcessor(@Qualifier("GIATAToCOAHCombiner") DocumentCombiner giatToCoahCombiner) {
    rootJson = new JSONObject("{\"documents\":[]}");
    this.giatToCoahCombiner = giatToCoahCombiner;
  }

  public void merge() {
    log.info("combining xml document pairs");
    List<Document> combinedDocuments = combineDocuments();
    log.info("converting xml document pairs into json");
    convertToJsonAndAppend(combinedDocuments);
    log.info("convert and append standalone xml files");
    convertAndAppendStandaloneXMLFiles();
    log.info("append existing json files");
    appendExistingJsonFiles();
    log.info("write result file");
    writeToFile();
  }

  private void convertAndAppendStandaloneXMLFiles() {
    try {
      File standaloneXML = new File(getClass().getClassLoader().getResource( "411144-giata.xml").toURI());
      log.debug("standalone xml file: {}", standaloneXML.getName());
      String xml = FileUtils.readFileToString(standaloneXML, Charset.forName("UTF-8"));
      JSONObject jsonObject = XML.toJSONObject(xml);
      rootJson.append("documents", jsonObject);
    } catch (URISyntaxException e) {
      log.error("file could not be found", e);
    } catch (IOException e) {
      log.error("reading from file failed", e);
    }
  }

  private void convertToJsonAndAppend(List<Document> combinedDocuments) {
    combinedDocuments.stream()
            .map(document -> {
              String xml = DocumentToString.docToString(document);
              return XML.toJSONObject(xml);})
            .forEach(jsonObject ->  rootJson.append("documents", jsonObject) );
  }

  private List<Document> combineDocuments() {
    return Arrays.asList(new String[]{"3956", "162838"}).stream()
              .map(this::getPair)
              .map(pair -> giatToCoahCombiner.combine(pair))
              .filter(Optional::isPresent)
              .map(Optional::get)
              .collect(Collectors.toList());
  }

  private void writeToFile() {
    log.info("starting to write");
    File resultFile = new File("dist/test/result.json");
    try (PrintWriter printWriter = new PrintWriter(resultFile);) {
      log.info("writing in progress...");
      printWriter.println(rootJson.toString(4));
      log.info("finished writing result file {}", resultFile.getName());
    } catch (FileNotFoundException e) {
      log.error("file could not be found", e);
    }
  }

  private void appendExistingJsonFiles() {
    try {
      File jsonFile = new File(getClass().getClassLoader().getResource( "594608-coah.json").toURI());
      String jsonString = FileUtils.readFileToString(jsonFile, Charset.forName("UTF-8"));
      rootJson.append("documents", new JSONObject(jsonString));
    } catch (URISyntaxException e) {
      log.error("could not create a jsonobject", e);
    } catch (IOException e) {
      log.error("file could not be read");
    }
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
