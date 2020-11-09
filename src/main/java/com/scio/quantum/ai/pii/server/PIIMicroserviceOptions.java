package com.scio.quantum.ai.pii.server;

import org.apache.commons.cli.Options;

public class PIIMicroserviceOptions {

    public PIIMicroserviceOptions() {

    }

    public Options createOptions(){

        Options options = new Options();

        options.addOption("hostIp",true,"Api IP");
        options.addOption("hostPort",true,"Api Port");
        options.addOption("kafkaIp",true,"Kafka IP");
        options.addOption("kafkaPort",true,"Kafka Port");
        options.addOption("kafkaTopicName",true,"Kafka TopicName");
        options.addOption("mongoIp",true,"Mongo IP");
        options.addOption("mongoPort",true,"Mongo Port");
        options.addOption("uiLocation",true,"uiLocation IP (for email)");
        options.addOption("elasticSearchIp",true,"Elastic Search IP");
        options.addOption("elasticsearchClusterName",true,"Elastic Search Cluster Name");
        return options;
    }
}
