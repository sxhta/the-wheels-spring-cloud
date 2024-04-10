package com.sxhta.cloud.cache.redis.configure;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.inject.Singleton;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serial;
import java.io.Serializable;

/**
 * redis配置
 */
@Singleton
@EnableCaching
@Configuration
@AutoConfigureBefore(RedisAutoConfiguration.class)
public class RedisConfiguration<K, V> implements CachingConfigurer, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Bean
    public RedisTemplate<K, V> redisTemplate(RedisConnectionFactory factory) {
        //配置redisTemplate
        final var redisTemplate = new RedisTemplate<K, V>();
        redisTemplate.setConnectionFactory(factory);
        // 使用StringRedisSerializer来序列化和反序列化redis的key值
        final var stringSerializer = new StringRedisSerializer();
        // 用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
        final var jackson2JsonRedisSerializer = serializer();
        //key序列化
        redisTemplate.setKeySerializer(stringSerializer);
        //value序列化
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        //Hash key序列化
        redisTemplate.setHashKeySerializer(stringSerializer);
        //Hash value序列化
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * 配置Jackson2JsonRedisSerializer序列化策略
     */
    private Jackson2JsonRedisSerializer<?> serializer() {
        //设置序列化
        final var objectMapper = new ObjectMapper();
        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会抛出异常
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        //解决 LocalDateTime 序列化问题
        final var javaTimeModule = new JavaTimeModule();
        objectMapper.registerModule(javaTimeModule);
        // 不存在的字段,不被序列化
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return new Jackson2JsonRedisSerializer<>(objectMapper, Object.class);
    }
}
