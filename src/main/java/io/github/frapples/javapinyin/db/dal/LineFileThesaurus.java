package io.github.frapples.javapinyin.db.dal;

import com.google.common.collect.Iterators;
import com.google.inject.Singleton;
import io.github.frapples.javapinyin.api.exception.JavaPinyinException;
import io.github.frapples.javapinyin.db.Config;
import io.github.frapples.javapinyin.db.parser.CharFileParser;
import io.github.frapples.javapinyin.db.parser.Item;
import io.github.frapples.javapinyin.db.parser.WordFileParser;
import io.github.frapples.javapinyin.utils.FileUtils;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 18-9-16
 */
@Singleton
public class LineFileThesaurus implements Thesaurus {

    @Override
    public List<String> getPinyinForChar(char chinese) {
        return null;
    }

    @Override
    public List<String> getPinyinForWord(String word) {
        return null;
    }

    @Override
    public List<Character> getHansForPinyin(String pinyin) {
        return null;
    }

    @Override
    public List<String> getWordForPinyin(List<String> pinyin) {
        return null;
    }

    public static Iterator<Item> loadCharWordFile() {
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
