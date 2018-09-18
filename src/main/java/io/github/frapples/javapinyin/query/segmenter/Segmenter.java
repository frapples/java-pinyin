package io.github.frapples.javapinyin.query.segmenter;

import java.util.Iterator;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/9/18
 */
public interface Segmenter {

    Iterator<String> cut(String sentence);
}
