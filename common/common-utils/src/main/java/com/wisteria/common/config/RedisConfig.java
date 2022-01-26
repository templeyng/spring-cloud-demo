package com.wisteria.common.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.instrument.util.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.lettuce.pool.max-idle}")
    int maxIdle;
    @Value("${spring.redis.lettuce.pool.max-active}")
    int maxActive;
    @Value("${spring.redis.lettuce.pool.max-wait}")
    long maxWaitMillis;
    @Value("${spring.redis.lettuce.pool.min-idle}")
    int minIdle;
    @Value("${spring.redis.timeout}")
    int timeout;

    @Bean(name = "productRedisTemplateBean")
    public RedisTemplate productRedisTemplate(@Value("${spring.redis.database.db1}") int database,
                                              @Value("${spring.redis.host}") String hostName,
                                              @Value("${spring.redis.port}") int port,
                                              @Value("${spring.redis.password}") String password) {
        RedisTemplate temple = new RedisTemplate();
        temple.setConnectionFactory(connectionFactory(database, hostName, port, password));
        setSerializerFun(temple);
        return temple;
    }

    @Bean(name = "stockRedisTemplateBean")
    public RedisTemplate stockRedisTemplate(@Value("${spring.redis.database.db2}") int database,
                                            @Value("${spring.redis.host}") String hostName,
                                            @Value("${spring.redis.port}") int port,
                                            @Value("${spring.redis.password}") String password) {
        RedisTemplate temple = new RedisTemplate();
        temple.setConnectionFactory(connectionFactory(database, hostName, port, password));
        setSerializerFun(temple);
        return temple;
    }

    @Bean(name = "purchaseRedisTemplateBean")
    public RedisTemplate purchaseRedisTemplate(@Value("${spring.redis.database.db3}") int database,
                                               @Value("${spring.redis.host}") String hostName,
                                               @Value("${spring.redis.port}") int port,
                                               @Value("${spring.redis.password}") String password) {
        RedisTemplate temple = new RedisTemplate();
        temple.setConnectionFactory(connectionFactory(database, hostName, port, password));
        setSerializerFun(temple);
        return temple;
    }

    @Bean(name = "saleRedisTemplateBean")
    public RedisTemplate saleRedisTemplate(@Value("${spring.redis.database.db4}") int database,
                                           @Value("${spring.redis.host}") String hostName,
                                           @Value("${spring.redis.port}") int port,
                                           @Value("${spring.redis.password}") String password) {
        RedisTemplate temple = new RedisTemplate();
        temple.setConnectionFactory(connectionFactory(database, hostName, port, password));
        setSerializerFun(temple);
        return temple;
    }

    /**
     * 使用lettuce配置Redis连接信息
     *
     * @param database Redis数据库编号
     * @param hostName 服务器地址
     * @param port     端口
     * @param password 密码
     * @return RedisConnectionFactory
     */
    public RedisConnectionFactory connectionFactory(int database, String hostName, int port, String password) {

        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(hostName);
        configuration.setPort(port);
        if (StringUtils.isNotBlank(password)) {
            configuration.setPassword(password);
        }
        if (database != 0) {
            configuration.setDatabase(database);
        }

        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxIdle(maxIdle);
        genericObjectPoolConfig.setMinIdle(minIdle);
        genericObjectPoolConfig.setMaxTotal(maxActive);
        genericObjectPoolConfig.setMaxWaitMillis(maxWaitMillis);

        LettuceClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
                .commandTimeout(Duration.ofMillis(timeout))
                .poolConfig(genericObjectPoolConfig)
                .build();

        LettuceConnectionFactory lettuce = new LettuceConnectionFactory(configuration, clientConfig);
        lettuce.afterPropertiesSet();
        return lettuce;
    }

    private void setSerializerFun(RedisTemplate<String, Object> template) {
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // value序列化方式采用jackson
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // hash的value序列化方式采用jackson
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
    }

}