package io.github.frapples.javapinyin.query.style;

import com.google.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 18-9-16
 */
@Singleton
public class Tone2Converter implements Converter {

    private final Map<Character, String> symbolTable = new HashMap<Character, String>() {{
        put('ā', "a1");
        put('á', "a2");
        put('ǎ', "a3");
        put('à', "a4");
        put('ē', "e1");
        put('é', "e2");
        put('ě', "e3");
        put('è', "e4");
        put('ō', "o1");
        put('ó', "o2");
        put('ǒ', "o3");
        put('ò', "o4");
        put('ī', "i1");
        put('í', "i2");
        put('ǐ', "i3");
        put('ì', "i4");
        put('ū', "u1");
        put('ú', "u2");
        put('ǔ', "u3");
        put('ù', "u4");
        put('ü', "v");
        put('ǖ', "v1");
        put('ǘ', "v2");
        put('ǚ', "v3");
        put('ǜ', "v4");
        put('ń', "n2");
        put('ň', "n3");
        put('ǹ', "n4");
        put('\u1e3f', "m2");
    }};

    @Override
    public String convert(String pinyin) {
        StringBuilder sb = new StringBuilder();
        for (char c : pinyin.toCharArray()) {
            if (symbolTable.containsKey(c)) {
                sb.append(symbolTable.get(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
