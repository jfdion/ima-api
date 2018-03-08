package ca.ulaval.gif3101.ima.api.utils;

public class Utils {

    public static void stackTrace(Exception e) {
        System.out.println("Printing stack trace:");
        StackTraceElement[] elements = e.getStackTrace();
        for (int i = 1; i < elements.length; i++) {
            StackTraceElement s = elements[i];
            System.out.println("\tat " + s.getClassName() + "." + s.getMethodName()
                    + "(" + s.getFileName() + ":" + s.getLineNumber() + ")");
        }
    }
}
