package engines;

import com.graphaware.reco.generic.context.Context;
import com.graphaware.reco.generic.engine.SingleScoreRecommendationEngine;
import com.graphaware.reco.generic.result.PartialScore;
import com.graphaware.reco.generic.result.Recommendation;
import com.graphaware.reco.generic.result.Recommendations;
import com.graphaware.reco.neo4j.engine.Neo4jTopLevelDelegatingRecommendationEngine;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;

import java.util.*;

public class MetadataWatchedMoviesEngine extends SingleScoreRecommendationEngine<Node, Node> {
    @Override
    protected Map<Node, PartialScore> doRecommendSingle(Node node, Context<Node, Node> context) {
        Map<Node, PartialScore> scores = new HashMap<Node, PartialScore>();
        for (Relationship rel : node.getRelationships(Relationships.WATCHED)) {
            Node tvShow = rel.getEndNode();
            Recommendations<Node> recommendations = new TVShowRecommendationEngine().recommend(tvShow, context);

            for (Recommendation<Node> rec : new ArrayList<>(recommendations.get())) {
                mergePartialScores(scores, rec);
            }
        }
        return scores;
    }

    private void mergePartialScores(Map<Node, PartialScore> scores, Recommendation<Node> rec) {
        if (scores.containsKey(rec.getItem())) {
            scores.get(rec.getItem()).add(rec.getScore().getTotalScore());
        } else {
            scores.put(rec.getItem(), new PartialScore(rec.getScore().getTotalScore()));
        }
    }

    @Override
    public String name() {
        return "Metadata Filtering: Similar Nodes";
    }
}
