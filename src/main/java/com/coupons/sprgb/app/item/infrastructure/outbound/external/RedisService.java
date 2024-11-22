package com.coupons.sprgb.app.item.infrastructure.outbound.external;

import com.coupons.sprgb.app.item.domain.client.ItemAPIClient;
import com.coupons.sprgb.app.item.domain.dto.ItemDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;

@RequiredArgsConstructor
@Component
@Slf4j
public class RedisService   {
    private final StringRedisTemplate redisTemplate;



    /**
     * Set a value in Redis with an optional expiration time.
     *
     * @param key       the key to store the value under
     * @param value     the value to store
     * @param duration  expiration time (optional, can be null for no expiration)
     */
    public void set(String key, String value, Duration duration) {
        try{
            ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
            if (duration != null) {
                valueOps.set(key, value, duration);
            } else {
                valueOps.set(key, value);
            }
        }catch (Exception e){
            log.error("error in redis set:{}",e.getMessage());
        }

    }

    /**
     * Get a value from Redis by its key.
     *
     * @param key the key of the value to retrieve
     * @return the value, or null if not found
     */
    public String get(String key) {
        try{
            ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
            return valueOps.get(key);
        }catch (Exception e ){
            log.error("error in redis get:{}",e.getMessage());
            return null;
        }

    }


}
