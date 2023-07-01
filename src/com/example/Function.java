package com.example;


import java.util.List;
import com.example.Word.WordType;
public class Function {
    private int pos;
    private double rate;
    private List<Word> Words;

    public Function(List<Word> Words, double rate) {
        this.Words = Words;
        this.rate = rate;
    }

    public Word next() {
        return (Word)this.Words.get(this.pos++);
    }

    public void back() {
        --this.pos;
    }

    public int getPos() {
        return this.pos;
    }

    public static double syntaxAnalyzer(Function Words) throws Exception {
        Word word = Words.next();
        switch(word.getType()) {
            case Rub:
            case Tor:
                Words.back();
                return plusR(Words);
            case Doll:
            case Tod:
                Words.back();
                return plusD(Words);
            default:
                throw new Exception();
        }
    }

    public static double plusR(Function Words) throws Exception {
        double val = rubF(Words);

        while(true) {
            Word word = Words.next();
            switch(word.getType()) {
                case OpPlus:
                    val += rubF(Words);
                    break;
                case OpMinus:
                    val -= rubF(Words);
                    break;
                case Eof:
                    Words.back();
                    return val;
                case RBracket:
                    Words.back();
                    return val / Words.rate;
                default:
                    throw new Exception();
            }
        }
    }

    public static double plusD(Function Words) throws Exception {
        double val = dollF(Words);

        while(true) {
            Word word = Words.next();
            switch(word.getType()) {
                case OpPlus:
                    val += dollF(Words);
                    break;
                case OpMinus:
                    val -= dollF(Words);
                    break;
                case Eof:
                    Words.back();
                    return val;
                case RBracket:
                    Words.back();
                    return val * Words.rate;
                default:
                    throw new Exception();
            }
        }
    }

    public static double rubF(Function Words) throws Exception {
        Word word = Words.next();
        switch(word.getType()) {
            case Rub:
                return Double.parseDouble(word.getVal());
            case Tor:
                word = Words.next();
                if (word.getType() == WordType.LBracket) {
                    double val = plusD(Words);
                    word = Words.next();
                    if (word.getType() != WordType.RBracket) {
                        throw new Exception();
                    }

                    return val;
                }

                throw new Exception();
            default:
                throw new Exception();
        }
    }

    public static double dollF(Function Words) throws Exception {
        Word word = Words.next();
        switch(word.getType()) {
            case Doll:
                return Double.parseDouble(word.getVal());
            case Tod:
                word = Words.next();
                if (word.getType() == WordType.LBracket) {
                    double val = plusR(Words);
                    word = Words.next();
                    if (word.getType() != WordType.RBracket) {
                        throw new Exception();
                    }

                    return val;
                }
            default:
                throw new Exception();
        }
    }
}
