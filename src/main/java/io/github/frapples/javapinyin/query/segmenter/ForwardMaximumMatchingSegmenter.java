package io.github.frapples.javapinyin.query.segmenter;

import com.google.common.base.Strings;
import com.google.common.base.Supplier;
import io.github.frapples.javapinyin.db.dal.Thesaurus;
import io.github.frapples.javapinyin.utils.IteratorUtils;
import java.util.Iterator;
import javax.inject.Inject;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/9/18
 * 最大正向匹配分词
 */
public class ForwardMaximumMatchingSegmenter implements Segmenter {

    @Inject
    private Thesaurus thesaurus;

    @Override
    public Iterator<String> cut(final String sentence) {
        if (Strings.isNullOrEmpty(sentence)) {
            return IteratorUtils.emptyIterator();
        }

        return IteratorUtils.create(new Supplier<String>() {
            private int i = 0;

            @Override
            public String get() {
                int end = i + 1;
                if (i >= sentence.length()) {
                    throw IteratorUtils.END_OF_ITERATOR_EXCEPTION;
                }
                while (true) {
                    String word = sentence.substring(i, end);
                    if (end < sentence.length() && thesaurus.hasPrefix(word + sentence.charAt(end))) {
                        end++;
                    } else {
                        i = end;
                        return word;
                    }
                }
            }
        });
    }
}
