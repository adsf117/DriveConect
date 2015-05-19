package com.app.adserranov.driveconect;

/**
 * Created by adserranov on 19/05/2015.
 */
import android.os.Bundle;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveFolder.DriveFolderResult;
import com.google.android.gms.drive.MetadataChangeSet;

public class CreateFolderActivity extends MainActivity {
    @Override
    public void onConnected(Bundle connectionHint) {
        super.onConnected(connectionHint);
        MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                .setTitle("New folder").build();
        Drive.DriveApi.getRootFolder(getGoogleApiClient()).createFolder(
                getGoogleApiClient(), changeSet).setResultCallback(folderCreatedCallback);
    }

    ResultCallback<DriveFolderResult> folderCreatedCallback = new
            ResultCallback<DriveFolderResult>() {
                @Override
                public void onResult(DriveFolderResult result) {
                    if (!result.getStatus().isSuccess()) {
                        showMessage("Error while trying to create the folder");
                        return;
                    }
                    showMessage("Created a folder: " + result.getDriveFolder().getDriveId());
                }
            };
}
