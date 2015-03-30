/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 18 December, 2014
 */

package com.chdryra.android.reviewer.View;

import android.util.Log;

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
    private final  HashMap<GvDataType, Class<? extends GvDataEditLayout<? extends GvData>>> mMapAdd;
    private final  HashMap<GvDataType, Class<? extends GvDataEditLayout<? extends GvData>>>
                                                                                            mMapEdit;

    private FactoryGvDataViewLayout() {
        mMapAdd = new HashMap<>();
        mMapAdd.put(GvChildList.TYPE, LayoutChildReview.class);
        mMapAdd.put(GvCommentList.TYPE, LayoutComment.class);
        mMapAdd.put(GvFactList.TYPE, LayoutFact.class);
        mMapAdd.put(GvImageList.TYPE, LayoutImage.class);
        mMapAdd.put(GvTagList.TYPE, LayoutTag.class);
        mMapAdd.put(GvLocationList.TYPE, LayoutLocationAdd.class);

        mMapEdit = new HashMap<>();
        mMapEdit.putAll(mMapAdd);
        mMapEdit.put(GvLocationList.TYPE, LayoutLocationEdit.class);
    }

    static <T extends GvData> GvDataEditLayout<T> newLayout
            (GvDataType dataType, GvDataEditLayout.GvDataAdder adder) {
        if (sFactory == null) sFactory = new FactoryGvDataViewLayout();
        try {
            Constructor ctor = sFactory.mMapAdd.get(dataType)
                    .getDeclaredConstructor(GvDataEditLayout.GvDataAdder.class);
            try {
                //TODO make type safe
                return (GvDataEditLayout) ctor.newInstance(adder);
            } catch (InstantiationException e) {
                Log.e(TAG, "Problem constructing ReviewDataAdd dialog for " + dataType
                        .getDatumName(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, "Illegal access whilst constructing ReviewDataAdd dialog for " +
                        dataType.getDatumName(), e);
            } catch (InvocationTargetException e) {
                Log.e(TAG, "Invocation exception whilst constructing ReviewDataAdd dialog for" +
                        " " + dataType.getDatumName(), e);
            }
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "NoSuchMethodException finding constructor", e);
        }

        throw new RuntimeException("DialogReviewDataAddFragment Dialog Constructor problems for "
                + dataType.getDatumName());
    }

    static <T extends GvData> GvDataEditLayout<T> newLayout
            (GvDataType dataType, GvDataEditLayout.GvDataEditor editor) {
        if (sFactory == null) sFactory = new FactoryGvDataViewLayout();
        try {
            Constructor ctor = sFactory.mMapEdit.get(dataType)
                    .getDeclaredConstructor(GvDataEditLayout.GvDataEditor.class);
            try {
                //TODO make type safe
                return (GvDataEditLayout) ctor.newInstance(editor);
            } catch (InstantiationException e) {
                Log.e(TAG, "Problem constructing edit dialog for " + dataType.getDatumName(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, "Illegal access whilst constructing edit dialog for " + dataType
                        .getDatumName(), e);
            } catch (InvocationTargetException e) {
                Log.e(TAG, "Invocation exception whilst constructing edit dialog for" + dataType
                        .getDatumName(), e);
            }
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "NoSuchMethodException finding constructor", e);
        }

        throw new RuntimeException("DialogReviewDataEditFragment Dialog Constructor problem for "
                + dataType.getDatumName());
    }
}
