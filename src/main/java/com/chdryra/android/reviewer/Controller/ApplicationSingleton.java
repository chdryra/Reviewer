/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 7 May, 2015
 */

package com.chdryra.android.reviewer.Controller;

import android.content.Context;

/**
 * Created by: Rizwan Choudrey
 * On: 07/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ApplicationSingleton {
    private Context mContext;
    private String  mName;

    protected ApplicationSingleton(Context context, String name) {
        mContext = context;
        mName = name;
    }

    protected void checkContextOrThrow(Context context) {
        if (context.getApplicationContext() != mContext.getApplicationContext()) {
            throw new RuntimeException("Can only have 1 " + mName + " per application!");
        }
    }

    protected Context getContext() {
        return mContext;
    }
}
