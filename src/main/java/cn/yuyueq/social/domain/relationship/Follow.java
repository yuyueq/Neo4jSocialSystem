package cn.yuyueq.social.domain.relationship;

import cn.yuyueq.social.domain.node.User;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

@Data
@RelationshipProperties
public class Follow{
    @Id@GeneratedValue
    private Long id;
    @TargetNode
    private User following;
}