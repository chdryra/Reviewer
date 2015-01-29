/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.app.Fragment;

/**
 * UI Activity holding {@link FragmentEditUrlBrowser}: browsing and searching URLs (currently
 * disabled).
 */
public class ActivityEditUrlBrowser extends ActivityViewReview implements LaunchableUi {

    @Override
    public void launch(LauncherUi launcher) {
        launcher.launch(this);
    }

    @Override
    protected Fragment createFragment() {
        return new FragmentEditUrlBrowser();
    }

}
