package com.example;
import com.example.Word.WordType;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
public class Main {

    public static void main(String[] args) {
        try {

            Config config = new Config();
            config.setProperties("Rate","60");
            double rate = Double.parseDouble(config.getProperties("Rate"));
            System.out.print("Введите выражение: ");
            Scanner in = new Scanner(System.in);
            String text = in.nextLine();
            List<Word> words = Word.WordAnalyze(text);
            Word first = (Word)words.get(0);
            char f = 32;
            if (first.getType() != WordType.Rub && first.getType() != WordType.Tor) {
                if (first.getType() == WordType.Doll || first.getType() == WordType.Tod) {
                    f = 100;
                }
            } else {
                f = 114;
            }

            Function WordBuf = new Function(words, rate);
            double result = Function.syntaxAnalyzer(WordBuf);
            String res = String.format("%.2f", result);
            if (f == 114) {
                System.out.print(res + "p");
            } else {
                System.out.print("$" + res);
            }
        } catch (FileNotFoundException var13) {
            System.out.println("The file is missing");
        } catch (NumberFormatException var14) {
            System.out.println("Invalid dollar rate");
        } catch (Exception var15) {
            var15.printStackTrace();
        }
    }
}
