package com.xetra11.trial.documentmerger;

import com.xetra11.trial.documentmerger.processor.DocumentProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Profile;
import org.springframework.core.SpringProperties;
import org.springframework.core.env.Environment;

import java.util.Arrays;

@SpringBootApplication
@Slf4j
public class DocumentMergerCLI implements CommandLineRunner {

  @Autowired
  private DocumentProcessor processor;
  @Autowired
  private Environment environment;

  public static void main(String[] args) {
    SpringApplication.run(DocumentMergerCLI.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    String[] activeProfiles = environment.getActiveProfiles();
    if (Arrays.stream(activeProfiles).anyMatch(profile -> profile.equals("web"))) {
      log.info("web profile active - will start only as web endpoint");
    } else {
      processor.merge();
    }
  }
}
