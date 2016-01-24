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

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReleaseViewContext extends ViewContextBasic{
    public ReleaseViewContext(UiPlugin ui) {
        setConfigUi(new FactoryConfigUi().newUiConfig(ui.getUiLaunchables()));
        setLauncher(new LaunchableUiLauncher(new FactoryLauncherUi()));
    }
}
