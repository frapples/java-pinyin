CREATE TABLE pinyin_thesaurus (
    id INTEGER PRIMARY KEY,
    hans VARCHAR(20) NOT NULL,
    pinyin_non_tone VARCHAR(30) NOT NULL,
    pinyin VARCHAR(30) NOT NULL
);

CREATE INDEX hans_index ON pinyin_thesaurus(hans);
CREATE INDEX pinyin_non_tone_index ON pinyin_thesaurus(pinyin_non_tone);
