package ru.clevertec.bank.cache.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.clevertec.bank.cache.Cache;
import ru.clevertec.bank.config.TestConfig;

@SpringJUnitConfig(TestConfig.class)
class LFUCacheTest {

    @Autowired
    @Qualifier("LFUCache")
    private Cache<String, Integer> cache;

    @Test
    void testPutAndGetShouldPutAnGetRightValue() {
        cache.put("key1", 1);
        cache.put("key2", 2);
        cache.put("key3", 3);

        assertEquals(1, cache.get("key1"));
        assertEquals(2, cache.get("key2"));
        assertEquals(3, cache.get("key3"));
    }

    @Test
    void testPutShouldDoEvictionWhenCacheOverflowed() {
        cache = new LFUCache<>(3);

        cache.put("key1", 1);
        cache.put("key2", 2);
        cache.put("key3", 3);

        cache.get("key3");
        cache.get("key3");
        cache.get("key1");

        cache.put("key4", 4);

        assertNull(cache.get("key2"));
        assertEquals(1, cache.get("key1"));
        assertEquals(3, cache.get("key3"));
        assertEquals(4, cache.get("key4"));
    }

    @Test
    void testGetShouldNotReturnValue() {
        assertNull(cache.get("nonExistentKey"));
    }

    @Test
    void testRemoveShouldDeleteElement() {
        cache.put("key1", 1);
        cache.put("key2", 2);

        cache.remove("key1");

        assertNull(cache.get("key1"));
        assertEquals(2, cache.get("key2"));
    }

}