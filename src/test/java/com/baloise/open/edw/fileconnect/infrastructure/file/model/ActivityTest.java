package com.baloise.open.edw.fileconnect.infrastructure.file.model;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class ActivityTest {

    @Test
    public void givenFile_whenRead_thenConvertedDto() throws IOException {
        Path xmlFile = new File("src/test/resources/test-data/Cycle-20150105-1200-00000.tcx").toPath();
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        String xmlContent = new String(Files.readAllBytes(xmlFile));
        TrainingCenterDatabaseT db = xmlMapper.readValue(xmlContent, TrainingCenterDatabaseT.class);
        Assertions.assertNotNull(db);
        Assertions.assertNotNull(db.activities);
    }
}
