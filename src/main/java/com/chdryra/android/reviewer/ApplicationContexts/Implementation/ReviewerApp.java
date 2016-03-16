/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationContexts.Implementation;

import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 16/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewerApp extends android.app.Application {
    private boolean mIsInitialised = false;

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
        mIsInitialised = true;
    }

    public boolean isInitialised() {
        return mIsInitialised;
    }
}
