package com.syncclone;

import com.syncclone.files.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {

    @Test
    void testReadDirectory() throws IOException {
        //Arrange
        Path baseDir = Path.of("test-files");
        try {
            Files.createDirectory(baseDir);
        } catch (FileAlreadyExistsException e) {
            FileUtils.deleteRecursively(baseDir);
            Files.createDirectory(baseDir);
        }

        List<String> expected = List.of("file0.md","file0.sh","file0.txt","file1.md","file1.sh","file1.txt","file2.md","file2.sh","file2.txt");

        //Act
        List<String> fileExtensions=List.of(".txt",".md",".sh");
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                Path file = baseDir.resolve("file" +i+ fileExtensions.get(j));
                Files.createFile(file);
            }
        }

        List<String> result = FileUtils.listDirectoryContentInStrings(baseDir);

        //Assert
        assertEquals(expected, result);
        FileUtils.deleteRecursively(baseDir);
    }
}