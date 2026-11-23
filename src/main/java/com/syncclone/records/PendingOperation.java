package com.syncclone.records;

import com.syncclone.enums.OperationType;

public record PendingOperation(
        IndexEntry indexEntry,
        OperationType operationType) {
}
