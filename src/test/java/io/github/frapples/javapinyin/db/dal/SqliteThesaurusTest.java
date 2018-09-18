package io.github.frapples.javapinyin.db.dal;

import static org.junit.jupiter.api.Assertions.*;

import io.github.frapples.javapinyin.api.GuiceContext;
import javax.inject.Inject;
import org.junit.jupiter.api.Test;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/9/18
 */
class SqliteThesaurusTest {

    private SqliteThesaurus sqliteThesaurus = GuiceContext.getBean(SqliteThesaurus.class);

    @Test
    void hasPrefix() {
        boolean r;
        r = sqliteThesaurus.hasPrefix("一");
        assertTrue(r);
        r = sqliteThesaurus.hasPrefix("一举");
        assertTrue(r);
        r = sqliteThesaurus.hasPrefix("一举两");
        assertTrue(r);
        r = sqliteThesaurus.hasPrefix("一举两得");
        assertTrue(r);
        r = sqliteThesaurus.hasPrefix("一举两得到");
        assertFalse(r);
    }
}