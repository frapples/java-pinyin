package io.github.frapples.javapinyin.db.dal;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Supplier;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.Lists;
import com.google.inject.Singleton;
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
        Item item = memoryCache.getHansIndexCache().get(String.valueOf(chinese));
        return item == null ? Collections.<String>emptyList() : item.getPinyins();
    }

    @Override
    public List<String> getPinyinForWord(String word) {
        Item item = memoryCache.getHansIndexCache().get(word);
        return item == null ? Collections.<String>emptyList() : item.getPinyins();
    }

    @Override
    public List<Character> getHansForPinyin(String pinyin) {
        ImmutableCollection<Item> item = memoryCache.getPinyinIndexCache().get(pinyin);
        return Lists.newArrayList(Collections2.transform(item, new Function<Item, Character>() {
            @Override
            public Character apply(Item input) {
                return input.getCharacter().charAt(0);
            }
        }));
    }

    @Override
    public List<String> getWordForPinyin(List<String> pinyins) {
        ImmutableCollection<Item> item = memoryCache.getPinyinIndexCache().get(Joiner.on(" ").join(pinyins));
        return Lists.newArrayList(Collections2.transform(item, new Function<Item, String>() {
            @Override
            public String apply(Item input) {
                return input.getCharacter();
            }
        }));
    }
}
