package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class FilesUtils {
    public static List<File> listFilesRecursively(String directoryPath) {
        List<File> fileList = new ArrayList<>();
        File directory = new File(directoryPath);

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                    fileList.add(file);

                    if (file.isDirectory()) {
                        // Recursive call for subdirectories
                        fileList.addAll(listFilesRecursively(file.getAbsolutePath()));
                    }
                }
            }
        }
        return fileList;
    }

    public static Queue<File> convertFilesToQueue(File[] files) {
        Queue<File> fileQueue = new LinkedList<>();
        for (File file : files) {
            fileQueue.offer(file);
        }
        return fileQueue;
    }
}
