package com.app.adserranov.driveconect;

import android.os.Bundle;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveFolder.DriveFileResult;
import com.google.android.gms.drive.MetadataChangeSet;

/**
 * Created by adserranov on 19/05/2015.
 */
public class CreateEmptyFileActivity extends MainActivity {

    @Override
    public void onConnected(Bundle connectionHint) {
        super.onConnected(connectionHint);

        // Perform I/O off the UI thread.
        new Thread() {
            @Override
            public void run() {
                MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                        .setTitle("New file")
                        .setMimeType("text/plain")
                        .setStarred(true).build();

                // Create an empty file on root folder.
                Drive.DriveApi.getRootFolder(getGoogleApiClient())
                        .createFile(getGoogleApiClient(), changeSet, null /* DriveContents */)
                        .setResultCallback(fileCallback);
            }
        }.start();
    }

    final private ResultCallback<DriveFolder.DriveFileResult> fileCallback = new
            ResultCallback<DriveFileResult>() {
                @Override
                public void onResult(DriveFileResult result) {
                    if (!result.getStatus().isSuccess()) {
                        showMessage("Error while trying to create the file");
                        return;
                    }
                    showMessage("Created an empty file: "
                            + result.getDriveFile().getDriveId());
                }
            };
}
