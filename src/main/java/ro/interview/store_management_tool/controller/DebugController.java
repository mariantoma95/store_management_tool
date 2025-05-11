package ro.interview.store_management_tool.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
//@Profile("local") // uncomment if remember to run with local spring profile
public class DebugController {

    private final CacheManager cacheManager;

    // Helper method to check cache evict
    @GetMapping("/debug/productBySku")
    public Map<Object, Object> productBySkuEntries() {
        Cache cache = cacheManager.getCache("productBySku");
        if (cache instanceof ConcurrentMapCache cmc) {
            return cmc.getNativeCache();
        }
        throw new IllegalStateException("productBySku is not a ConcurrentMapCache");
    }
}
