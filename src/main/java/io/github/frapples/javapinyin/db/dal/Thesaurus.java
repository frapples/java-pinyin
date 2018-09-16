package io.github.frapples.javapinyin.db.dal;

import java.util.List;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 18-9-16
 */
public interface Thesaurus {

    List<String> getPinyinForChar(char chinese);

    List<String> getPinyinForWord(String word);

    List<Character> getHansForPinyin(String pinyin);

    List<String> getWordForPinyin(List<String> pinyin);
}
