import com.graphaware.reco.generic.config.SimpleConfig;
import com.graphaware.reco.generic.result.Recommendation;
import com.graphaware.reco.neo4j.engine.Neo4jTopLevelDelegatingRecommendationEngine;
import com.graphaware.test.integration.ServerIntegrationTest;
import engines.TVShowRecommendationEngine;
import engines.UserRecommendationEngine;
import org.junit.Test;
import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

import java.util.List;

public class MyRecommendationEngineTest extends ServerIntegrationTest {

    @Test
    public void test() {
        try (Transaction tx = getDatabase().beginTx()) {
            getDatabase().execute("CREATE" +
                    "(d:Device {title: 'Device'}), " +
                    "(t1:TVShow {title: 'BBC Proms'}), " +
                    "(t2:TVShow {title: 'Glastonbury'}), " +
                    "(t3:TVShow {title: 'The News'}), " +
                    "(t4:TVShow {title: 'Classic FM'}), " +
                    "(g1:Genre {name: 'Concert'}), " +
                    "(g2:Genre {name: 'News'}), " +
                    "(g3:Genre {name: 'Classical Music'}), " +
                    "(g1)<-[:GENRE]-(t1), (g3)<-[:GENRE]-(t1), (g1)<-[:GENRE]-(t2), " +
                    "(g1)<-[:GENRE]-(t4), (g2)<-[:GENRE]-(t3), (g3)<-[:GENRE]-(t4), (d)-[:WATCHED]->(t1)");

            Neo4jTopLevelDelegatingRecommendationEngine engine = new UserRecommendationEngine();

            Node bbcProms = getDatabase().findNode(DynamicLabel.label("Device"), "title", "Device");

            List<Recommendation<Node>> recommendations = engine.recommend(bbcProms, new SimpleConfig(2));

            Recommendation<Node> rec = recommendations.get(0);

            tx.success();

        }

    }

}
