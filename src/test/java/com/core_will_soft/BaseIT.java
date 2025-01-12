package com.core_will_soft;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.FileCopyUtils;

import java.io.*;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.core.io.ResourceLoader.CLASSPATH_URL_PREFIX;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class BaseIT {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ResourceLoader resourceLoader;


    protected String readExpectedResponse(final String path) {
        return readJsonFromFile("expected-response/" + path);
    }

    protected String readJsonFromFile(final String path) {
        try (Reader reader = new InputStreamReader(getResourceAsInputStream(path + ".json"), UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private InputStream getResourceAsInputStream(final String path) throws IOException {
        return resourceLoader.getResource(CLASSPATH_URL_PREFIX + path).getInputStream();
    }
}
