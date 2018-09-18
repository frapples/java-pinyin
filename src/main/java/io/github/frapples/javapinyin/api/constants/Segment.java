package io.github.frapples.javapinyin.api.constants;

import io.github.frapples.javapinyin.query.segmenter.ForwardMaximumMatchingSegmenter;
import io.github.frapples.javapinyin.query.segmenter.Segmenter;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/9/18
 */
@AllArgsConstructor
public enum Segment {

    FORWARD_MAXIMUM_MATCHING(ForwardMaximumMatchingSegmenter.class);

    @Getter
    private Class<? extends Segmenter> segmenterClass;
}
