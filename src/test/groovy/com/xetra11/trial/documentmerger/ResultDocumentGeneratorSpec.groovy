package com.xetra11.trial.documentmerger

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.commons.io.FileUtils
import spock.lang.Specification

import java.nio.charset.Charset
import java.nio.file.Path

/***************************************
 * Author: xetra11                     
 * Datum: 3/9/2019                      
 * Funktion/Komponente: -              
 * Beschreibung: -                     
 * API: -                              
 **************************************/
class ResultDocumentGeneratorSpec extends Specification {
    def "should create a json file with two first level elements coah and giata" () {
        given:
        DocumentGenerator uut = new ResultDocumentGenerator();
        HashMap<String, Object> testMap = new HashMap<>();
        testMap.put("coah", new ArrayList<Object>());
        testMap.put("giata", new ArrayList<Object>());
        ObjectMapper mapper = new ObjectMapper();
        String expected = mapper.writeValueAsString(testMap);
        when:
        File resultFile = new File("dist/test/result.json");
        uut.generate(resultFile, testMap)
        then:
        String actual = FileUtils.readFileToString(resultFile, Charset.forName("UTF-8")).trim() // remove whitespace
        actual == expected
    }
}
