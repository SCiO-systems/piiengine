package com.scio.quantum.ai.pii.server;

import com.mongodb.MongoClient;
import com.scio.quantum.ai.pii.route.APIRouter;
import com.scio.quantum.ai.pii.route.PIIRouter;
import org.apache.camel.component.kafka.KafkaComponent;
import org.apache.camel.main.Main;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;

public class PIIMicroservice extends Main {

    public static void main(String[] args) throws Exception {

        boolean apiEnabled = true;

        PIIMicroserviceOptions piimo = new PIIMicroserviceOptions();
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse( piimo.createOptions(), args);

//      String ip = cmd.getOptionValue("ip");
        String hostIp = cmd.getOptionValue("hostIp");
        String hostPort = cmd.getOptionValue("hostPort");
        String kafkaIp = cmd.getOptionValue("kafkaIp");
        String kafkaPort = cmd.getOptionValue("kafkaPort");
        String uiLocation = cmd.getOptionValue("uiLocation");
        String mongoIP = cmd.getOptionValue("mongoIp");
        String mongoPort = cmd.getOptionValue("mongoPort");
        String elasticSearchIp = cmd.getOptionValue("elasticSearchIp");
        String elasticsearchClusterName = cmd.getOptionValue("elasticsearchClusterName");

//        String mongoDatabaseName = cmd.getOptionValue("mongoDatabaseName");
//        String mongoCollectionName = cmd.getOptionValue("mongoCollectionName");


        System.out.println("hostIp : "+hostIp);
        System.out.println("hostPort : "+hostPort);
        System.out.println("kafkaIp : "+kafkaIp);
        System.out.println("kafkaPort : "+kafkaPort);
        System.out.println("mongoIP : "+mongoIP);
        System.out.println("mongoPort : "+mongoPort);
        System.out.println("elasticSearchIp : "+elasticSearchIp);
        System.out.println("elasticsearchClusterName : "+elasticsearchClusterName);
        System.out.println("uiLocation : "+uiLocation);

        KafkaComponent kafka = new KafkaComponent();

        kafka.setBrokers(kafkaIp+":"+kafkaPort);
        String kafkaBroker = kafkaIp+":"+kafkaPort;

        PIIMicroservice piim = new PIIMicroservice();

        //piim.bind("sslContextParameters", parameters);
        piim.bind("kafka",kafka);
        piim.bind("mongoConnection",new MongoClient(mongoIP, Integer.parseInt(mongoPort)));

        if(apiEnabled == true){
            piim.addRouteBuilder(new APIRouter(hostIp,hostPort,kafkaIp,kafkaPort));
            System.out.println("API Online");
        }
        piim.addRouteBuilder(new PIIRouter(elasticsearchClusterName,elasticSearchIp,kafkaBroker,uiLocation));

        piim.run();

    }
}
