package com.ckl;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration("192.168.19.90");
        JedisConnectionFactory factory = new JedisConnectionFactory(configuration);
        factory.afterPropertiesSet();

        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setDefaultSerializer(new GenericFastJsonRedisSerializer());
        StringRedisSerializer serializer = new StringRedisSerializer();
        template.setKeySerializer(serializer);
        template.setHashKeySerializer(serializer);

        template.afterPropertiesSet();
        template.setEnableTransactionSupport(true);

        try {
            List<Object> txResults = template.execute(new SessionCallback<List<Object>>() {
                @Override
                public List<Object> execute(RedisOperations operations) throws DataAccessException {

                    operations.multi();

                    operations.opsForValue().set("test_long", 1);
                    int i = 1/0;
                    operations.opsForValue().increment("test_long", 1);

                    // This will contain the results of all ops in the transaction
                    return operations.exec();
                }
            });

        } catch (Exception e) {
            System.out.println("error");
            e.printStackTrace();
        }
        System.out.println("Number of items added to set: ");
    }
}
