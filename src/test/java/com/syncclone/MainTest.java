package com.syncclone;

import com.syncclone.filesystem.FileScanner;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {

    @Test
    void testReadDirectory() {
        //Arrange
        List<String> expected = List.of(
                "file1.md",
                "file1.txt",
                "file2.md",
                "file2.txt",
                "file3.md",
                "file3.txt"
        );

        //Act
        List<String> result = FileScanner.readDirectory(Path.of("test-files"));

        //Assert
        assertEquals(expected, result);
    }
}