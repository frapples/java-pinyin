package io.github.frapples.javapinyin.db.dal;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import io.github.frapples.javapinyin.api.GuiceContext;
import io.github.frapples.javapinyin.db.parser.Item;
import io.github.frapples.javapinyin.query.style.NormalConverter;
import io.github.frapples.javapinyin.utils.FileUtils;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 18-9-16
 */
public class SqliteIndexCreator {

    @Inject
    NormalConverter normalConverter;

    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
        String path = "src/main/resources/data/index.sqlite";
        GuiceContext.getBean(SqliteIndexCreator.class).create(path);
    }

    public void create(String path) throws ClassNotFoundException, IOException, SQLException {
        Class.forName("org.sqlite.JDBC");
        Connection c = null;
        try {
            c = DriverManager.getConnection("jdbc:sqlite:" + path);
            Statement stmt = c.createStatement();
            String sql = FileUtils.getResourceContext("data/create.sql", "UTF-8");
            stmt.executeUpdate(sql);
            stmt.close();
            insertData(c);
        } finally {
            if (c != null) {
                c.close();
            }
        }
    }

    private void insertData(Connection conn) throws SQLException {
        conn.setAutoCommit(false);
        Iterator<Item> iter = LineFileThesaurus.loadCharWordFile();
        while (iter.hasNext()) {
            Item item = iter.next();
            if (item.getCharacter().length() == 1) {
                for (String pinyin : item.getPinyins()) {
                    insert(conn, item.getCharacter(), normalConverter.convert(pinyin), pinyin);
                }

            } else if (item.getCharacter().length() > 1) {
                List<String> pinyins = new ArrayList<String>(item.getPinyins());
                for (int i = 0; i < pinyins.size(); i++) {
                    pinyins.set(i, normalConverter.convert(pinyins.get(i)));
                }
                insert(conn, item.getCharacter(),
                    Joiner.on(" ").join(pinyins),
                    Joiner.on(" ").join(item.getPinyins()));
            } else {
                Preconditions.checkArgument(false);
            }
        }
        conn.commit();
    }

    private void insert(Connection conn, String hans, String pinyinNonTone, String pinyin) throws SQLException {
        PreparedStatement st = conn.prepareStatement(
            "INSERT INTO pinyin_thesaurus (hans, pinyin_non_tone, pinyin) VALUES (?, ?, ?);");
        st.setString(1, hans);
        st.setString(2, pinyinNonTone);
        st.setString(3, pinyin);
        st.execute();
        st.close();
    }
}
