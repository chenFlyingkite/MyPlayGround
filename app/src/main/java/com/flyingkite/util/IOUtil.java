package com.flyingkite.util;

import java.io.Closeable;
import java.io.IOException;

public class IOUtil {
    public static void closeIt(Closeable cl) {
        if (cl == null) return;

        try {
            cl.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
