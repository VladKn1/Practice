
package com.example;
import java.util.ArrayList;
import java.util.List;
public class Word {
    private Word.WordType type;
    private String val;

    public Word.WordType getType() {
        return this.type;
    }

    public String getVal() {
        return this.val;
    }

    public Word(Word.WordType type, String val) {
        this.type = type;
        this.val = val;
    }

    public Word(Word.WordType type, Character val) {
        this.type = type;
        this.val = val.toString();
    }

    public static List<Word> WordAnalyze(String text) throws Exception {
        ArrayList<Word> Words = new ArrayList();
        int pos = 0;
        String toD = "toDollars";
        String toR = "toRubles";

        while(true) {
            while(pos < text.length()) {
                char c = text.charAt(pos);
                switch(c) {
                    case '(':
                        Words.add(new Word(Word.WordType.LBracket, c));
                        ++pos;
                        break;
                    case ')':
                        Words.add(new Word(Word.WordType.RBracket, c));
                        ++pos;
                        break;
                    case '*':
                    case ',':
                    default:
                        char f = 48;
                        if (c == '$') {
                            f = 100;
                            ++pos;
                            c = text.charAt(pos);
                        }

                        StringBuilder sb;
                        if (c <= '9' && c >= '0') {
                            sb = new StringBuilder();
                            int flag = 0;

                            do {
                                sb.append(c);
                                ++pos;
                                if (pos >= text.length()) {
                                    break;
                                }

                                c = text.charAt(pos);
                                if (c == ',') {
                                    ++flag;
                                }

                                if (flag > 1) {
                                    throw new RuntimeException("2 ,,");
                                }
                            } while(c <= '9' && c >= '0' || c == ',');

                            if (f == 100) {
                                Words.add(new Word(Word.WordType.Doll, sb.toString()));
                                boolean var10 = true;
                            } else {
                                char a = text.charAt(pos);
                                ++pos;
                                if (a == 'p' || a == 1088) {
                                    Words.add(new Word(Word.WordType.Rub, sb.toString()));
                                }
                            }
                        } else if (!toD.contains(Character.toString(c)) && !toR.contains(Character.toString(c))) {
                            if (c != ' ') {
                                throw new Exception();
                            }

                            ++pos;
                        } else {
                            sb = new StringBuilder();
                            sb.append(c);

                            while(toD.contains(sb.toString()) || toR.contains(sb.toString())) {
                                ++pos;
                                if (pos >= text.length()) {
                                    break;
                                }

                                c = text.charAt(pos);
                                sb.append(c);
                            }

                            if (sb.substring(0, sb.length() - 1).toString().equals(toD)) {
                                Words.add(new Word(Word.WordType.Tod, sb.substring(0, sb.length() - 1).toString()));
                            } else if (sb.substring(0, sb.length() - 1).toString().equals(toR)) {
                                Words.add(new Word(Word.WordType.Tor, sb.substring(0, sb.length() - 1).toString()));
                            }
                        }
                        break;
                    case '+':
                        Words.add(new Word(Word.WordType.OpPlus, c));
                        ++pos;
                        break;
                    case '-':
                        Words.add(new Word(Word.WordType.OpMinus, c));
                        ++pos;
                }
            }

            Words.add(new Word(Word.WordType.Eof, ""));
            return Words;
        }
    }

    public static enum WordType {
        LBracket,
        RBracket,
        OpPlus,
        OpMinus,
        Eof,
        Doll,
        Rub,
        Tod,
        Tor;

        private WordType() {
        }
    }
}
