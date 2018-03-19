/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.View.LauncherModel.Implementation;

import android.os.Bundle;

/**
 * Created by: Rizwan Choudrey
 * On: 06/10/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class UiLauncherArgs {
    private boolean mClearBackStack = false;
    private Bundle mBundle = new Bundle();
    private int mRequestCode;

    public UiLauncherArgs(int requestCode) {
        mRequestCode = requestCode;
    }

    public boolean isClearBackStack() {
        return mClearBackStack;
    }

    public Bundle getBundle() {
        return mBundle;
    }

    public UiLauncherArgs setBundle(Bundle args) {
        mBundle = args;
        return this;
    }

    public int getRequestCode() {
        return mRequestCode;
    }

    UiLauncherArgs setRequestCode(int requestCode) {
        mRequestCode = requestCode;
        return this;
    }

    public UiLauncherArgs setClearBackStack() {
        mClearBackStack = true;
        return this;
    }
}
