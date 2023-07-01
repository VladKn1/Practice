//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

public class Config {
    Properties prop = new Properties();

    public Config() {
    }

    public String getProperties(String key) throws FileNotFoundException {
        try {
            FileInputStream out = new FileInputStream("config.txt");
            this.prop.load(out);
        } catch (FileNotFoundException var3) {
            System.out.println("The file is missing");
        } catch (IOException var4) {
            var4.printStackTrace();
        }

        return this.prop.getProperty(key);
    }

    public void setProperties(String key, String value) throws IOException {
        try {
            new FileOutputStream("config.txt");
            this.prop.setProperty(key, value);
        } catch (FileNotFoundException var4) {
            var4.printStackTrace();
        }

        PrintWriter pw = new PrintWriter("config.txt");
        this.prop.store(pw, (String)null);
    }
}
