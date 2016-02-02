/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationContexts.Implementation;

import com.chdryra.android.reviewer.PlugIns.UiPlugin.Api.UiPlugin;
import com.chdryra.android.reviewer.View.Configs.FactoryConfigUi;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.FactoryLauncherUi;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchablesList;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReleaseViewContext extends ViewContextBasic{
    public ReleaseViewContext(UiPlugin ui) {
        LaunchablesList uiLaunchables = ui.getUiLaunchables();
        setConfigUi(new FactoryConfigUi().newUiConfig(uiLaunchables));
        FactoryLauncherUi launcherFactory
                = new FactoryLauncherUi(uiLaunchables.getDefaultReviewViewActivity());
        setLauncher(new LaunchableUiLauncher(launcherFactory));
    }
}
