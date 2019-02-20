package ca.on.oicr.gsi.jq_exporter;

import java.nio.file.Files;
import java.util.stream.Collectors;
import net.thisptr.jackson.jq.exception.JsonQueryException;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ca.on.oicr.gsi.jq_exporter.JqExporterApplication.class,
        value = {"path_config=target/test-classes/oozie_instrumentation_config.json"})
public class JqExporterApplicationTests {

    @Autowired
    private DataController controller;

    @Autowired
    ResourceLoader resourceLoader;

    @Test
    public void contextLoads() {

    }

    @Test
    public void getFile() throws JsonQueryException, Exception {
        ResponseEntity<String> actual = controller.get("local-file");
        ResponseEntity<String> expected = ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(Files.lines(resourceLoader.getResource("classpath:oozie_instrumentation_prometheus.out").getFile().toPath())
                        .collect(Collectors.joining("\n")));
        assertEquals(actual, expected);
    }

}
