package io.github.frapples.javapinyin.query.style;

import com.google.inject.Singleton;
import javax.inject.Inject;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 18-9-16
 */
@Singleton
public class Tone3Converter implements Converter {

    @Inject
    private Tone2Converter tone2Converter;

    public Tone3Converter() {
        System.out.println("tewst");
    }

    @Override
    public String convert(String pinyin) {
        pinyin = tone2Converter.convert(pinyin);

        StringBuilder sb = new StringBuilder();
        StringBuilder end = new StringBuilder();
        for (char c : pinyin.toCharArray()) {
            if (Character.isDigit(c)) {
                end.append(c);
            } else {
                sb.append(c);
            }
        }
        return sb.toString() + end.toString();
    }
}
