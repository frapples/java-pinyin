package io.github.frapples.javapinyin.api;

import io.github.frapples.javapinyin.api.constants.Style;
import io.github.frapples.javapinyin.query.Pinyin;
import java.util.List;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 18-9-16
 *
 * This is a facade class of the library.
 */
public class JavaPinyin {

    private static Pinyin pinyin = GuiceContext.getBean(Pinyin.class);

    public static List<List<String>> pinyin(String chinese) {
        return pinyin(chinese, Style.TONE, false);
    }

    public static List<List<String>> pinyin(String chinese, Style style) {
        return pinyin(chinese, style, false);
    }

    public static List<List<String>> pinyin(String chinese, Style style, boolean heteronym) {
        return pinyin.sentenceToPinyin(chinese, style, heteronym);
    }

    public static List<Character> pinyinToHans(String pinyinStr) {
        return pinyin.pinyinToHans(pinyinStr);
    }

    public static List<String> pinyinToWord(List<String> pinyinStr) {
        return pinyin.pinyinToWord(pinyinStr);
    }

    public static List<String> pinyinForHans(char hans) {
        return pinyin.pinyinForHans(hans, Style.TONE);
    }

    public static List<String> pinyinForHans(char hans, Style style) {
        return pinyin.pinyinForHans(hans, style);
    }

    public static List<String> pinyinForWord(String word) {
        return pinyin.wordToPinyin(word, Style.TONE);
    }

    public static List<String> pinyinForWord(String word, Style style) {
        return pinyin.wordToPinyin(word, style);
    }
}
