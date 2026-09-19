package com.syncclone.Files;

import com.syncclone.Exceptions.DeletePathException;
import com.syncclone.Exceptions.ReadDirectoryException;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUtils {
    public static void deleteRecursively(Path inputPath) {
        Stream<Path> outputStream = readRecursively(inputPath);
        outputStream.sorted(Comparator.reverseOrder()).forEach(path -> {
            try {
                Files.delete(path);
            } catch (IOException e) {
                throw new DeletePathException("IOException on attempted deletion of :" + path, e);
            }
        });
    }


    public static Stream<Path> readRecursively(Path path){
        try {
            return Files.walk(path);
        }catch (IOException | UncheckedIOException e){
            throw new ReadDirectoryException("Exception while trying to recursively read Path content.", e);
        }
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
