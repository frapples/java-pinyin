package io.github.frapples.javapinyin.db.parser;

import com.google.common.base.Splitter;
import io.github.frapples.javapinyin.utils.HexUtils;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 18-9-16
 */
public class CharFileParser extends BaseLineFileParser {

    public CharFileParser(InputStreamReader inputStreamReader) {
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

         // U+9F96
        String character = uniStringToString(result.get(0));
        if (character.isEmpty()) {
            return null;
        }

        List<String> pinyins = Splitter.on(",").trimResults().splitToList(result.get(1));
        return new Item(character, pinyins);
    }

    private String skipComment(String line) {
        List<String> result = Splitter.on("#").splitToList(line);
        if (result.size() <= 0 || result.get(0).isEmpty()) {
            return "";
        }
        return result.get(0);
    }

    private String uniStringToString(String uniStr) {
        String hex = uniStr.replace("U+", "");
        if (hex.length() > 4) { // 坑爹java，char双字节
            return "";
        }
        return String.valueOf(HexUtils.chr(hex));
    }
}
