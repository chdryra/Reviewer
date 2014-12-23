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
public class FactoryGvDataViewHolder {
    private static final String TAG = "FactoryGvDataViewHolder";
    private static FactoryGvDataViewHolder sFactory;
    private final  HashMap<GvDataList.GvType, Class<? extends GvDataViewLayout<? extends
            GvDataList
                    .GvData>>>             mMap;

    private FactoryGvDataViewHolder() {
        mMap = new HashMap<>();
        mMap.put(GvDataList.GvType.CHILDREN, LayoutChildReview.class);
        mMap.put(GvDataList.GvType.COMMENTS, LayoutComment.class);
        mMap.put(GvDataList.GvType.FACTS, LayoutFact.class);
        mMap.put(GvDataList.GvType.IMAGES, LayoutImage.class);
        mMap.put(GvDataList.GvType.TAGS, LayoutTag.class);
    }

    static <T extends GvDataList.GvData> GvDataViewHolder<T> newViewHolder
            (GvDataList.GvType dataType, GvDataViewAdd.GvDataAdder adder) {
        if (sFactory == null) sFactory = new FactoryGvDataViewHolder();
        try {
            Constructor ctor = sFactory.mMap.get(dataType)
                    .getDeclaredConstructor(GvDataViewAdd.GvDataAdder.class);
            try {
                //TODO make type safe
                GvDataViewLayout layout = (GvDataViewLayout) ctor.newInstance(adder);
                return layout.getViewHolder();
            } catch (InstantiationException e) {
                Log.e(TAG, "Problem constructing ReviewDataAdd dialog for " + dataType
                        .getDatumString(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, "Illegal access whilst constructing ReviewDataAdd dialog for " +
                        dataType.getDatumString(), e);
            } catch (InvocationTargetException e) {
                Log.e(TAG, "Invocation exception whilst constructing ReviewDataAdd dialog for" +
                        " " + dataType.getDatumString(), e);
            }
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "NoSuchMethodException finding constructor", e);
        }

        throw new RuntimeException("DialogReviewDataAddFragment Dialog Constructor problems for "
                + dataType.getDatumString());
    }

    static <T extends GvDataList.GvData> GvDataViewHolder<T> newHolder
            (GvDataList.GvType dataType, GvDataViewEdit.GvDataEditor editor) {
        if (sFactory == null) sFactory = new FactoryGvDataViewHolder();
        try {
            Constructor ctor = sFactory.mMap.get(dataType)
                    .getDeclaredConstructor(GvDataViewEdit.GvDataEditor.class);
            try {
                //TODO make type safe
                GvDataViewLayout dialogGv = (GvDataViewLayout) ctor.newInstance(editor);
                return dialogGv.getViewHolder();
            } catch (InstantiationException e) {
                Log.e(TAG, "Problem constructing edit dialog for " + dataType
                        .getDatumString(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, "Illegal access whilst constructing edit dialog for " + dataType
                        .getDatumString(), e);
            } catch (InvocationTargetException e) {
                Log.e(TAG, "Invocation exception whilst constructing edit dialog for" + dataType
                        .getDatumString(), e);
            }
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "NoSuchMethodException finding constructor", e);
        }

        throw new RuntimeException("DialogReviewDataEditFragment Dialog Constructor problem for "
                + dataType.getDatumString());
    }
}
