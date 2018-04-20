
package no.ohuen.logprocessor;

import java.util.Map;
import org.apache.kafka.common.serialization.Serializer;

/**
 *
 * @author abnormal
 */
public class LogPojoSerializer<T> implements Serializer<T> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public byte[] serialize(String topic, T data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void close() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
