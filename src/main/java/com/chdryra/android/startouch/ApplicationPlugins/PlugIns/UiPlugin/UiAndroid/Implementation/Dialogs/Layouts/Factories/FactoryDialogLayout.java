/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Factories;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.chdryra.android.corelibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.LocationServicesPlugin.Api.LocationServices;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Dialogs.Layouts.Configs.ConfigDialogLayouts;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Dialogs.Layouts.Implementation.AddEditLayoutNull;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Dialogs.Layouts.Implementation.AddLocation;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Interfaces.DatumLayoutEdit;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Interfaces.DatumLayoutView;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Dialogs.Layouts.Interfaces.GvDataAdder;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Dialogs.Layouts.Interfaces.GvDataEditor;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by: Rizwan Choudrey
 * On: 18/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryDialogLayout {
    private static final String TAG = TagKeyGenerator.getTag(FactoryDialogLayout.class);
    private final Context mContext;
    private final ConfigDialogLayouts mConfig;
    private final LocationServices mServices;

    public FactoryDialogLayout(Context context, ConfigDialogLayouts config, LocationServices services) {
        mContext = context;
        mConfig = config;
        mServices = services;
    }

    @Nullable
    public <T extends GvData> DatumLayoutEdit<T> newLayout
    (GvDataType<T> dataType, GvDataAdder adder) {
        if(dataType == GvLocation.TYPE) {
            //TODO make type safe
            return (DatumLayoutEdit<T>) new AddLocation(adder, mServices);
        }

        try {
            Class<? extends DatumLayoutEdit<T>> addEditLayout = mConfig.getAddEditLayoutClass(dataType);

            if(addEditLayout == null) return newNullLayout(dataType);

            Constructor<? extends DatumLayoutEdit<T>> ctor
                    = addEditLayout.getDeclaredConstructor(GvDataAdder.class);
            try {
                return ctor.newInstance(adder);
            } catch (InstantiationException e) {
                Log.e(TAG, "Problem constructing add layout for " + dataType.getDatumName(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, "Illegal access whilst constructing add layout for " + dataType
                        .getDatumName(), e);
            } catch (InvocationTargetException e) {
                Log.e(TAG, "Invocation exception whilst constructing add layout for" +
                        " " + dataType.getDatumName(), e);
            }
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "NoSuchMethodException finding constructor", e);
        }

        throw new RuntimeException("Add layout constructor problems for " + dataType
                .getDatumName());
    }

    @NonNull
    private <T extends GvData> DatumLayoutEdit<T> newNullLayout(GvDataType<T> dataType) {
        try {
            return new AddEditLayoutNull<>(mContext, dataType.getDataClass().newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public <T extends GvData> DatumLayoutEdit<T> newLayout
            (GvDataType<T> dataType, GvDataEditor editor) {
        try {
            Class<? extends DatumLayoutEdit<T>> addEditLayout = mConfig.getAddEditLayoutClass(dataType);

            if(addEditLayout == null) return newNullLayout(dataType);

            Constructor<? extends DatumLayoutEdit<T>> ctor =
                    addEditLayout.getDeclaredConstructor(GvDataEditor.class);
            try {
                return ctor.newInstance(editor);
            } catch (InstantiationException e) {
                Log.e(TAG, "Problem constructing edit layout for " + dataType.getDatumName(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, "Illegal access whilst constructing edit layout for " + dataType
                        .getDatumName(), e);
            } catch (InvocationTargetException e) {
                Log.e(TAG, "Invocation exception whilst constructing edit layout for" + dataType
                        .getDatumName(), e);
            }
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "NoSuchMethodException finding constructor", e);
        }

        throw new RuntimeException("Add layout constructor problems for " + dataType.getDatumName
                ());
    }

    @Nullable
    public <T extends GvData> DatumLayoutView<T> newLayout
            (GvDataType<T> dataType) {
        try {
            Class<? extends DatumLayoutView<T>> viewClass = mConfig.getViewLayoutClass(dataType);
            return viewClass != null ? viewClass.newInstance() : newNullLayout(dataType);
        } catch (InstantiationException e) {
            Log.e(TAG, "Problem constructing edit dialog for " + dataType.getDatumName(), e);
        } catch (IllegalAccessException e) {
            Log.e(TAG, "Illegal access whilst constructing edit dialog for " + dataType
                    .getDatumName(), e);
        }
        throw new RuntimeException("view layout constructor problem for " + dataType.getDatumName
                ());
    }
}
