# 介绍及特性
将汉字转拼音，或者是将拼音转汉字的一个工具库。

1. 支持中文词汇、中文单词转拼音。
2. 支持词汇拼音、单子拼音转中文。
3. 支持音调，可支持不同风格的输出。
4. 支持语句或文章转拼音，使用分词算法解决多音字问题。
5. 词库借用python-pinyin词库，非常丰富。
6. 支持简单的分词。

关于词库，实际上还是比python-pinyin少了不少。
这是因为java作为历史悠久的语言，char是双字节，无法表示所有的unicode字符。
目前暂时不考虑超过双字节编码的汉字，之后再查找资料完善。

但是，双字节编码的已有汉字已经非常够用了，被排除在外的几乎都是十分生僻的汉字了。

后续完善：
2. 根据拼音序列，智能匹配，转句子。

# 安装及环境要求
jdk版本要求：jdk >= 1.6

TODO...

# 使用例子
## 单字或词语转换
词语转拼音，支持多种输出风格：
```java
List<String> result = JavaPinyin.pinyinForWord("一举两得");
// [yī, jǔ, liǎng, dé]

List<String> result = JavaPinyin.pinyinForWord("一举两得",Style.TONE2);
// [yi1, ju3, lia3ng, de2]

List<String> result = JavaPinyin.pinyinForWord("一举两得",Style.TONE3);
// [yi1, ju3, liang3, de2]

List<String> result = JavaPinyin.pinyinForWord("一举两得",Style.NORMAL);
// [yi, ju, liang, de]
```
单字转拼音，返回所有多音字拼音：
```java
 List<String> result = JavaPinyin.pinyinForHans('中');
 // [zhōng, zhòng]
 List<String> result = JavaPinyin.pinyinForHans('中', Style.TONE3);
 // [zhong1, zhong4]
```

单个拼音转汉字，返回所有的同音字：
```java
List<Character> result = JavaPinyin.pinyinToHans("a");
// [吖, 呵, 呵, 呵, 呵, 啊, 啊, 啊, 啊, 啊, 嗄, 嗄, 腌, 錒, 锕, 阿, 阿, 阿, 阿]
```

词汇拼音转中文，返回所有的同音词：
```java
List<String> result = JavaPinyin.pinyinToWord(Arrays.asList("zhong", "dian"));
// [中点, 重典, 重点]
```

## 句子转换

句子转拼音，智能解决多音字问题：
```java
List<List<String>> result = JavaPinyin.pinyin("中文自动分词指的是使用计算机自动对中文文本进行词语的切分");
// [[zhōng], [wén], [zì], [dòng], [fēn], [cí], [zhǐ], [de], [shì], [shǐ], [yòng], [jì], [suàn], [jī], [zì], [dòng], [duì], [zhōng], [wén], [wén], [běn], [jìn], [xíng], [cí], [yǔ], [de], [qiē], [fēn]]
```

## 分词
简单分词：
```java
List<String> result = JavaPinyin.segment("中文自动分词指的是使用计算机自动对中文文本进行词语的切分");
// [中文, 自动, 分词, 指的, 是, 使用, 计算机, 自动, 对, 中文, 文本, 进行, 词语, 的切, 分]
```

# 资源消耗
1. 内存占用方面：非常小。
2. 性能方面：第一次调用需要耗费点时间，后面调用就快。目前没有严谨的beanchmark。

# 感谢
本项目主要参考及借用了以下项目的词库，表示感谢：
1. https://github.com/mozillazg/python-pinyin
