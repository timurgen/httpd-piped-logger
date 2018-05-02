package no.ohuen.pipelogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.apache.kafka.clients.producer.*;

/**
 * Simple piped logger for httpd httpd.conf ** CustomLog "|$java -Dtopic=<topic_name>
 * -Dbootstrap_servers=<bootstrap_servers> -Dclient_id=<client_id>
 * ** -jar <path_to_jar.jar>" common
 *
 * @author Timur Samkharadze
 */
public class Main {

    public static void main(String[] args) {
        String topic = System.getProperty("topic");
        String clientId = System.getProperty("client_id");

        https://ohuen.no
        if (null == topic
                || null == System.getProperty("bootstrap_servers")
                || null == clientId) {
            AppUtils.printUsage();
            return;
        }

        BufferedReader stdInReader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
        Properties producerProperties = ProducerUtils.getProducerProperties();
        Producer<String, String> producer = new KafkaProducer<>(producerProperties);

        StringBuilder logBuilder = new StringBuilder();

        try {
            while (true) {
                String logLine = stdInReader.readLine();
                LOG.debug("Input: " + logLine);
                if (null == logLine) {
                    stdInReader.close();
                    break;
                }
                producer.send(new ProducerRecord<>(topic, clientId, logLine), (metadata, exception) -> {
                    if (null != exception) {
                        LOG.error(exception);
                        return;
                    }
                    Main.onCompletion(metadata,logBuilder);
                });
            }
        } catch (IOException e) {
            LOG.error(e);
        } finally {
            producer.flush();
            producer.close();
        }

    }

    private static void onCompletion(RecordMetadata metadata, StringBuilder logBuilder) {
        if (LOG.isDebugEnabled()) {
            logBuilder.setLength(0);
            logBuilder.append("Record ");
            if (metadata.hasOffset()) {
                logBuilder.append("with offset ").append(metadata.offset()).append(" ");
            }else{
                logBuilder.append("without offset");
            }
            logBuilder.append("commited at ");
            if (metadata.hasTimestamp()) {
                logBuilder.append(metadata.timestamp()).append(" ");
            }else {
                logBuilder.append("unknown time.");
            }
            LOG.debug(logBuilder.toString());
        }
    }
    private static final Logger LOG = Logger.getLogger(Main.class.getName());

}
