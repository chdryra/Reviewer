/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Configs;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Interfaces.AddEditLayout;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Interfaces.DialogLayout;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 25/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class ConfigDialogLayouts {
    private final Map<GvDataType<?>, ConfigLayout<?>> mMap = new HashMap<>();

    @Nullable
    public <T extends GvData> Class<? extends AddEditLayout<T>> getAddEditLayoutClass(GvDataType<T> dataType) {
        return getLayouts(dataType).getAddEditLayoutClass();
    }

    @Nullable
    public <T extends GvData> Class<? extends DialogLayout<T>> getViewLayoutClass(GvDataType<T> dataType) {
        return getLayouts(dataType).getViewLayoutClass();
    }

    private <T extends GvData> ConfigLayout<T> getLayouts(GvDataType<T> datatype) {
        //TODO make type safe
        return (ConfigLayout<T>) mMap.get(datatype);
    }

    <T extends GvData> void add(GvDataType<T> dataType,
                                @Nullable Class<? extends AddEditLayout<T>> addEditClass,
                                @Nullable Class<? extends DialogLayout<T>> viewClass) {
        mMap.put(dataType, new ConfigLayout<>(dataType, addEditClass, viewClass));
    }

    /**
     * Created by: Rizwan Choudrey
     * On: 24/11/2015
     * Email: rizwan.choudrey@gmail.com
     */
    private static class ConfigLayout<T extends GvData> {
        private final GvDataType<T> mDataType;
        private final Class<? extends AddEditLayout<T>> mAddEditLayoutClass;
        private final Class<? extends DialogLayout<T>> mViewLayoutClass;

        private ConfigLayout(GvDataType<T> dataType, @Nullable Class<? extends AddEditLayout<T>>
                addEditLayoutClass, @Nullable Class<? extends DialogLayout<T>> viewLayoutClass) {
            mDataType = dataType;
            mAddEditLayoutClass = addEditLayoutClass;
            mViewLayoutClass = viewLayoutClass;
        }

        public GvDataType<T> getGvDataType() {
            return mDataType;
        }

        @Nullable
        public Class<? extends AddEditLayout<T>> getAddEditLayoutClass() {
            return mAddEditLayoutClass;
        }

        @Nullable
        public Class<? extends DialogLayout<T>> getViewLayoutClass() {
            return mViewLayoutClass;
        }
    }
}
