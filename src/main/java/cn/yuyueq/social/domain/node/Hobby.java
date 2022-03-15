package cn.yuyueq.social.domain.node;

import cn.yuyueq.social.domain.BaseNode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.neo4j.core.schema.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Node("Hobby")
public class Hobby extends BaseNode {
    @Property(name = "hname")
    private String hname;
    @Property(name = "htype")
    private String htype;
    public Hobby(String hname) {
        this.hname = hname;
    }
}