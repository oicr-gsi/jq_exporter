package ca.on.oicr.gsi.jq_exporter;

import java.net.MalformedURLException;
import java.net.URL;
import net.thisptr.jackson.jq.JsonQuery;
import net.thisptr.jackson.jq.exception.JsonQueryException;


public class Config {

    private String id;
    private URL url;
    private JsonQuery jq;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(String url) throws MalformedURLException {
        this.url = new URL(url);
    }

    public JsonQuery getJq() {
        return jq;
    }

    public void setJq(String jq) throws JsonQueryException {
        this.jq = JsonQuery.compile(jq);
    }

}
