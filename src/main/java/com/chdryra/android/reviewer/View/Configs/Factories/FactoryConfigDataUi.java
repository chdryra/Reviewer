package com.chdryra.android.reviewer.View.Configs.Factories;

import com.chdryra.android.reviewer.View.Configs.Implementation.ClassesAddEditViewDefault;
import com.chdryra.android.reviewer.View.Configs.Implementation.ConfigDataUiImpl;
import com.chdryra.android.reviewer.View.Configs.Implementation.LaunchableConfigsHolderImpl;
import com.chdryra.android.reviewer.View.Configs.Interfaces.ClassesAddEditView;
import com.chdryra.android.reviewer.View.Configs.Interfaces.ConfigDataUi;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvDataTypesList;
import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvData;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryConfigDataUi {
    public ConfigDataUi getDefaultConfig() {
        ClassesAddEditView defaultClasses = new ClassesAddEditViewDefault();
        ArrayList<LaunchableConfigsHolderImpl<?>> mConfigs = new ArrayList<>();
        for (GvDataType<? extends GvData> type : GvDataTypesList.ALL_TYPES) {
            mConfigs.add(new LaunchableConfigsHolderImpl<>(type, defaultClasses.getUiClasses(type)));
        }

        return new ConfigDataUiImpl(mConfigs);
    }
}
