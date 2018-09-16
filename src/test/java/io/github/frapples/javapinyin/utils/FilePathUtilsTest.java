package io.github.frapples.javapinyin.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 18-9-16
 */
class FilePathUtilsTest {

    @Test
    void join() {
        String p1 = FilePathUtils.join("/tmp", "path", "to", "file.txt");
        String p2 = FilePathUtils.join("/tmp", "path/", "to", "file.txt");
        System.out.println(p1);
        assertEquals(p1, p2);
    }
}