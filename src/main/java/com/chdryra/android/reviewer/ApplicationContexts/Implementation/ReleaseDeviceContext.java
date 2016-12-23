/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationContexts.Implementation;

import android.content.Context;
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
            .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
    private final String mImageDir;

    public ReleaseDeviceContext(Context context) {
        int stringId = context.getApplicationInfo().labelRes;
        mImageDir = context.getString(stringId);
    }

    @Override
    public File getImageStoragePath() {
        return FILE_DIR_EXT;
    }

    @Override
    public String getImageStorageDirectory() {
        return mImageDir;
    }
}
