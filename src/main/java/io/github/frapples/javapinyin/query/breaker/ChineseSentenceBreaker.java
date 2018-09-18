package io.github.frapples.javapinyin.query.breaker;

import io.github.frapples.javapinyin.db.dal.Thesaurus;
import java.util.Iterator;
import javax.inject.Inject;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/9/18
 */
public class ChineseSentenceBreaker implements SentenceBreaker {

    @Inject
    Thesaurus thesaurus;

    @Override
    public Iterator<String> cut(String sentence) {
        return null;
    }
}
