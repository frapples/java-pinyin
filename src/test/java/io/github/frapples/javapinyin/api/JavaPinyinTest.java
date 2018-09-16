package io.github.frapples.javapinyin.api;

import java.io.IOException;
import org.junit.jupiter.api.Test;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 18-9-16
 */
class JavaPinyinTest {

    @Test
    void chineseToPinyin() throws IOException {
        System.out.println(JavaPinyin.chineseToPinyin("中心"));
    }
}