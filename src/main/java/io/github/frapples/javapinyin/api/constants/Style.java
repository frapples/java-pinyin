package io.github.frapples.javapinyin.api.constants;


import io.github.frapples.javapinyin.query.style.Converter;
import io.github.frapples.javapinyin.query.style.NormalConverter;
import io.github.frapples.javapinyin.query.style.Tone2Converter;
import io.github.frapples.javapinyin.query.style.Tone3Converter;
import io.github.frapples.javapinyin.query.style.ToneConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 18-9-16
 *
 * 拼音风格
 */
@AllArgsConstructor
public enum Style {
    // 普通风格，不带声调。如： 中国 -> ``zhong guo``
    NORMAL(NormalConverter.class),
    // 标准声调风格，拼音声调在韵母第一个字母上（默认风格）。如： 中国 -> ``zhōng guó``
    TONE(ToneConverter.class),
    // 声调风格2，即拼音声调在各个韵母之后，用数字 [1-4] 进行表示。如： 中国 -> ``zho1ng guo2``
    TONE2(Tone2Converter.class),
    // 声调风格3，即拼音声调在各个拼音之后，用数字 [1-4] 进行表示。如： 中国 -> ``zhong1 guo2``
    TONE3(Tone3Converter.class);

    // TODO
    /*
    // 声母风格，只返回各个拼音的声母部分（注：有的拼音没有声母，详见 `#27`_）。如： 中国 -> ``zh g``
    INITIALS(),
    // 首字母风格，只返回拼音的首字母部分。如： 中国 -> ``z g``
    FIRST_LETTER(),
    // 韵母风格，只返回各个拼音的韵母部分，不带声调。如： 中国 -> ``ong uo``
    FINALS(),
    // 标准韵母风格，带声调，声调在韵母第一个字母上。如：中国 -> ``ōng uó``
    FINALS_TONE(),
    // 韵母风格2，带声调，声调在各个韵母之后，用数字 [1-4] 进行表示。如： 中国 -> ``o1ng uo2``
    FINALS_TONE2(),
    // 韵母风格3，带声调，声调在各个拼音之后，用数字 [1-4] 进行表示。如： 中国 -> ``ong1 uo2``
    FINALS_TONE3(),
    // 注音风格，带声调，阴平（第一声）不标。如： 中国 -> ``ㄓㄨㄥ ㄍㄨㄛˊ``
    BOPOMOFO(),
    // 注音风格，仅首字母。如： 中国 -> ``ㄓ ㄍ``
    BOPOMOFO_FIRST(),
    // 汉语拼音与俄语字母对照风格，声调在各个拼音之后，用数字 [1-4] 进行表示。如： 中国 -> ``чжун1 го2``
    CYRILLIC(),
    // 汉语拼音与俄语字母对照风格，仅首字母。如： 中国 -> ``ч г``
    CYRILLIC_FIRST();
    */


    @Getter
    private Class<? extends Converter> converter;
}
