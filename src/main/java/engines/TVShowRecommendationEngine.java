package engines;

import com.graphaware.reco.generic.engine.RecommendationEngine;
import com.graphaware.reco.generic.filter.BlacklistBuilder;
import com.graphaware.reco.generic.filter.Filter;
import com.graphaware.reco.generic.post.PostProcessor;
import com.graphaware.reco.neo4j.engine.Neo4jTopLevelDelegatingRecommendationEngine;
import engines.GenreInCommonEngine;
import org.neo4j.graphdb.Node;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TVShowRecommendationEngine extends Neo4jTopLevelDelegatingRecommendationEngine {

    @Override
    protected List<RecommendationEngine<Node, Node>> engines() {
        return Arrays.<RecommendationEngine<Node, Node>>asList(
                new GenreInCommonEngine()
        );
    }

    @Override
    protected List<PostProcessor<Node, Node>> postProcessors() {
        return Collections.emptyList(); //todo add your own post processors
    }

    @Override
    protected List<BlacklistBuilder<Node, Node>> blacklistBuilders() {
        return Collections.emptyList(); //todo add your own blacklist builders instead
    }

    @Override
    protected List<Filter<Node, Node>> filters() {
        return Collections.emptyList(); //todo add your own filters instead
    }
}
