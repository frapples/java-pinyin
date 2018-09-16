package io.github.frapples.javapinyin.db.dal;

import com.google.common.base.Supplier;
import io.github.frapples.javapinyin.db.cache.MemoryCache;
import io.github.frapples.javapinyin.db.parser.Item;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 18-9-16
 */
public class MemoryThesaurus implements Thesaurus {

    private MemoryCache memoryCache = new MemoryCache(new Supplier<Iterator<Item>>() {
        @Override
        public Iterator<Item> get() {
            return LineFileThesaurus.loadCharWordFile();
        }
    });

    @Override
    public List<String> getPinyinForChar(char chinese) {
        Item item = memoryCache.getCache().get(String.valueOf(chinese));
        return item == null ? Collections.<String>emptyList() : item.getPinyins();
    }

    @Override
    public List<String> getPinyinForWord(String word) {
        Item item = memoryCache.getCache().get(word);
        return item == null ? Collections.<String>emptyList() : item.getPinyins();
    }


}
