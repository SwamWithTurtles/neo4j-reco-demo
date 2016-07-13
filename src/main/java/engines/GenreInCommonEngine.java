package engines;

import com.graphaware.reco.neo4j.engine.SomethingInCommon;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.RelationshipType;

public class GenreInCommonEngine extends SomethingInCommon {
    @Override
    protected RelationshipType getType() {
        return Relationships.GENRE;
    }

    @Override
    protected Direction getDirection() {
        return Direction.BOTH;
    }

    @Override
    public String name() {
        return "Genre In Common";
    }
}

