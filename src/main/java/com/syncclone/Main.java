package com.syncclone;

import com.syncclone.Files.FileUtils;

import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        System.out.println("Application Started");

        FileUtils.deleteRecursively(Paths.get("mock_test_data"));
    }
}
