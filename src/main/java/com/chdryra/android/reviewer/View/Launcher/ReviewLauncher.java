/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 18 June, 2015
 */

package com.chdryra.android.reviewer.View.Launcher;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;

import com.chdryra.android.reviewer.ApplicationSingletons.Administrator;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 18/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewLauncher {
    private static final int REQUEST_CODE = 123;

    public static void launchReview(Context context, Fragment commissioner, GvReviewId id) {
        LaunchableUi ui = Administrator.get(context).getReviewLaunchable(id);
        LauncherUi.launch(ui, commissioner, REQUEST_CODE, ui.getLaunchTag(), new Bundle());
    }
}
