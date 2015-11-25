package com.chdryra.android.reviewer.View.Configs.Factories;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.View.Configs.Implementation.ConfigDataUiImpl;
import com.chdryra.android.reviewer.View.Configs.Implementation.DefaultLaunchables;
import com.chdryra.android.reviewer.View.Configs.Implementation.LaunchableClasses;
import com.chdryra.android.reviewer.View.Configs.Implementation.LaunchableConfigsHolder;
import com.chdryra.android.reviewer.View.Configs.Implementation.LaunchablesList;
import com.chdryra.android.reviewer.View.Configs.Interfaces.ConfigDataUi;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryConfigDataUi {
    public ConfigDataUi getDefaultConfig() {
        LaunchablesList defaultlaunchables = new DefaultLaunchables();
        return getConfigDataUi(defaultlaunchables);
    }

    @NonNull
    private ConfigDataUi getConfigDataUi(LaunchablesList defaultClasses) {
        ArrayList<LaunchableConfigsHolder<?>> configs = new ArrayList<>();

        for (LaunchableClasses<?> uiClasses : defaultClasses) {
            configs.add(new LaunchableConfigsHolder<>(uiClasses));
        }

        return new ConfigDataUiImpl(configs);
    }
}
