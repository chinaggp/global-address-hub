package com.globaladdresshub.address.config;

import com.globaladdresshub.address.exception.ApiErrorCode;
import com.globaladdresshub.address.exception.ApiException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.Instant;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class RateLimitConfig implements WebMvcConfigurer {

    private final int limitPerMinute;
    private final Map<String, Deque<Long>> requests = new ConcurrentHashMap<>();

    public RateLimitConfig(@Value("${app.rate-limit.random-address-per-minute:60}") int limitPerMinute) {
        this.limitPerMinute = limitPerMinute;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RandomAddressRateLimitInterceptor()).addPathPatterns("/api/address/random");
    }

    private class RandomAddressRateLimitInterceptor implements HandlerInterceptor {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
            String key = request.getRemoteAddr() == null ? "unknown" : request.getRemoteAddr();
            long now = Instant.now().toEpochMilli();
            removeExpiredRequests(now);
            Deque<Long> timestamps = requests.computeIfAbsent(key, ignored -> new ArrayDeque<>());
            synchronized (timestamps) {
                if (timestamps.size() >= limitPerMinute) {
                    throw new ApiException(ApiErrorCode.RATE_LIMITED);
                }
                timestamps.addLast(now);
            }
            return true;
        }
    }

    void removeExpiredRequests(long now) {
        long windowStart = now - 60_000L;
        requests.forEach((key, timestamps) -> {
            synchronized (timestamps) {
                while (!timestamps.isEmpty() && timestamps.peekFirst() < windowStart) {
                    timestamps.removeFirst();
                }
                if (timestamps.isEmpty()) {
                    requests.remove(key, timestamps);
                }
            }
        });
    }
}
