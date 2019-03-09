package com.xetra11.trial.documentmerger

import com.thoughtworks.xstream.XStream
import com.xetra11.trial.documentmerger.helper.XStreamMapConverter
import org.apache.commons.io.FileUtils
import spock.lang.Specification
import com.thoughtworks.xstream.converters.Converter

import java.nio.charset.Charset

/***************************************
 * Author: xetra11                     
 * Datum: 3/9/2019                      
 * Funktion/Komponente: -              
 * Beschreibung: -                     
 * API: -                              
 **************************************/
class COAHDocumentAppenderTest extends Specification {
    def "should append a coah xml file to the resulting json file" (){
        given:
        File coahFile = new File(getClass().getClassLoader().getResource("3956-coah.xml").toURI())
        String coahFileAsXML = FileUtils.readFileToString(coahFile, Charset.forName("UTF-8"))
        COAHDocumentAppender uut = new COAHDocumentAppender();

        HashMap<String, List<Object>> resultJsonFile = new HashMap<>();
        resultJsonFile.put("coah", new ArrayList<Object>());
        resultJsonFile.put("giata", new ArrayList<Object>());

        XStream xmlParser = new XStream();
        xmlParser.registerConverter(new XStreamMapConverter());
        xmlParser.alias("content", Map.class);
        HashMap<String, List<Object>> coahFileAsMap = (Map<String, List<Object>>) xmlParser.fromXML(coahFileAsXML);

        when:
        uut.apply(coahFile, resultJsonFile, DocumentAppender.DocumentType.XML);

        then:
        resultJsonFile.containsKey("coah") == true;
        resultJsonFile.get("coah", false) != false;
        resultJsonFile.get("coah").get(0).get("content").get(0).getAt("hotel").get(0).get("giata_id") == "3956"
     }
}
