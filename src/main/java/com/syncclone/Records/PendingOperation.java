package com.syncclone.Records;

import com.syncclone.Enums.OperationType;

public record PendingOperation(IndexEntry indexEntry, OperationType operationType) {
}
