package com.syncclone.model;

import com.syncclone.records.IndexEntry;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public class ProjectManifest {

    public ProjectManifest() {
        this.syncPath = "";
        this.fileIndexOld = Collections.emptyList();
        this.fileIndexNew = Collections.emptyList();
    }

    //Folder that syncclone is syncing
    private String syncPath;

    //List of IndexEntries
    private List<IndexEntry> fileIndexOld;

    private List<IndexEntry> fileIndexNew;

    //Getters and Setters
    public Path getSyncPath() {
        return Path.of(syncPath);
    }
    public void setSyncPath(Path syncPath) {
        this.syncPath = syncPath.toAbsolutePath().normalize().toString();
    }

    public List<IndexEntry> getFileIndexOld() {
        return fileIndexOld;
    }

    public List<IndexEntry> getFileIndexNew() {
        return fileIndexNew;
    }
    public void setFileIndexNew(List<IndexEntry> fileIndexNew) {
        if (fileIndexOld.isEmpty()){
            this.fileIndexOld = fileIndexNew;
        }else {
            this.fileIndexOld = this.fileIndexNew;
        }
        this.fileIndexNew = fileIndexNew;
    }
}
