package com.scio.quantum.ai.pii.process;

import com.scio.quantum.ai.pii.models.geocoordinates.GeoCoordinate;
import com.scio.quantum.ai.pii.models.geocoordinates.GeoCoordinatePosition;
import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.*;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations;

import com.scio.quantum.ai.pii.models.file.CandidateFile;
import com.scio.quantum.ai.pii.models.job.Job;
import com.scio.quantum.ai.pii.models.namedentity.NamedEntity;
import com.scio.quantum.ai.pii.models.namedentity.NamedEntityClass;
import com.scio.quantum.ai.pii.models.namedentity.NamedEntityPosition;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DetectPIIProcessor implements Processor {

    private int maxLength = 151;
    private int minLength = 3;

    @Override
    public void process(Exchange exchange) throws Exception {
        Job job = exchange.getProperty("JOB",Job.class);
        ArrayList<String> customTerms = job.getCustomTerms();
        Set<String> customTermsSet = new HashSet<String>(customTerms);
        CandidateFile cf = exchange.getIn().getBody(CandidateFile.class);

        cf = detectNamedEntities(cf,customTermsSet);
        cf = detectGeospatialCoordinates(cf);

        exchange.getOut().setBody(cf,CandidateFile.class);
    }

    private CandidateFile detectNamedEntities(CandidateFile cf,Set<String> customTermsSet) throws IOException, ClassNotFoundException {
        String serializedClassifier = "edu/stanford/nlp/models/ner/english.all.3class.distsim.crf.ser.gz";
        AbstractSequenceClassifier<CoreLabel> classifier = CRFClassifier.getClassifier(serializedClassifier);
        List<List<CoreLabel>> out = classifier.classify(cf.getContent());

        ArrayList<NamedEntity> namedEntities = new ArrayList<>();
        for (List<CoreLabel> sentence : out) {
            for (CoreLabel word : sentence) {
                if(word.get(CoreAnnotations.AnswerAnnotation.class).equalsIgnoreCase("PERSON")){
                    String lemma = word.word();
                    if((lemma.length()<maxLength)&&(lemma.length()>minLength)){
                        if(!isCustomTerm(lemma,customTermsSet)) {
                            double confidence = 0.0;
                            int start = word.beginPosition();
                            int stop = word.endPosition();
                            NamedEntityPosition nep = new NamedEntityPosition(start, stop);
                            NamedEntityClass nec = NamedEntityClass.PERSON;
                            NamedEntity ne = new NamedEntity(lemma, confidence, nep, nec);
                            namedEntities.add(ne);
                        }
                    }
                }
            }
        }
        if(namedEntities != null){
            cf.setNamedEntities(namedEntities);
        }
        return cf;

    }
    /*
    Detect Coordinates of the following form:
        50°4'17.698"north, 14°24'2.826"east
        50d4m17.698N 14d24m2.826E
        40:26:46N,79:56:55W
        40:26:46.302N 79:56:55.903W
        40°26′47″N 79°58′36″W
        40d 26′ 47″ N 79d 58′ 36″ W
        40.446195N 79.948862W
        40,446195° 79,948862°
        40° 26.7717, -79° 56.93172
        40.446195, -79.948862
     */
    private CandidateFile detectGeospatialCoordinates(CandidateFile cf){
        String geoPattern = ".*([-+]?)([\\d]{1,2})(((\\.)(\\d+)(,)))(\\s*)(([-+]?)([\\d]{1,3})((\\.)(\\d+))?)*.";
        Pattern pattern = Pattern.compile(geoPattern);
        String content = cf.getContent();
        Matcher matcher = pattern.matcher(content);
        ArrayList<GeoCoordinate> geoCoordinates = new ArrayList<>();
        while(matcher.find()){
            int start = matcher.start();
            int stop = matcher.end();

            String occurence = content.substring(start,stop);
            GeoCoordinatePosition gcp = new GeoCoordinatePosition(start,stop-1);
            GeoCoordinate go = new GeoCoordinate(occurence,gcp);

            geoCoordinates.add(go);

        }


        if(geoCoordinates != null){
            cf.setGeoCoordinates(geoCoordinates);
        }
        return cf;
    }
    private boolean isCustomTerm(String lemma,Set<String> customTermsSet){
        return customTermsSet.contains(lemma);
    }
}
