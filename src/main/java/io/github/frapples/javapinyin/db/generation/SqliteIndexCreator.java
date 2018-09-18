package io.github.frapples.javapinyin.db.generation;

import io.github.frapples.javapinyin.api.GuiceContext;
import io.github.frapples.javapinyin.db.dal.DbRow;
import io.github.frapples.javapinyin.db.dal.LineFileThesaurus;
import io.github.frapples.javapinyin.db.parser.Item;
import io.github.frapples.javapinyin.query.style.NormalConverter;
import io.github.frapples.javapinyin.utils.ConnectionExtDecorator;
import io.github.frapples.javapinyin.utils.FileUtils;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import javax.inject.Inject;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 18-9-16
 */
public class SqliteIndexCreator {

    @Inject
    private NormalConverter normalConverter;

    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
        String path = "src/main/resources/data/index.sqlite";
        GuiceContext.getBean(SqliteIndexCreator.class).create(path);
    }

    public void create(String path) throws ClassNotFoundException, IOException, SQLException {
        ConnectionExtDecorator conn = null;
        try {
            conn = ConnectionExtDecorator.connect("jdbc:sqlite:" + path);
            conn.exec(FileUtils.getResourceContext("data/create.sql", "UTF-8"));
            insertData(conn);
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    private void insertData(ConnectionExtDecorator conn) throws SQLException {
        conn.setAutoCommit(false);
        Iterator<Item> iter = LineFileThesaurus.loadCharWordFile();
        while (iter.hasNext()) {
            Item item = iter.next();
            for (DbRow dbRow : DbRow.of(item, normalConverter)) {
                insert(conn, dbRow);
            }
        }
        conn.commit();
    }

    private void insert(ConnectionExtDecorator conn, DbRow dbRow) throws SQLException {
        conn.exec(
            "INSERT INTO pinyin_thesaurus (hans, pinyin_non_tone, pinyin) VALUES (?, ?, ?);",
            dbRow.getHans(), dbRow.getPinyinNonTone(), dbRow.getPinyin()
        );
    }
}
