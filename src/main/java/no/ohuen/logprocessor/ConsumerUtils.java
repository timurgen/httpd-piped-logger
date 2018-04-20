package no.ohuen.logprocessor;

import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;

/**
 *
 * @author abnormal
 */
public class ConsumerUtils {

    public static final Properties getConsumerProperties() {
        final Properties config = new Properties();

        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "logprocessor");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "10.47.90.206:9092");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        return config;
    }
}
