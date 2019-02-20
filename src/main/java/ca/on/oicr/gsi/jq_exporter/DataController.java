package ca.on.oicr.gsi.jq_exporter;

import com.fasterxml.jackson.databind.JsonNode;
import java.net.URISyntaxException;
import java.util.stream.Collectors;
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
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.TEXT_PLAIN);
        return new ResponseEntity<>(dataService.get(service).stream().map(JsonNode::asText).collect(Collectors.joining("\n")), responseHeaders, HttpStatus.OK);
    }

}
