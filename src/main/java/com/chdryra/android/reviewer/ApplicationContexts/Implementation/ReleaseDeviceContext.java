package com.chdryra.android.reviewer.ApplicationContexts.Implementation;

import android.os.Environment;

import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.DeviceContext;

import java.io.File;

/**
 * Created by: Rizwan Choudrey
 * On: 13/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReleaseDeviceContext implements DeviceContext {
    private static final File FILE_DIR_EXT = Environment
            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
    private static final String IMAGE_DIR = "Reviewer";

    @Override
    public File getImageStoragePath() {
        return FILE_DIR_EXT;
    }

    @Override
    public String getImageStorageDirectory() {
        return IMAGE_DIR;
    }
}
