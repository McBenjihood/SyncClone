package com.syncclone;

import com.syncclone.core.SynccloneInitializer;
import com.syncclone.model.ProjectManifest;
import org.tinylog.Logger;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        Path path = Path.of("/home/benji/Obsidian/benjis-vault/BBZW/");

        ProjectManifest projectManifest = SynccloneInitializer.initializeConfigStructure(path);
        Logger.info("initialized Syncclone config directory");
    }
}
