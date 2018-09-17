package io.github.frapples.javapinyin.query.style;

import com.google.inject.Singleton;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 18-9-16
 */
@Singleton
public class ToneConverter implements Converter {

    @Override
    public String convert(String pinyin) {
        return pinyin;
    }
}
