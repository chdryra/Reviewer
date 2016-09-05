/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationContexts.Implementation;

import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ViewContext;
import com.chdryra.android.reviewer.View.Configs.ConfigUi;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.FactoryUiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class ViewContextBasic implements ViewContext {
    private ConfigUi mConfigUi;
    private FactoryUiLauncher mLauncherFactory;

    protected void setConfigUi(ConfigUi configUi) {
        mConfigUi = configUi;
    }

    protected void setLauncherFactory(FactoryUiLauncher launcherFactory) {
        mLauncherFactory = launcherFactory;
    }

    @Override
    public FactoryUiLauncher getLauncherFactory() {
        return mLauncherFactory;
    }

    @Override
    public ConfigUi getUiConfig() {
        return mConfigUi;
    }
}
