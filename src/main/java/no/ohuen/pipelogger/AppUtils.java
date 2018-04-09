package no.ohuen.pipelogger;

/**
 *
 * @author abnormal
 */
public class AppUtils {
    public static final void printUsage(){
        System.out.println("Usage:");
        System.out.println("java -Dtopic=<topic_name> -Dbootstrap_servers=<bootstrap_servers> -Dclient_id=<client_id>"
                + " -jar pipedlogger.jar");
    } 
}
