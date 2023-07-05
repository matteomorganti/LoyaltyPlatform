package it.unicam.cs.ids.backend.util;

import java.io.PrintStream;

import static java.lang.System.*;

public class ConsoleLog {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RED = "\u001B[31m";

    private static final PrintStream OUT = out;
    private static final PrintStream ERR = err;

    public static void log(String message) {
        OUT.print(ANSI_GREEN);
        OUT.println(message + ANSI_RESET);
    }

    public static void error(String message) {
        ERR.print(ANSI_RED);
        ERR.println("[ERROR] " + message + ANSI_RESET);
    }
}

