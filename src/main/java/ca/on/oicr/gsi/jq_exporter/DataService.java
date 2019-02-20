package ca.on.oicr.gsi.jq_exporter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.net.URISyntaxException;
import java.util.List;
import net.thisptr.jackson.jq.Scope;
import net.thisptr.jackson.jq.exception.JsonQueryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author mlaszloffy
 */
@Service
public class DataService {

    @Autowired
    private ConfigService configService;

    @Autowired
    ResourceLoader resourceLoader;

    private static final Scope SCOPE = Scope.newEmptyScope();
    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        SCOPE.loadFunctions(Scope.class.getClassLoader());
    }

    public List<JsonNode> get(String service) throws URISyntaxException, JsonQueryException, Exception {
        JsonNode json;
        if (configService.getUrl(service).toURI().toString().startsWith("file:")) {
            File file = resourceLoader.getResource(configService.getUrl(service).toString()).getFile();
            json = MAPPER.readTree(file);
        } else {
            RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());
            json = restTemplate.getForObject(configService.getUrl(service).toURI(), JsonNode.class);
        }
        return configService.getJq(service).apply(SCOPE, json);
    }

    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        int timeout = 5000;
        SimpleClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(timeout);
        return clientHttpRequestFactory;
    }
}
