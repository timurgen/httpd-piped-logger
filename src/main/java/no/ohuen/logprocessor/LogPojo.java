package no.ohuen.logprocessor;

import nl.basjes.parse.core.Field;

/**
 *
 * @author abnormal
 */
public class LogPojo {
    private String ip;
    private String httpMethod;
    private String status;
    private String uri;

    @Field("IP:connection.client.host")
    public void setIp(final String ip) {
        this.ip = ip;
    }
    @Field("HTTP.METHOD:request.firstline.method")
    public void setHttpMethod(final String httpMethod) {
        this.httpMethod = httpMethod;
    }
    @Field("STRING:request.status.last")
    public void setStatus(String status) {
        this.status = status;
    }
    @Field("HTTP.URI:request.firstline.uri")
    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getIp() {
        return ip;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public String getStatus() {
        return status;
    }

    public String getUri() {
        return uri;
    }

    @Override
    public String toString() {
        return "LogPojo{" + "ip=" + ip + ", httpMethod=" + httpMethod + ", status=" + status + ", uri=" + uri + '}';
    }
    
    
    
}
