package provider;

import java.time.Duration;

public class DefaultCacheProvider implements CacheProvider {
    @Override
    public byte[] get(String key) {
        return new byte[0];
    }

    @Override
    public void add(String key, byte[] payload, Duration ttl) {

    }
}
