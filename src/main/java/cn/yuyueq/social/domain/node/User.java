package cn.yuyueq.social.domain.node;
import cn.yuyueq.social.domain.BaseNode;
import cn.yuyueq.social.domain.relationship.Follow;
import cn.yuyueq.social.domain.relationship.Like;
import cn.yuyueq.social.domain.relationship.Publish;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.neo4j.core.schema.*;
import org.springframework.data.neo4j.core.schema.Relationship.Direction;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Node("User")
public class User extends BaseNode {
    @Property(name = "account")
    private String account;
    @Property(name = "password")
    private String password;
    @Property(name = "nickname")
    private String nickname;
    @Property(name = "age")
    private int age;
    @Property(name = "gender")
    private String gender;
    @Property(name = "email")
    private String email;
    @Property(name = "address")
    private String address;
    @Property(name = "imgurl")
    private String imgurl;

    @Relationship(type = "Follow", direction = Direction.OUTGOING)
    private List<Follow> followings;

    @Relationship(type = "Like")
    private List<Like> myhobbys;

    @Relationship(type = "Publish", direction = Direction.OUTGOING)
    private List<Publish> myshares;

    private String hobbyList;
}