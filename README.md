# httpd-piped-logger
Simple httpd piped kafka logger that sends httpd access logs to Apache Kafka.

[![travis-ci build status](https://travis-ci.org/timurgen/httpd-piped-logger.svg)](https://travis-ci.org/timurgen/httpd-piped-logger)
[![Coverage Status](https://coveralls.io/repos/github/timurgen/httpd-piped-logger/badge.svg?branch=master)](https://coveralls.io/github/timurgen/httpd-piped-logger?branch=master)

### Usage
add to httpd.conf

*CustomLog "|java -Dtopic=<topic_name> -Dbootstrap_servers=<bootstrap_servers> -Dclient_id=<client_id> -jar <path_to_jar.jar>" common*

where common is alias to your log format. Use javaw instead of java if you run windows.
