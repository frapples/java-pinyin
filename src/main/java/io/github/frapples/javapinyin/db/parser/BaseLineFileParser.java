package io.github.frapples.javapinyin.db.parser;

import io.github.frapples.javapinyin.api.exception.JavaPinyinException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 18-9-16
 */
public abstract class BaseLineFileParser implements Iterable<Item> {

    protected final BufferedReader reader;

    private Item next;

    public BaseLineFileParser(InputStreamReader inputStreamReader) {
        this.reader = new BufferedReader(inputStreamReader);
    }

    @Override
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            @Override
            public boolean hasNext() { // hashNext应保证幂等性
                if (next == null) {
                    next = readNext();
                }
                return next != null;
            }

            @Override
            public Item next() {
                Item c = next;
                next = null;
                return c;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    private Item readNext() {
        try {
            String line = reader.readLine();
            while (line != null) {
                Item item = parseLine(line);
                if (item != null) {
                    return item;
                }

                line = reader.readLine();
            }
            return null;
        } catch (IOException e) {
            throw new JavaPinyinException(e);
        }
    }

    protected abstract Item parseLine(String line);
}
