package com.chdryra.android.reviewer.View.Interfaces;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;

import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.ReviewView;

/**
 * Created by: Rizwan Choudrey
 * On: 18/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface LauncherUi {
    Bundle getArguments();

    void launch(DialogFragment launchableUI);

    void launch(Activity launchableUI);

    void launch(ReviewView reviewView);
}
