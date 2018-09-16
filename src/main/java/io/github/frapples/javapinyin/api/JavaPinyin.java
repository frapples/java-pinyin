package io.github.frapples.javapinyin.api;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.github.frapples.javapinyin.db.Thesaurus;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 18-9-16
 */
public class JavaPinyin {

    private static Injector injector = Guice.createInjector();
    private static Thesaurus thesaurus = injector.getInstance(Thesaurus.class);

    public static List<List<String>> chineseToPinyin(String chinese) {

        List<List<String>> result = new ArrayList<List<String>>();
        for (char c : chinese.toCharArray()) {
            result.add(thesaurus.getPinyin(c));
        }
        return result;
    }
}
