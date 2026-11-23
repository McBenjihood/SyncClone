package com.syncclone.records;

public record IndexEntry(
        String relativePath,
        long size,
        long lastModifiedEpoch,
        String sha256Hash,
        boolean isDirectory
) {
    public boolean hasQuickMetadataChanged(long currentSize, long currentModified) {
        return this.size != currentSize || this.lastModifiedEpoch != currentModified;
    }
}
