package io.github.frapples.javapinyin.db.parser;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Frapples <isfrapples@outlook.com>
 * @date 18-9-16
 */
@Data
@AllArgsConstructor
public class Item {

    String character;
    List<String> pinyins;
}
