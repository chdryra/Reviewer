/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationContexts.Implementation;

import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ViewContext;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.Configs.ConfigUi;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class ViewContextBasic implements ViewContext {
    private ConfigUi mConfigUi;
    private LaunchableUiLauncher mLauncher;

    public void setConfigUi(ConfigUi configUi) {
        mConfigUi = configUi;
    }

    public void setLauncher(LaunchableUiLauncher launcher) {
        mLauncher = launcher;
    }

    @Override
    public LaunchableUiLauncher getUiLauncher() {
        return mLauncher;
    }

    @Override
    public ConfigUi getUiConfig() {
        return mConfigUi;
    }
}
