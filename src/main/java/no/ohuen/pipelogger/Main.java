package no.ohuen.pipelogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import org.apache.kafka.clients.producer.*;

/**
 * Simple piped logger for httpd
 * httpd.conf
 * ** CustomLog "|$java -Dtopic=<topic_name> -Dbootstrap_servers=<bootstrap_servers> -Dclient_id=<client_id> 
 * ** -jar <path_to_jar.jar>" common
 * @author abnormal
 */
public class Main {
    public static void main(String[] args) throws IOException {
        String topic = System.getProperty("topic");
        String clientId = System.getProperty("client_id");
        if (null == topic
                || null == System.getProperty("bootstrap_servers")
                || null == clientId) {
            AppUtils.printUsage();
            return;
        }

        BufferedReader stdInReader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
        Properties producerProperties = ProducerUtils.getProducerProperties();
        Producer<String, String> producer = new KafkaProducer<>(producerProperties);
        try {
            while (true) {
                String logLine = stdInReader.readLine();
                if (null == logLine) {
                    stdInReader.close();
                    break;
                }
                producer.send(new ProducerRecord<>(topic, clientId, logLine));

            }
        } finally {
            producer.flush();
            producer.close();
        }

    }

}
