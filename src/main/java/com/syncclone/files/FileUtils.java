package com.syncclone.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUtils {
    public static void deleteRecursively(Path path) throws IOException {
        if(Files.isDirectory(path)){
            try (Stream<Path> entries = Files.list(path)){
                for(Path entry : entries.toList()){
                    deleteRecursively(entry);
                }
            }
        }
        Files.delete(path);
    }

    public static List<String> listDirectoryContentInStrings(Path inputPath){
        try (Stream<Path> stream = Files.list(inputPath)){
            return stream
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .sorted()
                    .collect(Collectors.toList());
        } catch (IOException e) {
            return List.of();
        }
    }
}

