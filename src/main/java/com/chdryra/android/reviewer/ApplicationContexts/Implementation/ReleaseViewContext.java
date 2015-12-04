package com.chdryra.android.reviewer.ApplicationContexts.Implementation;

import com.chdryra.android.reviewer.View.LauncherModel.Factories.FactoryConfigDataUi;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.FactoryLauncherUi;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReleaseViewContext extends ViewContextBasic{
    public ReleaseViewContext() {
        setUiConfig();
        setUiLauncher();

    }

    private void setUiConfig() {
        FactoryConfigDataUi configBuilder = new FactoryConfigDataUi();
        setConfigDataUi(configBuilder.getDefaultConfig());
    }

    private void setUiLauncher() {
        LaunchableUiLauncher launcher = new LaunchableUiLauncher(new FactoryLauncherUi());
        setLauncher(launcher);
    }
}
