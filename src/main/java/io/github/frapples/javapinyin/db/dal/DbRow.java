package io.github.frapples.javapinyin.db.dal;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import io.github.frapples.javapinyin.db.parser.Item;
import io.github.frapples.javapinyin.query.style.NormalConverter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 2018/9/17
 */
@Data
@AllArgsConstructor
public class DbRow {

    private String hans;
    private String pinyin;
    private String pinyinNonTone;

    public static List<DbRow> of(final Item item, final NormalConverter normalConverter) {
        if (item.getCharacter().length() == 1) {
            return Lists.transform(item.getPinyins(), new Function<String, DbRow>() {
                @Override
                public DbRow apply(String pinyin) {
                    return new DbRow(item.getCharacter(), pinyin, normalConverter.convert(pinyin));
                }
            });

        } else if (item.getCharacter().length() > 1) {
            List<String> pinyins = new ArrayList<String>(item.getPinyins());
            for (int i = 0; i < pinyins.size(); i++) {
                pinyins.set(i, normalConverter.convert(pinyins.get(i)));
            }

            DbRow row = new DbRow(item.getCharacter(),
                Joiner.on(" ").join(item.getPinyins()),
                Joiner.on(" ").join(pinyins));
            return Collections.singletonList(row);
        } else {
            Preconditions.checkArgument(false);
            return Collections.emptyList();
        }
    }
}
