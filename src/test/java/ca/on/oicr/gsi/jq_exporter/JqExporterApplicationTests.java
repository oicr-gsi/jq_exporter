package ca.on.oicr.gsi.jq_exporter;

import java.nio.file.Files;
import java.util.stream.Collectors;
import net.thisptr.jackson.jq.exception.JsonQueryException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ca.on.oicr.gsi.jq_exporter.JqExporterApplication.class,
        value = {"path_config=target/test-classes/oozie_instrumentation_config.json"},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JqExporterApplicationTests {

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void contextLoads() {
        //
    }

    @Test
    public void getFile() throws JsonQueryException, Exception {
        webTestClient.get().uri("/local-file").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.TEXT_PLAIN)
                .expectBody(String.class).isEqualTo(Files.lines(resourceLoader.getResource("classpath:oozie_instrumentation_prometheus.out")
                        .getFile().toPath()).collect(Collectors.joining("\n")));
    }

}
