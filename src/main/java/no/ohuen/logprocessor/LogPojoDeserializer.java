package no.ohuen.logprocessor;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import nl.basjes.parse.core.Parser;
import nl.basjes.parse.core.exceptions.DissectionFailure;
import nl.basjes.parse.core.exceptions.InvalidDissectorException;
import nl.basjes.parse.core.exceptions.MissingDissectorsException;
import nl.basjes.parse.httpdlog.HttpdLoglineParser;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

/**
 * Deserializer for httpd log strings
 * @author abnormal
 * @param <T>
 */
public class LogPojoDeserializer<T> implements Deserializer<T> {

    private static final String FORMAT = "%h %l %u %t \"%r\" %>s %b";
    private final Parser<LogPojo> logParser;

    public LogPojoDeserializer() {
        this.logParser = new HttpdLoglineParser<>(LogPojo.class, FORMAT);
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        //noop
    }

    @Override
    @SuppressWarnings("unchecked")
    public T deserialize(String topic, byte[] bytes) {
        if (bytes == null) {
            return null;
        }

        T data;
        try {
            data = (T) this.logParser.parse(new String(bytes, StandardCharsets.UTF_8));
        } catch (DissectionFailure | InvalidDissectorException | MissingDissectorsException e) {
            throw new SerializationException(e);
        }
        return data;
    }

    @Override
    public void close() {
        //noop
    }

}
