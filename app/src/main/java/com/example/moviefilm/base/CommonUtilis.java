package com.example.moviefilm.base;

import android.content.Context;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

public class CommonUtilis {
    public static void showKeyboard(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public static void hideKeyboard(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    public static boolean renameFile(File f1, File f2) {
        boolean canReName = false;
        if(canRename(f1, f2)) {
            canReName = true;
        } else {
            try {
                copy(f1, f2);
            } catch (Exception ex) {
                canReName = true;
                Log.e("TAG", "Error to move new app: " + f1 + " > " + f2);
            }
        }
        return canReName;
    }

    public static void copy(final File f1, final File f2) throws IOException {
        f2.createNewFile();

        final RandomAccessFile file1 = new RandomAccessFile(f1, "r");
        final RandomAccessFile file2 = new RandomAccessFile(f2, "rw");

        file2.getChannel().write(file1.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, f1.length()));
        file1.close();
        file2.close();
    }

    public static boolean canRename(final File f1, final File f2) {
        final String p1 = f1.getAbsolutePath().replaceAll("^(/mnt/|/)", "");
        final String p2 = f2.getAbsolutePath().replaceAll("^(/mnt/|/)", "");

        return p1.replaceAll("\\/\\w+", "").equals(p2.replaceAll("\\/\\w+", ""));
    }
}
