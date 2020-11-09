package com.scio.quantum.ai.pii.aggregations;

import com.scio.quantum.ai.pii.models.namedentity.NamedEntity;
import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.util.ArrayList;

public class FoundTermsAggregator implements AggregationStrategy {

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        if(oldExchange == null){
            ArrayList<NamedEntity> namedEntities = new ArrayList();
            NamedEntity ne = newExchange.getIn().getBody(NamedEntity.class);
            namedEntities.add(ne);
            newExchange.getOut().setBody(namedEntities);
            return newExchange;
        }else{
            ArrayList<NamedEntity> namedEntities = oldExchange.getIn().getBody(ArrayList.class);
            NamedEntity ne = newExchange.getIn().getBody(NamedEntity.class);
            namedEntities.add(ne);
            newExchange.getOut().setBody(namedEntities);
            return newExchange;
        }
    }


}
