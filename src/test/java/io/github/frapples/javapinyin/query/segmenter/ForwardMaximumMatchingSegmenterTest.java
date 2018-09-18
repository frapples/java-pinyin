package io.github.frapples.javapinyin.query.segmenter;

import com.google.common.collect.Iterators;
import io.github.frapples.javapinyin.api.GuiceContext;
import java.util.Arrays;
import java.util.Iterator;
import org.junit.jupiter.api.Test;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/9/18
 */
class ForwardMaximumMatchingSegmenterTest {

    private ForwardMaximumMatchingSegmenter chineseSentenceBreaker = GuiceContext.getBean(ForwardMaximumMatchingSegmenter.class);

    @Test
    void cut() {
        String sentence = "中文自动分词指的是使用计算机自动对中文文本进行词语的切分，即像英文那样使得中文句子中的词之间有空格以标识。中文自动分词被认为是中文自然语言处理中的一个最基本的环节。 ";
        Iterator<String> result = chineseSentenceBreaker.cut(sentence);
        String[] arr = Iterators.toArray(result, String.class);
        System.out.println(Arrays.toString(arr));
    }
}