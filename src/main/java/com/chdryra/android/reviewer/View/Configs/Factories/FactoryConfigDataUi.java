package com.chdryra.android.reviewer.View.Configs.Factories;

import com.chdryra.android.reviewer.View.Configs.Implementation.ConfigDataUiImpl;
import com.chdryra.android.reviewer.View.Configs.Implementation.ClassesAddEditViewDefault;
import com.chdryra.android.reviewer.View.Configs.Interfaces.ConfigDataUi;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryConfigDataUi {
    private FactoryLaunchableConfigs mConfigsFactory;

    public FactoryConfigDataUi() {
        mConfigsFactory = new FactoryLaunchableConfigs();
    }

    public ConfigDataUi getDefaultConfig() {
        return new ConfigDataUiImpl(new ClassesAddEditViewDefault(), mConfigsFactory);
    }
}
