package cn.yuyueq.social.domain.relationship;

import cn.yuyueq.social.domain.node.Hobby;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@Data
@RelationshipProperties
public class Like {
    @Id
    private Long id;
    @TargetNode
    private Hobby hobby;
}