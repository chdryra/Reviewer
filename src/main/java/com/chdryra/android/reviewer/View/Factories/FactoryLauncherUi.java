package com.chdryra.android.reviewer.View.Factories;

import android.app.Activity;
import android.os.Bundle;

import com.chdryra.android.reviewer.View.Implementation.LauncherUiImpl;
import com.chdryra.android.reviewer.View.Interfaces.LauncherUi;

/**
 * Created by: Rizwan Choudrey
 * On: 18/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryLauncherUi {
    public LauncherUi newLauncher(Activity commissioner, int requestCode, String tag, Bundle args) {
        return new LauncherUiImpl(commissioner, requestCode, tag, args);
    }

    public LauncherUi newLauncher(Activity commissioner, int requestCode, String tag) {
        return newLauncher(commissioner, requestCode, tag, new Bundle());
    }
}
