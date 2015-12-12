/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 18 December, 2014
 */

package com.chdryra.android.reviewer.View.AndroidViews.Dialogs.Layouts.Factories;

import android.util.Log;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.View.AndroidViews.Dialogs.Layouts.Configs.ConfigDialogLayouts;
import com.chdryra.android.reviewer.View.AndroidViews.Dialogs.Layouts.Interfaces.AddEditLayout;
import com.chdryra.android.reviewer.View.AndroidViews.Dialogs.Layouts.Interfaces.DialogLayout;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by: Rizwan Choudrey
 * On: 18/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryDialogLayout {
    private static final String TAG = "FactoryGvDataViewHolder";
    private ConfigDialogLayouts mConfig;

    public FactoryDialogLayout(ConfigDialogLayouts config) {
        mConfig = config;
    }

    public <T extends GvData> AddEditLayout<T> newLayout
    (GvDataType<T> dataType, AddEditLayout.GvDataAdder adder) {
        try {
            Class<? extends AddEditLayout<T>> addEditLayout = mConfig.getAddEditLayoutClass(dataType);
            Constructor<? extends AddEditLayout<T>> ctor
                    = addEditLayout.getDeclaredConstructor(AddEditLayout.GvDataAdder.class);
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

    public <T extends GvData> AddEditLayout<T> newLayout
            (GvDataType<T> dataType, AddEditLayout.GvDataEditor editor) {
        try {
            Class<? extends AddEditLayout<T>> addEditLayout = mConfig.getAddEditLayoutClass(dataType);
            Constructor<? extends AddEditLayout<T>> ctor =
                    addEditLayout.getDeclaredConstructor(AddEditLayout.GvDataEditor.class);
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

    public <T extends GvData> DialogLayout<T> newLayout
            (GvDataType<T> dataType) {
        try {
            Class<? extends DialogLayout<T>> viewClass = mConfig.getViewLayoutClass(dataType);
            return viewClass.newInstance();
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
