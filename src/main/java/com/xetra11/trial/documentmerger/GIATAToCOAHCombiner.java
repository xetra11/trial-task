package com.xetra11.trial.documentmerger;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.validation.constraints.NotNull;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

/***************************************
 * Author: xetra11                     
 * Datum: 3/9/2019                      
 * Funktion/Komponente: -              
 * Beschreibung: -                     
 * API: -                              
 **************************************/
@Service
@Slf4j
public class GIATAToCOAHCombiner implements DocumentCombiner {
  @Override
  public Optional<Document> combine(@NotNull File COAHfile, @NotNull File GIATAfile) {
    log.info("Combination of COAH file {} and GIATA file {} starting", COAHfile.getName(), GIATAfile.getName());
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = null;
      builder = factory.newDocumentBuilder();
      try {
        Document coahDocument = builder.parse(COAHfile);
        Document giataDocument = builder.parse(GIATAfile);
        log.info("resolving nodes");
        Node giataResult = giataDocument.getElementsByTagName("result").item(0);
        Node giataIDElement = coahDocument.getElementsByTagName("giata_id").item(0);
        Node parentNode = giataIDElement.getParentNode();
        Node importedNode = coahDocument.importNode(giataResult, true);

        log.info("inserting GIATA file content");
        parentNode.insertBefore(importedNode, giataIDElement);
        return Optional.of(coahDocument);

      } catch (SAXException | IOException e) {
        log.error("document parsing went wrong", e);
        log.error("parsed files were {} and {}", COAHfile.getName(), GIATAfile.getName());
      }
    } catch (ParserConfigurationException e) {
      log.error("document builder could not be created", e);
    }
    return Optional.empty();
  }

  @Override
  public Optional<Document> combine(DocumentPair documentPair) {
    return this.combine(documentPair.getFileA(), documentPair.getFileB());
  }
}
