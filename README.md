# Topcoder - Challenge Forum Processor

## Dependencies

- nodejs https://nodejs.org/en/ (v10+)
- Kafka
- Mongodb

## Configuration

Configuration for the processor is at `config/default.js`.
The following parameters can be set in config files or in env variables

- LOG_LEVEL: the log level; default value: 'debug'
- TC_AUTHN_URL: TC_AUTHN_URL, used to get the access token
- TC_USERNAME : TC USERNAME, used to make TC_AUTHN_REQUEST_BODY for getting the access token
- TC_PASSWORD: TC PASSWORD, used to make TC_AUTHN_REQUEST_BODY for getting the access token
- TC_CLIENT_ID: TC CLIENT ID is client id which is used to make TC_AUTHN_REQUEST_BODY for getting the access token
- CLIENT_V2CONNECTION : CLIENT V2 CONNECTION, used to make TC_AUTHN_REQUEST_BODY for getting the access token
- TC_AUTHZ_URL: TC AUTHZ URL , used to get the access token
- KAFKA_URL : comma separated Kafka hosts for consumer to listen; default value: 'localhost:9092'
- KAFKA_CLIENT_CERT: Kafka connection certificate, optional; default value is undefined; if not provided, then SSL connection is not used, direct insecure connection is used; if provided, it can be either path to certificate file or certificate content
- KAFKA_CLIENT_CERT_KEY: Kafka connection private key, optional; default value is undefined; if not provided, then SSL connection is not used, direct insecure connection is used; if provided, it can be either path to private key file or private key content
- MONGODB_URL: Mongodb uri , used to get connected with mongodb
- USER_API_URL: User api url, used to get the user info ; default value : 'https://api.topcoder-dev.com/v3'
- CREATE_CHALLENGE_TOPIC: create challenge kafka topic; default value : ''challenge.notification.create'
- CHALLENGE_EVENT_TOPIC : create challenge event kafka topic which is user registration and unregistration from challenge; default value: 'challenge.notification.events'
- CREATE_CHALLENGE_RESOURCE_TOPIC : create challenge resource kafka topic; default value: 'challenge.action.resource.create'
- DELETE_CHALLENGE_RESOURCE_TOPIC: delete challenge resource kafka topic; default value: 'challenge.action.resource.delete'

## Heroku deployment 

- Download and install the heroku CLI, download and installation link `https://devcenter.heroku.com/articles/getting-started-with-nodejs#set-up`
- Go to heroku and create new app.
- Login to heroku using your cli by typing "heroku login" and then enter your credentials
- Initialize a git repository in existing directory 
``` 
$ git init
$ heroku git:remote -a <your project> 
```
- Deploy your application Commit your code to the repository and deploy it to Heroku using Git.
``` 
$ git add .
$ git commit -am "make it better"
$ git push heroku master
```
#### NOTE you should have Procfile in your root directory for running your node app and below code in the procfile

``` 
Worker : node src/app.js
```
#### NOTE
You can use the mongolab for your mongodb database and provide the mongodb uri using environment variable `MONGODB_URL`

## Local Kafka setup

- `http://kafka.apache.org/quickstart` contains details to setup and manage Kafka server,
  below provides details to setup Kafka server in Mac, Windows will use bat commands in bin/windows instead
- download kafka at `https://www.apache.org/dyn/closer.cgi?path=/kafka/1.1.0/kafka_2.11-1.1.0.tgz`
- extract out the downloaded tgz file
- go to extracted directory kafka_2.11-0.11.0.1
- start ZooKeeper server:
  `bin/zookeeper-server-start.sh config/zookeeper.properties`
- use another terminal, go to same directory, start the Kafka server:
  `bin/kafka-server-start.sh config/server.properties`
- note that the zookeeper server is at localhost:2181, and Kafka server is at localhost:9092
- Use another terminal, go to same directory, create some topics:
  `bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic challenge.notification.create`
  `bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic challenge.notification.events`
  `bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic challenge.action.resource.create`
  `bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic challenge.action.resource.delete`
- Verify that the topics are created:
  `bin/kafka-topics.sh --list --zookeeper localhost:2181`,
  it should list out the created topics
- run the producer and then write some message into the console to send to the `challenge.notification.events` topic:
  `bin/kafka-console-producer.sh --broker-list localhost:9092 --topic challenge.notification.events`
  in the console, write message, one message per line:
  `{   "topic": "challenge.notification.events",   "originator": "topcoder-challenges-api",   "timestamp": "2019-11-24T23:26:28.926Z",   "mime-type": "application/json",   "payload": {     "type": "USER_UNREGISTRATION",     "data": {   "challengeId": "b0b96d7f-2e7b-4a1d-a894-165e55b01d2d",       "userId": 40158988     }   }`
- Optionally, use another terminal, go to same directory, start a consumer to view the messages:
  `bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic challenge.notification.events --from-beginning`
- If the kafka don't allow to input long message you can use this script to write message from file:
  `path_to_kafka/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic challenge.notification.events < our_project_root_directory/test/data/project/challenge.notification.events.json`
- Writing/reading messages to/from other topics are similar. All example for messages are in:
`our_project_root_directory/test/data`

## Local Mongodb setup
- download the mongodb from `https://www.mongodb.com/download-center/community`
- Install it in your os , for installing doc `https://docs.mongodb.com/manual/administration/install-community/`
- Run the mongodb localy , default mongodb runs on 27017 port

## Local deployment

- Install dependencies `npm i`
- Run code lint check `npm run lint`, running `npm run lint:fix` can fix some lint errors if any
- Start processor app `npm start`

## Verification
Refer to the verification document `Verification.md`
