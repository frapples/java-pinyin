package io.github.frapples.javapinyin.query.breaker;

import java.util.Iterator;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/9/18
 */
public interface SentenceBreaker {

    Iterator<String> cut(String sentence);
}
