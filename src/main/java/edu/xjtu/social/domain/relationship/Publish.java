package edu.xjtu.social.domain.relationship;

import edu.xjtu.social.domain.node.Share;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@Data
@RelationshipProperties
public class Publish {
    @Id
    private Long id;
    @TargetNode
    private Share share;
}
