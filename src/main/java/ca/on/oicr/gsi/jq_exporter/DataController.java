package ca.on.oicr.gsi.jq_exporter;

import com.fasterxml.jackson.databind.JsonNode;
import java.net.URISyntaxException;
import net.thisptr.jackson.jq.exception.JsonQueryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DataController {

    @Autowired
    private DataService dataService;

    @RequestMapping(value = "/{service}")
    public ResponseEntity<String> get(@PathVariable(value = "service") String service) throws URISyntaxException, JsonQueryException, Exception {
        StringBuilder sb = new StringBuilder();
        for (JsonNode j : dataService.get(service)) {
            sb.append(j.asText());
            sb.append("\n");
        }

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.TEXT_PLAIN);
        return new ResponseEntity<>(sb.toString(), responseHeaders, HttpStatus.OK);
    }

}
