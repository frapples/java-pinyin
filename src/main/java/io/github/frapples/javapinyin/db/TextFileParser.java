package io.github.frapples.javapinyin.db;

import io.github.frapples.javapinyin.api.exception.JavaPinyinException;
import com.google.common.base.Splitter;
import io.github.frapples.javapinyin.db.TextFileParser.Item;
import io.github.frapples.javapinyin.utils.HexUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 18-9-16
 */
public class TextFileParser implements Iterable<Item> {

    @Data
    @AllArgsConstructor
    public static class Item {

        String character;
        List<String> pinyins;
    }

    private final BufferedReader reader;

    private Item next;

    public TextFileParser(InputStreamReader inputStreamReader) {
        this.reader = new BufferedReader(inputStreamReader);
    }

    @Override
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            @Override
            public boolean hasNext() {
                next = readNext();
                return next != null;
            }

            @Override
            public Item next() {
                return next;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    private Item readNext() {
        try {
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    return null;
                }
                Item item = parse(line);
                if (item != null) {
                    return item;
                }
            }
        } catch (IOException e) {
            throw new JavaPinyinException(e);
        }
    }

    private Item parse(String line) {
        line = this.skipComment(line);
        if (line.isEmpty()) {
            return null;
        }

        List<String> result = Splitter.on(":").trimResults().splitToList(line);
        if (result.size() != 2) {
            return null;
        }

        String character = uniStringToString(result.get(0)); // U+9F96
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
