package com.chdryra.android.reviewer.View.Configs.Factories;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.View.Configs.Implementation.ClassesAddEditViewDefault;
import com.chdryra.android.reviewer.View.Configs.Implementation.ClassesDialogLayoutHolder;
import com.chdryra.android.reviewer.View.Configs.Implementation.ClassesHolder;
import com.chdryra.android.reviewer.View.Configs.Implementation.ConfigDataUiImpl;
import com.chdryra.android.reviewer.View.Configs.Implementation.LaunchableConfigsHolder;
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
        return getConfigDataUi(defaultClasses);
    }

    @NonNull
    private ConfigDataUi getConfigDataUi(ClassesAddEditView defaultClasses) {
        ArrayList<LaunchableConfigsHolder<?>> mConfigs = new ArrayList<>();
        ArrayList<ClassesDialogLayoutHolder<?>> mLayouts = new ArrayList<>();
        for (GvDataType<? extends GvData> type : GvDataTypesList.ALL_TYPES) {
            ClassesHolder<?> uiClasses = defaultClasses.getUiClasses(type);
            mConfigs.add(new LaunchableConfigsHolder<>(type, uiClasses.getAddClass(),
                    uiClasses.getEditClass(), uiClasses.getViewClass()));
            mLayouts.add(getLayouts(uiClasses));
        }

        return new ConfigDataUiImpl(mConfigs, mLayouts);
    }

    @NonNull
    private <T extends GvData> ClassesDialogLayoutHolder<T> getLayouts(ClassesHolder<T> uiClasses) {
        return new ClassesDialogLayoutHolder<>(uiClasses.getGvDataType(),
                uiClasses.getAddEditLayoutClass(), uiClasses.getViewLayoutClass());
    }

}
