package io.github.frapples.javapinyin.db.dal;

import com.google.common.base.Splitter;
import io.github.frapples.javapinyin.api.exception.JavaPinyinException;
import io.github.frapples.javapinyin.db.Config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 18-9-16
 */
public class SqliteThesaurus implements Thesaurus {

    private Connection conn;

    public SqliteThesaurus() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite::resource:" + Config.SQLITE_INDEX_FILE_PATH);
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

    private List<String> selectByHans(String hans) {
        try {
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT pinyin FROM pinyin_thesaurus WHERE hans = ?;");
            stmt.setString(1, hans);
            ResultSet rs = stmt.executeQuery();

            List<String> result = new ArrayList<String>();
            while (rs.next()) {
                String pinyin = rs.getString("pinyin");
                result.add(pinyin);
            }
            stmt.close();
            return result;
        } catch (SQLException e) {
            throw new JavaPinyinException(e);
        }
    }
}
