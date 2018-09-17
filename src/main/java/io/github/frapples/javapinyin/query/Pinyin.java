package io.github.frapples.javapinyin.query;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.inject.Singleton;
import io.github.frapples.javapinyin.api.GuiceContext;
import io.github.frapples.javapinyin.api.constants.Style;
import io.github.frapples.javapinyin.db.dal.Thesaurus;
import io.github.frapples.javapinyin.query.style.Converter;
import java.util.ArrayList;
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


    public List<List<String>> sentenceToPinyin(String chinese, Style style) {

        List<List<String>> result = new ArrayList<List<String>>();
        for (char c : chinese.toCharArray()) {
            List<String> pinyins = thesaurus.getPinyinForChar(c);
            pinyins = stylify(pinyins, style);
            result.add(pinyins);
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
