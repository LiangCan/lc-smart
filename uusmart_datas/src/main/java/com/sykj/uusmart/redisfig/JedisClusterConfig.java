package com.sykj.uusmart.redisfig;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisCluster;

@Configuration
@ConditionalOnClass({JedisCluster.class})
public class JedisClusterConfig {

//    @Value("${spring.redis.cache.clusterNodes}")
//    String nodes;
//
//    @Value("${spring.redis.cache.commandTimeout}")
//    Integer timeout;


//    @Primary
//    @RefreshScope
//    @Bean("redisTemplate")
    //没有此属性就不会装配bean 如果是单个redis 将此注解注释掉
//    @ConditionalOnProperty( name="spring.redis.cluster.nodes" , matchIfMissing=false)
//    public RedisTemplate<String, Object> getRedisTemplate(RedisConnectionFactory factory) {
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
//        redisTemplate.setConnectionFactory(factory);
//
//        RedisSerializer stringSerializer = new StringRedisSerializer();
//        RedisSerializer redisObjectSerializer = new RedisObjectSerializer();
//        redisTemplate.setKeySerializer(stringSerializer); // key的序列化类型
//        redisTemplate.setHashKeySerializer(stringSerializer);
//        redisTemplate.setValueSerializer(redisObjectSerializer); // value的序列化类型
//        redisTemplate.afterPropertiesSet();
//        return redisTemplate;
//    }

    @RefreshScope
    @Bean("stringRedisTemplate")
    @ConditionalOnProperty(name="spring.redis.host", matchIfMissing=false)
    public StringRedisTemplate getStringRedisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate stringRedisTemplate =  new StringRedisTemplate(factory);
//        stringRedisTemplate.opsForValue().set("123","444");
//        System.out.println(stringRedisTemplate.opsForValue().get("123"));
        return stringRedisTemplate;
    }

    @Primary
    @Bean("redisTemplate")
    @ConditionalOnProperty( name="spring.redis.host" , matchIfMissing=true)
    public RedisTemplate<String, Object> getSingleRedisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setKeySerializer(new StringRedisSerializer()); // key的序列化类型
        redisTemplate.setValueSerializer(new RedisObjectSerializer()); // value的序列化类型
        return redisTemplate;
    }

//    public static void main(String[] args){
//        String nodes = "119.23.238.27:7000,119.23.238.27:7001,119.23.238.27:7002,119.23.238.27:7003,119.23.238.27:7004,119.23.238.27:7005";
//        Integer timeout = 5000;
//        String[] serverArray = nodes.split(",");
//        Set<HostAndPort> node = new HashSet<>();
//        for (String ipPort: serverArray) {
//            String[] ipPortPair = ipPort.split(":");
//            node.add(new HostAndPort(ipPortPair[0].trim(),Integer.valueOf(ipPortPair[1].trim())));
//        }
//        JedisCluster jedisCluster = new JedisCluster(node, timeout);
//        String  idStr =  jedisCluster.get();
//
//    }

}