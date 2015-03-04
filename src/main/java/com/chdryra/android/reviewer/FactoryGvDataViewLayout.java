/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 18 December, 2014
 */

package com.chdryra.android.reviewer;

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
    private static FactoryGvDataViewLayout sFactory;
    private final  HashMap<GvDataList.GvDataType, Class<? extends GvDataEditLayout<? extends
            GvDataList.GvData>>>           mMap;

    private FactoryGvDataViewLayout() {
        mMap = new HashMap<>();
        mMap.put(GvChildList.TYPE, LayoutChildReview.class);
        mMap.put(GvCommentList.TYPE, LayoutComment.class);
        mMap.put(GvFactList.TYPE, LayoutFact.class);
        mMap.put(GvImageList.TYPE, LayoutImage.class);
        mMap.put(GvTagList.TYPE, LayoutTag.class);
        mMap.put(GvLocationList.TYPE, LayoutLocationAdd.class);
    }

    static <T extends GvDataList.GvData> GvDataEditLayout<T> newLayout
            (GvDataList.GvDataType dataType, GvDataEditLayout.GvDataAdder adder) {
        if (sFactory == null) sFactory = new FactoryGvDataViewLayout();
        try {
            Constructor ctor = sFactory.mMap.get(dataType)
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

    static <T extends GvDataList.GvData> GvDataEditLayout<T> newLayout
            (GvDataList.GvDataType dataType, GvDataEditLayout.GvDataEditor editor) {
        if (sFactory == null) sFactory = new FactoryGvDataViewLayout();
        try {
            Constructor ctor = sFactory.mMap.get(dataType)
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
