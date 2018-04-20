package no.ohuen.logprocessor;

import java.util.Arrays;
import org.apache.kafka.streams.StreamsConfig;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Consumed;

/**
 *
 * @author abnormal
 */
public class Main {

    public static void main(String[] args) {
        final Properties config = new Properties();

        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "logprocessor");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "10.47.90.206:9092");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        
        final Deserializer<LogPojo> logDeserializer = new LogPojoDeserializer<>();
        final Serializer<LogPojo> logSerializer = new LogPojoSerializer<>();
        final Serde<LogPojo> logSerde = Serdes.serdeFrom(logSerializer, logDeserializer);

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, LogPojo> textLines = builder.stream("httpd-logger", Consumed.with(Serdes.String(), logSerde));
        textLines.filter((k,v) -> Arrays.asList("403","404","500").contains(v.getStatus())).print();
        KafkaStreams streams = new KafkaStreams(builder.build(), config);
        streams.start();
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}
