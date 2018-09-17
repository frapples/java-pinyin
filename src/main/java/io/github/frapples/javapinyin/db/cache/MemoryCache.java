package io.github.frapples.javapinyin.db.cache;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Supplier;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import io.github.frapples.javapinyin.db.parser.Item;
import io.github.frapples.javapinyin.query.style.NormalConverter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.inject.Inject;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 18-9-16
 */
public class MemoryCache {

    @Inject
    private NormalConverter normalConverter;

    private volatile Map<String, Item> hansIndexCache;
    private volatile ImmutableMultimap<String, Item> pinyinIndexCache;
    private Supplier<Iterator<Item>> load;

    public MemoryCache(Supplier<Iterator<Item>> load) {
        this.load = load;
    }

    public Map<String, Item> getHansIndexCache() {
        if (hansIndexCache == null) {
            synchronized (this) {
                if (hansIndexCache == null) {
                    loadCache();
                }
            }
        }
        return this.hansIndexCache;
    }

    public ImmutableMultimap<String, Item> getPinyinIndexCache() {
        if (pinyinIndexCache == null) {
            synchronized (this) {
                if (pinyinIndexCache == null) {
                    loadCache();
                }
            }
        }
        return this.pinyinIndexCache;
    }

    private void loadCache() {
        final Map<String, Item> hansIndexCache = new ConcurrentHashMap<String, Item>();
        final Multimap<String, Item> pinyinIndexCache = HashMultimap.create();

        Iterator<Item> iterator = this.load.get();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            String hans = item.getCharacter();
            hansIndexCache.put(hans, item);

            if (hans.length() == 1) {
                for (String pinyin : item.getPinyins()) {
                    pinyinIndexCache.put(normalConverter.convert(pinyin), item);
                }
            } else if (hans.length() > 1) {
                List<String> pinyins = Lists.transform(item.getPinyins(), new Function<String, String>() {
                    @Override
                    public String apply(String input) {
                        return normalConverter.convert(input);
                    }
                });
                pinyinIndexCache.put(Joiner.on(" ").join(pinyins), item);
            }
        }
        this.hansIndexCache = hansIndexCache;
        this.pinyinIndexCache = ImmutableMultimap.copyOf(pinyinIndexCache);
    }
}
