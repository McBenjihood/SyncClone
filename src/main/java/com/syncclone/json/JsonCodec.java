package com.syncclone.json;

import com.syncclone.model.ProjectManifest;
import org.tinylog.Logger;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import java.nio.file.Path;

public class JsonCodec {
    private static final ObjectMapper objectMapper = ObjectMapperFactory.getInstance();

    public static ProjectManifest readProjectManifest(Path configManifestPath){
        return objectMapper.readValue(configManifestPath, ProjectManifest.class);
    }

    public static boolean writeProjectManifest(Path configManifestPath, ProjectManifest projectManifest){
        try {
            objectMapper.writeValue(configManifestPath, projectManifest);
        } catch (JacksonException e) {
            Logger.error("Jackson failed to map and write to json", e);
            return false;
        }
        return true;
    }
}
