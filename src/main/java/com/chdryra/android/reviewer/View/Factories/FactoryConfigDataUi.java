package com.chdryra.android.reviewer.View.Factories;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.View.Implementation.Configs.ConfigDataUiImpl;
import com.chdryra.android.reviewer.View.Implementation.Configs.DefaultLaunchables;
import com.chdryra.android.reviewer.View.Implementation.Configs.LaunchableClasses;
import com.chdryra.android.reviewer.View.Implementation.Configs.LaunchableConfigsHolder;
import com.chdryra.android.reviewer.View.Implementation.Configs.LaunchablesList;
import com.chdryra.android.reviewer.View.Interfaces.ConfigDataUi;

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
