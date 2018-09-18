package io.github.frapples.javapinyin.db.dal;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.inject.Singleton;
import io.github.frapples.javapinyin.api.exception.JavaPinyinException;
import io.github.frapples.javapinyin.db.Config;
import io.github.frapples.javapinyin.utils.ConnectionExtDecorator;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 18-9-16
 */
@Singleton
public class SqliteThesaurus implements Thesaurus {

    private ConnectionExtDecorator connection;

    public SqliteThesaurus() throws ClassNotFoundException, SQLException {
        this.connection = ConnectionExtDecorator.connect("jdbc:sqlite::resource:" + Config.SQLITE_INDEX_FILE_PATH);
        this.connection.exec("PRAGMA case_sensitive_like = ON;");
    }

    @Override
    public List<String> getPinyinForChar(char chinese) {
        return this.selectByHans(String.valueOf(chinese));
    }

    @Override
    public List<String> getPinyinForWord(String word) {
        List<String> result = this.selectByHans(word);
        if (result.isEmpty()) {
            return Collections.emptyList();
        } else {
            return Splitter.on(" ").trimResults().splitToList(result.get(0));
        }
    }

    @Override
    public List<Character> getHansForPinyin(String pinyin) {
        List<String> result = selectByPinyin(pinyin);
        return Lists.transform(result, new Function<String, Character>() {
            @Override
            public Character apply(String input) {
                return input.charAt(0);
            }
        });
    }

    @Override
    public List<String> getWordForPinyin(List<String> pinyin) {
        return selectByPinyin(Joiner.on(" ").join(pinyin));
    }

    @Override
    public boolean hasPrefix(String prefix) {
        try {
            String sql = "SELECT count(*) FROM pinyin_thesaurus WHERE hans like ?";
            String likeCond = prefix + "%";
            return connection.queryForCount(sql, likeCond) >= 1;
        } catch (SQLException e) {
            throw new JavaPinyinException(e);
        }
    }

    private List<String> selectByHans(String hans) {
        try {
            ResultSet rs = connection.query(
                "SELECT pinyin FROM pinyin_thesaurus WHERE hans = ?;",
                hans);
            List<String> result = new ArrayList<String>();
            while (rs.next()) {
                String pinyin = rs.getString("pinyin");
                result.add(pinyin);
            }
            return result;
        } catch (SQLException e) {
            throw new JavaPinyinException(e);
        }
    }

    private List<String> selectByPinyin(String pinyin) {
        try {
            ResultSet rs = connection.query("SELECT hans FROM pinyin_thesaurus WHERE pinyin_non_tone = ?;",
                pinyin);
            List<String> result = new ArrayList<String>();
            while (rs.next()) {
                String hans = rs.getString("hans");
                result.add(hans);
            }
            return result;
        } catch (SQLException e) {
            throw new JavaPinyinException(e);
        }
    }

}
