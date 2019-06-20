package io.github.frapples.javapinyin.query;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.inject.Singleton;
import io.github.frapples.javapinyin.api.GuiceContext;
import io.github.frapples.javapinyin.api.constants.SegmentStrategy;
import io.github.frapples.javapinyin.api.constants.Style;
import io.github.frapples.javapinyin.db.dal.Thesaurus;
import io.github.frapples.javapinyin.query.segmenter.ChineseSegmenter;
import io.github.frapples.javapinyin.query.segmenter.Segmenter;
import io.github.frapples.javapinyin.query.style.Converter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 18-9-16
 */
@Singleton
public class Pinyin {

    @Inject
    private Thesaurus thesaurus;

    private Segmenter segmenter = new ChineseSegmenter(SegmentStrategy.FORWARD_MAXIMUM_MATCHING);

    public List<List<String>> sentenceToPinyin(String chinese, Style style, boolean heteronym) {
        List<List<String>> result = new ArrayList<List<String>>();

        if (heteronym) {
            for (char c : chinese.toCharArray()) {
                List<String> pinyins = thesaurus.getPinyinForChar(c);
                pinyins = stylify(pinyins, style);
                result.add(pinyins);
            }
        } else {
            Iterator<String> segs = segmenter.cut(chinese);
            while (segs.hasNext()) {
                String seg = segs.next();
                if (seg.length() == 1) {
                    List<String> pinyin = thesaurus.getPinyinForChar(seg.charAt(0));
                    pinyin = stylify(pinyin, style);
                    result.add(Collections.singletonList(pinyin.get(0)));
                } else if (seg.length() >= 1) {
                    List<String> pinyin = thesaurus.getPinyinForWord(seg);
                    pinyin = stylify(pinyin, style);
                    for (String p : pinyin) {
                        result.add(Collections.singletonList(p));
                    }
                } else {
                    result.add(Collections.<String>emptyList());
                }

            }
        }
        return result;
    }

    public List<String> wordToPinyin(String word, Style style) {
        List<String> result = thesaurus.getPinyinForWord(word);
        return stylify(result, style);
    }

    public List<Character> pinyinToHans(String pinyin) {
        return thesaurus.getHansForPinyin(pinyin);
    }

    private List<String> stylify(List<String> s, Style style) {
        final Converter converter = GuiceContext.getBean(style.getConverter());
        return Lists.transform(s, new Function<String, String>() {
            @Override
            public String apply(String input) {
                return converter.convert(input);
            }
        });
    }

    public List<String> pinyinToWord(List<String> pinyin) {
        return thesaurus.getWordForPinyin(pinyin);
    }

    public List<String> pinyinForHans(char hans, Style style) {
        List<String> result = thesaurus.getPinyinForChar(hans);
        return stylify(result, style);
    }
}
