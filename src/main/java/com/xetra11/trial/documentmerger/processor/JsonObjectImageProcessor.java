package com.xetra11.trial.documentmerger.processor;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.xetra11.trial.documentmerger.helper.ImageFetcher;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
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
public class JsonObjectImageProcessor implements ImageProcessor<JSONObject> {

  @Value("${processor.output.images}")
  @Setter
  private String imageExportDirectory;
  private int imageNameCounter = 0;

  @Override
  public void process(JSONObject jsonObject) {
    this.imageNameCounter = 0;
    try {
      FileUtils.forceMkdir(new File(imageExportDirectory));
      String jsonSource = jsonObject.toString(4);

      List<String> imageUrls = JsonPath.parse(jsonSource)
              .read("$..image[*].url", List.class);

      log.info("{} images crawled", imageUrls.size());

      List<byte[]> images = imageUrls.parallelStream()
              .map(ImageFetcher::fetchImage)
              .collect(Collectors.toList());

      log.info("{} images fetched", images.size());

      FileUtils.cleanDirectory(new File(imageExportDirectory));

      images.forEach(imageBytes -> {
        imageNameCounter++;

        try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(new File(imageExportDirectory + "image_" + imageNameCounter + ".jpg")))) {
          try {
            outputStream.write(imageBytes);
          } catch (IOException e) {
            e.printStackTrace();
          }
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        }
      });

      log.info("{} images exported", images.size());

    } catch (IOException e) {
      log.error("could not create directory for image processing", e);
    }


  }
}
