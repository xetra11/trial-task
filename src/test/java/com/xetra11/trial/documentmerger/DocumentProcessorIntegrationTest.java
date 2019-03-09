package com.xetra11.trial.documentmerger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/***************************************
 * Author: xetra11                     
 * Datum: 3/9/2019                      
 * Funktion/Komponente: -              
 * Beschreibung: -                     
 * API: -                              
 **************************************/
@RunWith(SpringRunner.class)
@SpringBootTest
public class DocumentProcessorIntegrationTest {

  @Autowired
  private DocumentProcessor uut;

  @Test
  public void shouldMergeHardcodedFilesIntoOneJson() {
    uut.merge();
  }
}