# 1 mybatis-eden

实现纯粹的Mybatis启动

通过一下方式查询

```java
User user = sqlSession.selectOne("com.loserico.mybatis.mapper.UserMapper.selectById", 1)
```

配置了logback.xml实现SQL打印