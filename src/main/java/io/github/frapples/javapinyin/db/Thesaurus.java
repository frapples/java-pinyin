package io.github.frapples.javapinyin.db;

import io.github.frapples.javapinyin.api.exception.JavaPinyinException;
import io.github.frapples.javapinyin.db.parser.Item;
import io.github.frapples.javapinyin.db.parser.CharFileParser;
import io.github.frapples.javapinyin.db.parser.WordFileParser;
import io.github.frapples.javapinyin.utils.FileUtils;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 18-9-16
 */
public class Thesaurus {

    private volatile Map<String, Item> cache;

    public List<String> getPinyin(char chinese) {
        Item item = this.getCache().get(String.valueOf(chinese));
        return item == null ? Collections.<String>emptyList(): item.getPinyins();
    }

    public List<String> getPinyinForWord(String word) {
        Item item = this.getCache().get(word);
        return item == null ? Collections.<String>emptyList(): item.getPinyins();
    }

    private Map<String, Item> getCache() {
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
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(
                FileUtils.getResource(Config.CHAR_PINYIN_FILE_PATH).openStream(), Config.FILE_CHARSET);
            CharFileParser charFileParser = new CharFileParser(inputStreamReader);

            inputStreamReader = new InputStreamReader(
                FileUtils.getResource(Config.WORD_PINYIN_FILE_PATH).openStream(), Config.FILE_CHARSET);
            WordFileParser wordFileParser = new WordFileParser(inputStreamReader);

            Map<String, Item> cache = new ConcurrentHashMap<String, Item>();
            for (Item item : wordFileParser) {
                cache.put(item.getCharacter(), item);
            }
            for (Item item : charFileParser) {
                cache.put(item.getCharacter(), item);
            }
            return cache;
        } catch(IOException e){
            throw new JavaPinyinException(e);
        }
    }

}
