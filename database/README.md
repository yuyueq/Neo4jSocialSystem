> Neo4j 数据库的导出与导入
 
1.数据库导出

> neo4j-admin dump --database=<database> --to=<XX/XXX/XX.db.dump>

2.数据库导入

>neo4j-admin load --from=<XX/XXX/XX.db.dump> --database=<database> [–force]

实例：

执行数据导出命令

>./neo4j-admin  dump --database=graph.db --to=/soft/graph.db.dump


执行数据导入命令
>neo4j-admin load --from=/soft/graph.db.dump --database=graph.db --force



---




## linux 环境，导入导出



先停止



```plain
cd /opt/neo4j/neo4j-community-3.5.5/bin/
./neo4j stop
```



导出



```plain
./neo4j-admin  dump --database=graph.db --to=/opt/neo4j/graph.db.dump
```



导入



```plain
./neo4j-admin load --from=/home/robot/Neoj_data/graph.db.dump --database=graph.db --force
```



启动



```plain
./neo4j start
```



## windows 下导入导出（管理员权限打开 cmd）



导出



```plain
neo4j-admin dump --database=graph.db --to=D:\database\neo4j-community-3.5.6-windows\graph.db.dump
```



导入



```plain
neo4j-admin load --from=D:\database\neo4j-community-3.5.6-windows\graph.db.dump --database=graph.db --force
```



启动



```plain
neo4j.bat console
```



访问
http://localhost:7474/



## cypher 语句语法



**-------------- 查询 --------------**



查询全部



```plain
match(n) return n
```



查询特定属性的



```plain
match(n) 
where n.code='lsj' 
return n;
```



```plain
match(n{name:'Tom Hanks'}) 
return n;
```



模糊查询



```plain
MATCH (n) where n.showname =~ '.*测试.*' RETURN n
```



查询具有指定 Lable 的节点



```plain
match(n:OP) 
return n;
```



**-------------- 创建 --------------**



```plain
create (n:Person {showname:'李',unit:'人',code:'li',des:'',type:'Simple',title:'李2',innercode:'li'}) return n;
```



```plain
create (n:AM:GL {showname:'收入',unit:'元',code:'SR_GL_AM',des:'',type:'Simple',title:'收入',innercode:'SR_GL_AM'}) return n;
```



**-------------- 更新 --------------**

为节点增加或修改属性



```plain
match (n)
where n.code='li'
set n.title = '李2',
n.showname = '李1'
return n;
```



为节点增加标签



```plain
match (n)
where id(n)=7
set n:Company
return n;
```



为节点更新标签



```plain
match (n)
where n.code='LSJ'
set n:GL:AM remove n:GL2:AM2
return n;
```



同时更新 label 和属性



```plain
match (n)
where n.code='LSJ'
remove n:GL2:AM2 set n:GL:AM 
set n = {code:'LSJ',innercode:'LSJ',type:'Simple',title:'办公费2',showname:'办公费2',unit:'元',des:'2'}
```



为关系增加属性



```plain
match (n)<-[r]-(m)
where id(n)=7 and id(m)=8
set r.team='Azure'
return n;
```



**-------------- 创建关系 --------------**

创建没有任何属性的关系



```plain
MATCH (a:Person),(b:Movie)
WHERE a.name = 'Robert Zemeckis' AND b.title = 'Forrest Gump'
CREATE (a)-[r:DIRECTED]->(b)
RETURN r;
```



创建关系，并设置关系的属性



```plain
MATCH (a:Person),(b:Movie)
WHERE a.name = 'Tom Hanks' AND b.title = 'Forrest Gump'
CREATE (a)-[r:ACTED_IN { roles:['Forrest'] }]->(b)
RETURN r;
```



```plain
MATCH (a),(b)
WHERE a.code = 'li' AND b.code = 'li2'
CREATE (a)-[r:Sum_to {cal_grp: ["1"], cal_pl: ["1"], title: ["线性增强"], roles: ["Sum"]}]->(b)
RETURN r;
```



**-------------- 查询关系 --------------**

查询跟指定节点有关系的节点



```plain
match(a)-[r]->(b) where a.code = 'A_JZS' return a, b
```



为关系命名，通过 [r] 为关系定义一个变量名，通过函数 type 获取关系的类型



```plain
MATCH (a{code: 'lsj' })-[r]->(b{ code: 'lsj2' })
RETURN r,type(r);
```



查询特定的关系类型，通过 [Variable:RelationshipType{Key:Value}] 指定关系的类型和属性



```plain
MATCH (a{ code: 'LSJ' })-[r:Sum_to{cal_grp:["1"],cal_pl:["1"],title:["线性增强"],roles:["Sum"]}]->(b{ code: 'LSJ2' })
RETURN r,type(r);
```



**-------------- 删除 --------------**

【1】先删关系，再删节点

【2】当记不得关系名时，type(r) 可以查到关系名

【3】彻底删除节点标签名，需要删除前期对该标签名建立的索引



查看某个节点或每一类节点的所有关系



```plain
MATCH (n:LSJ)-[r]-() RETURN n,r
```



```plain
MATCH (n{code:'lsj'})-[r]-() RETURN n,r
```



删除节点，以及与之相关的所有关系



```plain
match(n) where n.showname =~ '.*测试.*' delete n

match(n)-[r]->(b) where b.code = 'A_LSJ_1' delete r

MATCH (n:LSJ)-[r]-() DELETE n,r
```



```plain
MATCH (n{code:'lsj'})-[r]-() DELETE  n,r
```



```plain
match(n) 
where n.code='li2' 
delete n;
```



**-------------- 清空全部数据 --------------**

正常情况下，需要先删除所有的关系，才能删除节点



```plain
MATCH p = ()-[r]->() delete p //删除关系
MATCH (n) delete n //删除节点
```



另一种快速清库方法



```plain
MATCH (n) detach delete n
```



统计所有节点个数：



```plain
MATCH (n) RETURN count(*)
```





