package com.chdryra.android.reviewer.View.Configs.Interfaces;

import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.Launcher.Factories.FactoryLaunchableUi;
import com.chdryra.android.reviewer.View.Launcher.Interfaces.LaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface LaunchableConfig<T extends GvData> {
    String getTag();

    int getRequestCode();

    LaunchableUi getLaunchable(FactoryLaunchableUi launchableFactory);

    GvDataType<T> getGvDataType();
}
