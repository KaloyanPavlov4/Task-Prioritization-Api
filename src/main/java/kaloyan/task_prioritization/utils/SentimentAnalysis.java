package kaloyan.task_prioritization.utils;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import java.util.List;
import java.util.Properties;

public final class SentimentAnalysis {
    private static final StanfordCoreNLP pipeline = createPipeline();

    private static StanfordCoreNLP createPipeline() {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        return new StanfordCoreNLP(props);
    }

    public static String getSentiment(String text) {
        Annotation annotation = new Annotation(text);
        pipeline.annotate(annotation);
        CoreMap sentence = ((List<CoreMap>)annotation.get(CoreAnnotations.SentencesAnnotation.class)).get(0);
        return (String)sentence.get(SentimentCoreAnnotations.SentimentClass.class);
    }
}
