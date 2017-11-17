package ca.on.oicr.gsi.jq_exporter;

import com.fasterxml.jackson.databind.JsonNode;
import java.net.URISyntaxException;
import java.util.List;
import net.thisptr.jackson.jq.exception.JsonQueryException;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<JsonNode> get(String service) throws URISyntaxException, JsonQueryException, Exception {
        RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());
        JsonNode in = restTemplate.getForObject(configService.getUrl(service).toURI(), JsonNode.class);

        return configService.getJq(service).apply(in);
    }

    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        int timeout = 5000;
        SimpleClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(timeout);
        return clientHttpRequestFactory;
    }
}
