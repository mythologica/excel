package org.example.common;

import java.io.BufferedReader;
import java.io.FileReader;

public class FileTools {
    public static String read(String fullFileName) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader  = new BufferedReader(new FileReader(fullFileName)) ) {
            String str;
            while ((str = reader.readLine()) != null) {
                sb.append(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return sb.toString();
    }
}
