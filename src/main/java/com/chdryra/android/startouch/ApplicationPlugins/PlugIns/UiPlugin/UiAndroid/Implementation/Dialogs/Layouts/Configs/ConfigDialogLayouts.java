/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid
        .Implementation.Dialogs.Layouts.Configs;

import android.support.annotation.Nullable;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Dialogs.Layouts.Interfaces.DatumLayoutEdit;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Dialogs.Layouts.Interfaces.DatumLayoutView;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvDataType;

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
    public <T extends GvData> Class<? extends DatumLayoutEdit<T>> getAddEditLayoutClass
            (GvDataType<T> dataType) {
        return getLayouts(dataType).getAddEditLayoutClass();
    }

    @Nullable
    public <T extends GvData> Class<? extends DatumLayoutView<T>> getViewLayoutClass
            (GvDataType<T> dataType) {
        return getLayouts(dataType).getViewLayoutClass();
    }

    <T extends GvData> void add(GvDataType<T> dataType,
                                @Nullable Class<? extends DatumLayoutEdit<T>> addEditClass,
                                @Nullable Class<? extends DatumLayoutView<T>> viewClass) {
        mMap.put(dataType, new ConfigLayout<>(dataType, addEditClass, viewClass));
    }

    private <T extends GvData> ConfigLayout<T> getLayouts(GvDataType<T> datatype) {
        //TODO make type safe
        return (ConfigLayout<T>) mMap.get(datatype);
    }

    /**
     * Created by: Rizwan Choudrey
     * On: 24/11/2015
     * Email: rizwan.choudrey@gmail.com
     */
    private static class ConfigLayout<T extends GvData> {
        private final GvDataType<T> mDataType;
        private final Class<? extends DatumLayoutEdit<T>> mAddEditLayoutClass;
        private final Class<? extends DatumLayoutView<T>> mViewLayoutClass;

        private ConfigLayout(GvDataType<T> dataType, @Nullable Class<? extends DatumLayoutEdit<T>>
                addEditLayoutClass, @Nullable Class<? extends DatumLayoutView<T>> viewLayoutClass) {
            mDataType = dataType;
            mAddEditLayoutClass = addEditLayoutClass;
            mViewLayoutClass = viewLayoutClass;
        }

        public GvDataType<T> getGvDataType() {
            return mDataType;
        }

        @Nullable
        public Class<? extends DatumLayoutEdit<T>> getAddEditLayoutClass() {
            return mAddEditLayoutClass;
        }

        @Nullable
        public Class<? extends DatumLayoutView<T>> getViewLayoutClass() {
            return mViewLayoutClass;
        }
    }
}
