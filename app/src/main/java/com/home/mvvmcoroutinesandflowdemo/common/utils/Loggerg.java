package com.home.mvvmcoroutinesandflowdemo.common.utils;

public class Loggerg {

    public static void d(String msg) {
        String tag = "more";
        StackTraceElement targetStackTraceElement = getTargetStackTraceElement();
        String fileName = targetStackTraceElement.getFileName();
        int lineNumber = targetStackTraceElement.getLineNumber();
        android.util.Log.d(tag, msg + "  (" + fileName + ":" + lineNumber + ")");
    }

    private static StackTraceElement getTargetStackTraceElement() {
        // find the target invoked method
        StackTraceElement targetStackTrace = null;
        boolean shouldTrace = false;
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (StackTraceElement stackTraceElement : stackTrace) {
            boolean isLogMethod = stackTraceElement.getClassName().equals(Loggerg.class.getName());
            if (shouldTrace && !isLogMethod) {
                targetStackTrace = stackTraceElement;
                break;
            }
            shouldTrace = isLogMethod;
        }
        return targetStackTrace;
    }
}
