package com.chdryra.android.reviewer.View.Interfaces;

import com.chdryra.android.reviewer.View.Factories.FactoryLaunchableUi;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvData;

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
