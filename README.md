# httpd-piped-logger
Simple httpd piped kafka logger that sends httpd access logs to Apache Kafka.

### Usage
add to httpd.conf

*CustomLog "|java -Dtopic=<topic_name> -Dbootstrap_servers=<bootstrap_servers> -Dclient_id=<client_id> -jar <path_to_jar.jar>" common*

where common is alias to your log format. Use javaw instead of java if you run windows.
