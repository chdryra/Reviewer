/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 October, 2014
 */

package com.chdryra.android.reviewer;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by: Rizwan Choudrey
 * On: 23/10/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewDataUILauncher {
    private static final String LAUNCHER_ARGS = "com.chdryra.android.review.args_key";
    private Fragment mCommissioner;
    private int      mRequestCode;
    private String   mTag;
    private Bundle   mArgs;

    private ReviewDataUILauncher(Fragment commissioner, int requestCode, String tag, Bundle args) {
        mCommissioner = commissioner;
        mRequestCode = requestCode;
        mTag = tag;
        mArgs = args;
    }

    static void launch(ReviewDataUI ui, Fragment commissioner, int requestCode, String tag,
                       Bundle args) {
        ui.launch(new ReviewDataUILauncher(commissioner, requestCode, tag,
                args));
    }

    static Bundle getArgsForActivity(Activity reviewDataUI) {
        return reviewDataUI.getIntent().getBundleExtra(LAUNCHER_ARGS);
    }

    void launch(DialogFragment reviewDataUI) {
        DialogShower.show(reviewDataUI, mCommissioner, mRequestCode, mTag, mArgs);
    }

    void launch(Activity reviewDataUI) {
        Intent i = new Intent(mCommissioner.getActivity(), reviewDataUI.getClass());
        i.putExtra(LAUNCHER_ARGS, mArgs);
        mCommissioner.startActivityForResult(i, mRequestCode);
    }
}