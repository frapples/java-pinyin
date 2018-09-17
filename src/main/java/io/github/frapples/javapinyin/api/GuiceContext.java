package io.github.frapples.javapinyin.api;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import io.github.frapples.javapinyin.db.dal.SqliteThesaurus;
import io.github.frapples.javapinyin.db.dal.Thesaurus;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 18-9-16
 */
public class GuiceContext {

    private static final Injector injector = Guice.createInjector(Stage.PRODUCTION, new MainMoulde());

    public static <T> T getBean(Class<T> clazz) {
        return injector.getInstance(clazz);
    }
}

class MainMoulde extends AbstractModule {

    @Override
    protected void configure() {
        bind(Thesaurus.class).to(SqliteThesaurus.class);
    }
}
