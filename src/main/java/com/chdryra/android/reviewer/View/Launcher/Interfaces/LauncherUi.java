package com.chdryra.android.reviewer.View.Launcher.Interfaces;

import android.app.Activity;
import android.app.DialogFragment;

import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.ReviewView;

/**
 * Created by: Rizwan Choudrey
 * On: 18/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface LauncherUi {
    void launch(DialogFragment launchableUI);

    void launch(Activity launchableUI);

    void launch(ReviewView reviewView);
}
