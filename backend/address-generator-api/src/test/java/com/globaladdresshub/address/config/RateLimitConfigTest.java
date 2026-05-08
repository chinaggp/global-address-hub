package com.globaladdresshub.address.config;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class RateLimitConfigTest {

    @Test
    void expiredClientEntriesAreRemovedDuringCleanup() {
        RateLimitConfig config = new RateLimitConfig(10);
        ArrayDeque<Long> staleRequests = new ArrayDeque<>();
        staleRequests.add(1L);
        trackedRequests(config).put("stale-client", staleRequests);

        config.removeExpiredRequests(61_000L);

        assertThat(trackedRequests(config)).isEmpty();
    }

    @SuppressWarnings("unchecked")
    private Map<String, Deque<Long>> trackedRequests(RateLimitConfig config) {
        try {
            Field field = RateLimitConfig.class.getDeclaredField("requests");
            field.setAccessible(true);
            return (Map<String, Deque<Long>>) field.get(config);
        } catch (ReflectiveOperationException exception) {
            throw new AssertionError(exception);
        }
    }
}
