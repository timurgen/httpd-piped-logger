package no.ohuen.pipelogger;

import java.util.Properties;
import org.apache.kafka.clients.producer.ProducerConfig;

/**
 *
 * @author abnormal
 */
public class ProducerUtils {

    public static final Properties getProducerProperties() {
        Properties kafkaProducerProperties = new Properties();
        kafkaProducerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, System.getProperty("bootstrap_servers"));
        kafkaProducerProperties.put(ProducerConfig.CLIENT_ID_CONFIG, System.getProperty("client_id"));
        kafkaProducerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG
                , "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProducerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG
                , "org.apache.kafka.common.serialization.StringSerializer");
        return kafkaProducerProperties;
    }
    
}
