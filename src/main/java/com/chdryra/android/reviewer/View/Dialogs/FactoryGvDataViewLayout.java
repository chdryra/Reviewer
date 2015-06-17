/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 18 December, 2014
 */

package com.chdryra.android.reviewer.View.Dialogs;

import android.util.Log;

import com.chdryra.android.reviewer.View.GvDataModel.GvChildList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/**
 * Created by: Rizwan Choudrey
 * On: 18/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryGvDataViewLayout {
    private static final String TAG = "FactoryGvDataViewHolder";
    private static FactoryGvDataViewLayout
                                                                                            sFactory;
    private final HashMap<GvDataType, Class<? extends AddEditLayout<? extends GvData>>> mMapAdd;
    private final HashMap<GvDataType, Class<? extends AddEditLayout<? extends GvData>>>
                                                                                        mMapEdit;
    private final HashMap<GvDataType, Class<? extends DialogLayout<? extends GvData>>>
                                                                                        mMapView;

    private FactoryGvDataViewLayout() {
        mMapAdd = new HashMap<>();
        mMapAdd.put(GvChildList.TYPE, AddEditChildReview.class);
        mMapAdd.put(GvCommentList.TYPE, AddEditComment.class);
        mMapAdd.put(GvFactList.TYPE, AddEditFact.class);
        mMapAdd.put(GvImageList.TYPE, EditImage.class);
        mMapAdd.put(GvTagList.TYPE, AddEditTag.class);
        mMapAdd.put(GvLocationList.TYPE, AddLocation.class);

        mMapEdit = new HashMap<>();
        mMapEdit.putAll(mMapAdd);
        mMapEdit.put(GvLocationList.TYPE, EditLocation.class);

        mMapView = new HashMap<>();
        mMapView.putAll(mMapEdit);
        mMapView.put(GvChildList.TYPE, ViewChildReview.class);
    }

    public static <T extends GvData> AddEditLayout<T> newLayout
            (GvDataType dataType, AddEditLayout.GvDataAdder adder) {
        if (sFactory == null) sFactory = new FactoryGvDataViewLayout();
        try {
            Constructor ctor = sFactory.mMapAdd.get(dataType)
                    .getDeclaredConstructor(AddEditLayout.GvDataAdder.class);
            try {
                //TODO make type safe
                return (AddEditLayout) ctor.newInstance(adder);
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

    public static <T extends GvData> AddEditLayout<T> newLayout
            (GvDataType dataType, AddEditLayout.GvDataEditor editor) {
        if (sFactory == null) sFactory = new FactoryGvDataViewLayout();
        try {
            Constructor ctor = sFactory.mMapEdit.get(dataType)
                    .getDeclaredConstructor(AddEditLayout.GvDataEditor.class);
            try {
                //TODO make type safe
                return (AddEditLayout) ctor.newInstance(editor);
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

    public static <T extends GvData> DialogLayout<T> newLayout
            (GvDataType dataType) {
        if (sFactory == null) sFactory = new FactoryGvDataViewLayout();
        try {
            //TODO make type safe
            return (DialogLayout) sFactory.mMapView.get(dataType).newInstance();
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
