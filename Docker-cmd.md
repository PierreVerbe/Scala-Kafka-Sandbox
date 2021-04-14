# Docker-cmd

## Useful commands
* Builds, (re)creates, starts, and attaches to containers for a service <br>
```bash
$ docker-compose -f [docker-compose file] up -d
```

* Starts existing containers for a service <br>
```bash
$ docker-compose start
```

* Stops running containers without removing them <br>
```bash
$ docker-compose stop
```

* Show all top level images, their repository and tags, and their size <br>
```bash
$ docker images
```
  
* List containers <br>
```bash
$ docker ps
```

* Run a command in a running container <br>
```bash
$ docker exec -it [container name] /bin/sh
```

* Exit a container terminal <br>
```bash
$ control + d
```

## Create a kafka topic
```bash
$ docker-compose -f docker-compose.yml up -d
$ docker-compose exec broker bash
$ kafka-topics.sh --create --zookeeper zookeeper:2181 --replication-factor 1 --partition 1 --topic Topic_Name
$ kafka-topics.sh --list --zookeeper zookeeper:2181
```

## Notes
* Official Confluent Docker Image for Kafka (Community Version)  <br>
  https://hub.docker.com/r/confluentinc/cp-kafka
  
* Official Confluent Docker Image for Zookeeper <br>
  https://hub.docker.com/r/confluentinc/cp-zookeeper
  
* Setup a multi kafka broker <br>
  https://betterprogramming.pub/kafka-docker-run-multiple-kafka-brokers-and-zookeeper-services-in-docker-3ab287056fd5
