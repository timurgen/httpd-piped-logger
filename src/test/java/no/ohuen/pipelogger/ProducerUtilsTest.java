package no.ohuen.pipelogger;

import java.util.Properties;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Timur Samkharadze
 */
public class ProducerUtilsTest {

    public ProducerUtilsTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        //there are 3 system properties that are essential for application
        //kafka bootstrap servers, topic, client id. For this test we need only 2 of them
        System.setProperty("bootstrap_servers", "test string");
        System.setProperty("client_id", "test string");
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     *
     */
    @Test
    public void testGetProducerProperties() {
        Properties result = ProducerUtils.getProducerProperties();
        assertTrue(result instanceof java.util.Properties);
    }

    @Test
    public void testPropertiesBootstrapServerExist() {
        Properties result = ProducerUtils.getProducerProperties();
        assertTrue(null != result.getProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG));
    }

    @Test
    public void testPropertiesBootstrapClientIdExist() {
        Properties result = ProducerUtils.getProducerProperties();
        assertTrue(null != result.getProperty(ProducerConfig.CLIENT_ID_CONFIG));
    }

    @Test
    public void testPropertiesBootstrapKeySerializerIdExist() {
        Properties result = ProducerUtils.getProducerProperties();
        assertTrue(null != result.getProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG));
    }

    @Test
    public void testPropertiesBootstrapValueSerializerIdExist() {
        Properties result = ProducerUtils.getProducerProperties();
        assertTrue(null != result.getProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG));
    }

}
