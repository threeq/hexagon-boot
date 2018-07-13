package com.hexagon.boot.libs.cache.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisElementReader;
import org.springframework.data.redis.serializer.RedisElementWriter;
import org.springframework.lang.Nullable;

import java.nio.ByteBuffer;

public class RedisElementJsonReaderWriter<T> implements RedisElementReader<T>, RedisElementWriter<T> {

    private final @Nullable
    Jackson2JsonRedisSerializer<T> serializer;

    public RedisElementJsonReaderWriter() {
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        this.serializer = jackson2JsonRedisSerializer;
    }

    @Nullable
    @Override
    public T read(ByteBuffer buffer) {
        return serializer.deserialize(buffer.array());
    }

    @Override
    public ByteBuffer write(T element) {
        return ByteBuffer.wrap(serializer.serialize(element));
    }
}
