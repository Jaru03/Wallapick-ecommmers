package Wallapick.Services;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Service
public class BlacklistService {

    // A ConcurrentHashMap is used to store the tokens and their expiration time
    private final ConcurrentHashMap<String, Long> blacklist = new ConcurrentHashMap<>();
    // A ScheduledExecutorService is used to schedule the removal of expired tokens
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public void blacklistToken(String token, long expiration, TimeUnit timeUnit) {
        // Stores the token and its expiration timestamp
        blacklist.put(token, System.currentTimeMillis() + timeUnit.toMillis(expiration));
        // Schedule the removal of the token from the blacklist when it expires
        scheduler.schedule(() -> blacklist.remove(token), expiration, timeUnit);
    }

    public boolean isBlacklisted(String token) {
        return blacklist.containsKey(token);
    }
}