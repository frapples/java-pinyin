package io.github.frapples.javapinyin.db;

import com.google.common.base.Supplier;
import com.google.common.collect.Iterators;
import io.github.frapples.javapinyin.api.exception.JavaPinyinException;
import io.github.frapples.javapinyin.db.cache.MemoryCache;
import io.github.frapples.javapinyin.db.parser.Item;
import io.github.frapples.javapinyin.db.parser.CharFileParser;
import io.github.frapples.javapinyin.db.parser.WordFileParser;
import io.github.frapples.javapinyin.utils.FileUtils;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 18-9-16
 */
public class Thesaurus {

    private MemoryCache memoryCache = new MemoryCache(new Supplier<Iterator<Item>>() {
        @Override
        public Iterator<Item> get() {
            return loadCharWordFile();
        }
    });

    public List<String> getPinyin(char chinese) {
        Item item = memoryCache.getCache().get(String.valueOf(chinese));
        return item == null ? Collections.<String>emptyList() : item.getPinyins();
    }

    public List<String> getPinyinForWord(String word) {
        Item item = memoryCache.getCache().get(word);
        return item == null ? Collections.<String>emptyList() : item.getPinyins();
    }


    private Iterator<Item> loadCharWordFile() {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(
                FileUtils.getResource(Config.CHAR_PINYIN_FILE_PATH).openStream(), Config.FILE_CHARSET);
            CharFileParser charFileParser = new CharFileParser(inputStreamReader);

            inputStreamReader = new InputStreamReader(
                FileUtils.getResource(Config.WORD_PINYIN_FILE_PATH).openStream(), Config.FILE_CHARSET);
            WordFileParser wordFileParser = new WordFileParser(inputStreamReader);

            return Iterators.concat(charFileParser.iterator(), wordFileParser.iterator());
        } catch (IOException e) {
            throw new JavaPinyinException(e);
        }
    }

}
