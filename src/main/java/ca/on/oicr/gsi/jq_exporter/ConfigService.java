package ca.on.oicr.gsi.jq_exporter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import net.thisptr.jackson.jq.JsonQuery;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

/**
 *
 * @author mlaszloffy
 */
@Service
@RefreshScope
public class ConfigService {

    @Value("${path_config}")
    private String path;

    private Map<String, Config> configById;

    @PostConstruct
    public void init() throws IOException {
        System.out.println("Configuration file path = " + path);
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Config>> typeReference = new TypeReference<List<Config>>() {
        };
        List<Config> cs = mapper.readValue(new File(path), typeReference);

        configById = new HashMap<>();
        cs.forEach((c) -> {
            configById.put(c.getId(), c);
        });
        System.out.println("Configuration entries loaded = " + cs.size());
    }

    public URL getUrl(String id) throws Exception {
        if (configById.containsKey(id)) {
            return configById.get(id).getUrl();
        } else {
            throw new Exception("Service id not found");
        }
    }

    public JsonQuery getJq(String id) throws Exception {
        if (configById.containsKey(id)) {
            return configById.get(id).getJq();
        } else {
            throw new Exception("Service id not found");
        }
    }

}
