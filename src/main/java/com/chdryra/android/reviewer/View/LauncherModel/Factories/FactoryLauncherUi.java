package com.chdryra.android.reviewer.View.LauncherModel.Factories;

import android.app.Activity;
import android.os.Bundle;

import com.chdryra.android.reviewer.View.LauncherModel.Implementation.LauncherUiImpl;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LauncherUi;

/**
 * Created by: Rizwan Choudrey
 * On: 18/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryLauncherUi {
    public LauncherUi newLauncher(Activity commissioner, int requestCode, String tag, Bundle args) {
        return new LauncherUiImpl(commissioner, requestCode, tag, args);
    }
}
