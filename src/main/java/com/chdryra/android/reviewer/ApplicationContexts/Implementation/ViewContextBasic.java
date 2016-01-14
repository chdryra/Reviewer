package com.chdryra.android.reviewer.ApplicationContexts.Implementation;

import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ViewContext;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.Configs.ConfigDataUi;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class ViewContextBasic implements ViewContext {
    private ConfigDataUi mConfigDataUi;
    private LaunchableUiLauncher mLauncher;

    public void setConfigDataUi(ConfigDataUi configDataUi) {
        mConfigDataUi = configDataUi;
    }

    public void setLauncher(LaunchableUiLauncher launcher) {
        mLauncher = launcher;
    }

    @Override
    public LaunchableUiLauncher getUiLauncher() {
        return mLauncher;
    }

    @Override
    public ConfigDataUi getUiConfig() {
        return mConfigDataUi;
    }
}
