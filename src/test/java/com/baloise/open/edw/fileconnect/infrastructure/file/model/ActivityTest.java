package com.baloise.open.edw.fileconnect.infrastructure.file.model;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class ActivityTest {

    @Test
    public void givenFile_whenRead_thenConvertedDto() throws IOException {
        File xmlFile = new File("src/test/resources/test-data/Run-20151103-1813-07944.tcx");

        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")));

        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        XmlMapper xmlMapper = new XmlMapper(module);

        xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        xmlMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        xmlMapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
        xmlMapper.registerModule(timeModule);

        xmlMapper.registerModule(new JaxbAnnotationModule());
        xmlMapper.registerModule(new Jdk8Module());

        TrainingCenterDatabaseT db = xmlMapper.readValue(new FileReader(xmlFile), TrainingCenterDatabaseT.class);
        Assertions.assertNotNull(db);
        Assertions.assertNotNull(db.activities);
    }
}
