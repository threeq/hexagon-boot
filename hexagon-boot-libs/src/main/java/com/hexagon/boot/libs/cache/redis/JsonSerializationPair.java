package com.hexagon.boot.libs.cache.redis;

import org.springframework.data.redis.serializer.RedisElementReader;
import org.springframework.data.redis.serializer.RedisElementWriter;
import org.springframework.data.redis.serializer.RedisSerializationContext;

public class JsonSerializationPair<T> implements RedisSerializationContext.SerializationPair<T> {

    private final RedisElementReader<T> reader;
    private final RedisElementWriter<T> writer;

    public JsonSerializationPair() {
        RedisElementJsonReaderWriter<T> readerAndWriter = new RedisElementJsonReaderWriter<>();
        this.reader = readerAndWriter;
        this.writer = readerAndWriter;
    }

    @Override
    public RedisElementReader<T> getReader() {
        return reader;
    }

    @Override
    public RedisElementWriter<T> getWriter() {
        return writer;
    }
}


