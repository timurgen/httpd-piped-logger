package no.ohuen.logprocessor;

import java.util.Arrays;
import org.apache.kafka.streams.StreamsConfig;
import java.util.Properties;
import nl.basjes.parse.core.Parser;
import nl.basjes.parse.core.exceptions.DissectionFailure;
import nl.basjes.parse.core.exceptions.InvalidDissectorException;
import nl.basjes.parse.core.exceptions.MissingDissectorsException;
import nl.basjes.parse.httpdlog.HttpdLoglineParser;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.log4j.Logger;

/**
 *
 * @author abnormal
 */
public class Main {
    private static final Logger LOG = Logger.getLogger(Main.class.getName());
    private static final String FORMAT = "%h %l %u %t \"%r\" %>s %b";

    public static void main(String[] args) {
        Properties config = new Properties();
        Parser<LogPojo> logParser = new HttpdLoglineParser<>(LogPojo.class, FORMAT);
        
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "logprocessor");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

        KStreamBuilder builder = new KStreamBuilder();
        KStream<String, String> textLines = builder.stream("httpd-logger");
        textLines.foreach((key, value) -> {
            try {
                LogPojo logLine = logParser.parse(value);
                if(Arrays.asList("500", "403", "404").contains(logLine.getStatus())){
                    LOG.warn(logLine.toString());
                }
            } catch (DissectionFailure | InvalidDissectorException | MissingDissectorsException ex) {
               LOG.fatal(ex.getMessage());
            }
        });
        KafkaStreams streams = new KafkaStreams(builder, config);
        streams.start();
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}
