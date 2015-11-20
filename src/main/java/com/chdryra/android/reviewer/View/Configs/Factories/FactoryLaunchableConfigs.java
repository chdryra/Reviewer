package com.chdryra.android.reviewer.View.Configs.Factories;

import com.chdryra.android.reviewer.View.Configs.Implementation.ClassesHolder;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfigsHolder;
import com.chdryra.android.reviewer.View.Configs.Implementation.LaunchableConfigsHolderImpl;
import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryLaunchableConfigs {
    public <T extends GvData> LaunchableConfigsHolder<T> newConfig(GvDataType<T> dataType,
                                                             ClassesHolder classes) {
        return new LaunchableConfigsHolderImpl<>(dataType, classes);
    }
}
