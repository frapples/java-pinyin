package io.github.frapples.javapinyin.api;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 18-9-16
 */
public class GuiceContext {

    public static final Injector injector = Guice.createInjector();

    public static <T> T getBean(Class<T> clazz) {
        return injector.getInstance(clazz);
    }

}
