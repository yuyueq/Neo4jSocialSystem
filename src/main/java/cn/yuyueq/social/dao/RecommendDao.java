package cn.yuyueq.social.dao;
import cn.yuyueq.social.domain.node.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendDao extends Neo4jRepository<User,Long> {

    //根据用户账号获取用户相关信息并查出用户关注的人来匹配关注一样的用户
    @Query("match (user:User) where user.account={account}\n" +
            "match ((user)-[:Follow]->()-[:Follow]->(p)) return distinct p")
    List<User> byFriend(@Param("account") String account);

    //根据用户账号获取用户相关信息并查出用户爱好来匹配和兴趣一样的用户
    @Query("match (user:User) where user.account={account}\n" +
            "match (user)-[:Like]->(hobbies)\n" +
            "match (users)-[:Like]->(hobbies) return users")
    List<User> byHobby(@Param("account") String account);
    //根据用户账号获取用户相关信息并查出用户发布的动态来匹配和兴趣一样的用户
    @Query("match (user:User) where user.account={account}\n" +
            "match (user)-[:Publish]->(shares)\n" +
            "match (shares)-[:Praised]->(users) return users")
    List<User> byshare(String account);
}
