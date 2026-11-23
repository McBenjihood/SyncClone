package com.syncclone.core;

import com.syncclone.files.FileTreeService;
import com.syncclone.json.JsonCodec;
import com.syncclone.json.ObjectMapperFactory;
import com.syncclone.model.ProjectManifest;
import com.syncclone.records.IndexEntry;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class SynccloneInitializer {

    private static final ObjectMapper objectMapper = ObjectMapperFactory.getInstance();
    private static final Path configManifestPath = Path.of(System.getProperty("user.home"), ".syncclone", "manifest.json");



    public static ProjectManifest initializeConfigStructure(Path syncPath){
        if (Files.exists(configManifestPath) && Files.isRegularFile(configManifestPath)){
            return JsonCodec.readProjectManifest(configManifestPath);
        }else {
            Path configPath = configManifestPath.getParent();
            try {
                Files.createDirectories(configPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            ProjectManifest projectManifest = new ProjectManifest();

            //IndexEntryList
            List<IndexEntry> indexEntryList = FileTreeService.readRecursively(syncPath);

            Path normalizedPath = syncPath.normalize().toAbsolutePath();


            projectManifest.setSyncPath(normalizedPath);
            projectManifest.setFileIndexNew(indexEntryList);

            if (JsonCodec.writeProjectManifest(configManifestPath, projectManifest)){
                return projectManifest;
            }else {
                return new ProjectManifest();
            }
        }
    }
}
