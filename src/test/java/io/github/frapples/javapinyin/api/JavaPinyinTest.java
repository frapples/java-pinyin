package io.github.frapples.javapinyin.api;

import io.github.frapples.javapinyin.api.constants.Style;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 18-9-16
 */
class JavaPinyinTest {

    static {
        JavaPinyin.init();
    }

    @Test
    void chineseToPinyin() throws InterruptedException {
        List<List<String>> result = JavaPinyin.pinyin("中心", Style.TONE3);
        System.out.println(result);
    }

    @Test
    void chineseToPinyinForWord() {
        List<String> result = JavaPinyin.pinyinForWord("一举两得");
        System.out.println(result);
    }

    @Test
    void pinyinToHans() {
        List<Character> result = JavaPinyin.pinyinToHans("a");
        System.out.println(result);
    }

    @Test
    void pinyinToWord() {
        List<String> result = JavaPinyin.pinyinToWord(Arrays.asList("yi", "shi", "er", "niao"));
        System.out.println(result);
        result = JavaPinyin.pinyinToWord(Arrays.asList("zhong", "dian"));
        System.out.println(result);
    }

    @Test
    void pinyinForHans() {
        List<String> result = JavaPinyin.pinyinForHans('中');
        System.out.println(result);
        result = JavaPinyin.pinyinForHans('中', Style.TONE3);
        System.out.println(result);
    }

    @Test
    void pinyin() {
        List<List<String>> result = JavaPinyin.pinyin("中文自动分词指的是使用计算机自动对中文文本进行词语的切分");
        System.out.println(result);
    }

    @Test
    void segment() {
        List<String> result = JavaPinyin.segment("中文自动分词指的是使用计算机自动对中文文本进行词语的切分");
        System.out.println(result);
    }
}