package io.github.frapples.javapinyin.utils;

import com.google.common.base.Supplier;
import java.util.Iterator;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/9/18
 */
public class IteratorUtils {

    private IteratorUtils() {
        throw new UnsupportedOperationException();
    }

    private static class EndOfIteratorException extends RuntimeException {
    }
    public static EndOfIteratorException END_OF_ITERATOR_EXCEPTION = new EndOfIteratorException();

    public static <T> Iterator<T> create(Supplier<T> nextFunc) {
        return new ReadOnlyIterator<T>(nextFunc);
    }


    private static class ReadOnlyIterator<T> implements Iterator<T> {

        ReadOnlyIterator(Supplier<T> nextFunc) {
            this.nextFunc = nextFunc;
        }

        private Supplier<T> nextFunc;
        private T next;
        private boolean isEnd = false;
        private boolean isCurrentHasNextCalled = false;

        @Override
        public boolean hasNext() {
            if (!isEnd && !isCurrentHasNextCalled) {
                try {
                    next = nextFunc.get();
                } catch (EndOfIteratorException e) {
                    isEnd = true;
                }
                isCurrentHasNextCalled = true;
            }
            return !isEnd;
        }

        @Override
        public T next() {
            isCurrentHasNextCalled = false;
            return next;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    };

}
