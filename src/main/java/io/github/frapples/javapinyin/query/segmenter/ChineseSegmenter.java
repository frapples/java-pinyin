package io.github.frapples.javapinyin.query.segmenter;

import io.github.frapples.javapinyin.api.GuiceContext;
import io.github.frapples.javapinyin.api.constants.SegmentStrategy;
import java.util.Iterator;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/9/18
 * 中文分词，策略类
 */
public class ChineseSegmenter implements Segmenter {

    private Segmenter segmenter;

    public ChineseSegmenter(SegmentStrategy segmentStrategy) {
        segmenter = GuiceContext.getBean(segmentStrategy.getSegmenterClass());
    }

    @Override
    public Iterator<String> cut(String sentence) {
        return segmenter.cut(sentence);
    }
}
