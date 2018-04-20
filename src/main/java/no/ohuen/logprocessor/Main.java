package no.ohuen.logprocessor;

import java.util.Arrays;
import java.util.List;
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
        final Properties config = ConsumerUtils.getConsumerProperties();
        final Deserializer<LogPojo> logDeserializer = new LogPojoDeserializer<>();
        final Serializer<LogPojo> logSerializer = new LogPojoSerializer<>();
        final Serde<LogPojo> logSerde = Serdes.serdeFrom(logSerializer, logDeserializer);

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, LogPojo> textLines = builder.stream("httpd-logger", Consumed.with(Serdes.String(), logSerde));
        textLines.filterNot((k, v) -> Main.isStaticResource(v.getUri()))
                .filter((k, v) -> Arrays.asList("200", "403", "404", "500").contains(v.getStatus()));
        KafkaStreams streams = new KafkaStreams(builder.build(), config);
        streams.start();
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }

    /**
     * Returns true if String uri represent a static resource
     *
     * @param uri
     * @return
     */
    private static boolean isStaticResource(String uri) {
        if (null == uri) {
            return false;
        }
        List<String> staticResourceExtensions = Arrays.asList(
                ".js", ".css", ".map", ".png", ".jpg", ".gif", ".bmp", ".woff", ".ttf");
        String[] split = uri.split("\\?");
        return staticResourceExtensions.stream().anyMatch(extension -> (split[0].endsWith(extension)));
    }
}
