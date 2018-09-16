package io.github.frapples.javapinyin.db.parser;

import com.google.common.base.Splitter;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 18-9-16
 */
public class WordFileParser extends BaseLineFileParser {

    public WordFileParser(InputStreamReader inputStreamReader) {
        super(inputStreamReader);
    }

    @Override
    protected Item parseLine(String line) {
        line = this.skipComment(line);
        if (line.isEmpty()) {
            return null;
        }

        List<String> result = Splitter.on(":").trimResults().splitToList(line);
        if (result.size() != 2) {
            return null;
        }

        String character = result.get(0);
        if (character.isEmpty()) {
            return null;
        }

        List<String> pinyins = Splitter.on(" ").trimResults().splitToList(result.get(1));
        return new Item(character, pinyins);
    }

    private String skipComment(String line) {
        List<String> result = Splitter.on("#").splitToList(line);
        if (result.size() <= 0 || result.get(0).isEmpty()) {
            return "";
        }
        return result.get(0);
    }
}
