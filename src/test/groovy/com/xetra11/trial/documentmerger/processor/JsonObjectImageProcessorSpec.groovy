package com.xetra11.trial.documentmerger.processor

import org.apache.commons.io.FileUtils
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestContext
import org.springframework.test.context.TestPropertySource
import spock.lang.Specification

import java.nio.charset.Charset

/***************************************
 * Author: xetra11                     
 * Datum: 3/9/2019                      
 * Funktion/Komponente: -              
 * Beschreibung: -                     
 * API: -
 **************************************/
class JsonObjectImageProcessorSpec extends Specification {

    @Autowired
    private JsonObjectImageProcessor uut

    def "should process all images of a given json object"(){
        given:
        JsonObjectImageProcessor uut = new JsonObjectImageProcessor();
        File jsonFile = new File(Thread.currentThread().getContextClassLoader().getResource("merged-documents-test.json").toURI());
        String jsonString = FileUtils.readFileToString(jsonFile, Charset.forName("UTF-8"));
        JSONObject testJson = new JSONObject(jsonString);
        uut.setImageExportDirectory("dist/images/")
        String[] extensions = ["jpg"];
        when:
        uut.process(testJson);

        then:
        FileUtils.listFiles(new File("dist/images/"), extensions, false).size() == 106

    }
}
