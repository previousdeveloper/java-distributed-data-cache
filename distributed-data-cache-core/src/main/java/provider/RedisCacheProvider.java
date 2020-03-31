package provider;

import redis.clients.jedis.Jedis;

import java.nio.charset.Charset;
import java.time.Duration;

public class RedisCacheProvider implements CacheProvider {
    Jedis jedis = new Jedis();

    @Override
    public byte[] get(String key) {
        return jedis.get(key.getBytes(Charset.defaultCharset()));
    }

    @Override
    public void add(String key, byte[] payload, Duration ttl) {
        jedis.set(key.getBytes(), payload);
    }
}
