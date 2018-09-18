package io.github.frapples.javapinyin.utils;

import static org.junit.jupiter.api.Assertions.*;

import com.google.common.base.Supplier;
import com.google.common.collect.Iterators;
import java.util.Arrays;
import java.util.Iterator;
import org.junit.jupiter.api.Test;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/9/18
 */
class IteratorUtilsTest {

    @Test
    void create() {

        /*
         * for (int i = 0; i < 10; i++)
         * */
        Iterator<Integer> iter = IteratorUtils.create(new Supplier<Integer>() {
            private int i = 0;

            @Override
            public Integer get() {
                if (!(i < 10)) {
                    throw IteratorUtils.END_OF_ITERATOR_EXCEPTION;
                }
                int returnV = i;
                i++;
                return returnV;
            }
        });

        Integer[] arr = Iterators.toArray(iter, Integer.class);
        System.out.println(Arrays.toString(arr));
    }
}