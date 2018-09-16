package io.github.frapples.javapinyin.api;

import org.junit.jupiter.api.Test;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 18-9-16
 */
class JavaPinyinTest {

    @Test
    void chineseToPinyin() {
        System.out.println(JavaPinyin.chineseToPinyin("中心"));
    }
}