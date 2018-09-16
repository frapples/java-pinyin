package io.github.frapples.javapinyin.api;

import io.github.frapples.javapinyin.api.constants.Style;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 18-9-16
 */
class JavaPinyinTest {

    @Test
    void chineseToPinyin() throws InterruptedException {
        List<List<String>> result = JavaPinyin.pinyin("中心", Style.TONE3);
        System.out.println(result);
    }

    @Test
    void chineseToPinyinForWord() {
        List<String> result = JavaPinyin.pinyinForWord("一举两得",Style.TONE2);
        System.out.println(result);
    }
}