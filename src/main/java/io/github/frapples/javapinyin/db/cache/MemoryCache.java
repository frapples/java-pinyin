package io.github.frapples.javapinyin.db.cache;

import com.google.common.base.Supplier;
import io.github.frapples.javapinyin.db.parser.Item;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 18-9-16
 */
public class MemoryCache {

    private volatile Map<String, Item> cache;
    private Supplier<Iterator<Item>> load;

    public MemoryCache(Supplier<Iterator<Item>> load) {
        this.load = load;
    }

    public Map<String, Item> getCache() {
        if (cache == null) {
            synchronized (this) {
                if (cache == null) {
                    this.cache = loadCache();
                }
            }
        }
        return this.cache;
    }

    private Map<String, Item> loadCache() {
        final Map<String, Item> cache = new ConcurrentHashMap<String, Item>();

        Iterator<Item> iterator = this.load.get();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            cache.put(item.getCharacter(), item);
        }
        return cache;
    }
}
