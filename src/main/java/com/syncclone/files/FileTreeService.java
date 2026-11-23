package com.syncclone.files;

import com.google.common.hash.Hashing;
import com.google.common.io.MoreFiles;
import com.syncclone.exceptions.DeletePathException;
import com.syncclone.exceptions.ReadDirectoryException;
import com.syncclone.records.IndexEntry;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileTreeService {
    public static void deleteRecursively(Path inputPath) {
        List<IndexEntry> filesList = readRecursively(inputPath);
        Stream<Path> outputStream = filesList.stream()
                .map(indexEntry -> inputPath.resolve(indexEntry.relativePath()))
                .sorted(Comparator.reverseOrder());
        outputStream
                .forEach(path -> {
                    try {
                        Files.delete(path);
                    } catch (IOException e) {
                        throw new DeletePathException("IOException on attempted deletion of :" + path, e);
                    }
                });
    }

    public static List<IndexEntry> readRecursively(Path inputPath){
        try (Stream<Path> stream = Files.walk(inputPath)){
            return stream
                    .map(path -> indexEntryFromPath(inputPath, path))
                    .collect(Collectors.toList());
        }catch (IOException | UncheckedIOException e){
            throw new ReadDirectoryException("Exception while trying to recursively read Path content.", e);
        }
    }


    public static IndexEntry indexEntryFromPath(Path syncPath, Path filePath) {
        BasicFileAttributes attributes = null;
        try {
            attributes = Files.readAttributes(filePath, BasicFileAttributes.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String relativePath = syncPath.relativize(filePath)
                .toString()
                .replace('\\', '/');
        return new IndexEntry(
                relativePath,
                attributes.size(),
                attributes.lastModifiedTime().toMillis(),
                getSHA256(filePath),
                Files.isDirectory(filePath)
        );
    }

    public static Path pathFromIndexEntry(IndexEntry indexEntry){
        return Path.of(indexEntry.relativePath());
    }


    private static String getSHA256(Path path) {
        if (Files.isRegularFile(path)){
            try {
                return MoreFiles.asByteSource(path).hash(Hashing.sha256()).toString();
            } catch (IOException e) {
                return "";
            }
        }
        return "";
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
