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
    private static final String TAG = "FactoryDialogHolder";
    private static FactoryGvDataViewHolder sFactory;
    private final  HashMap<GvDataList.GvType, Class<? extends DialogGvData<? extends GvDataList
            .GvData>>>                     mMap;

    private FactoryGvDataViewHolder() {
        mMap = new HashMap<>();
        mMap.put(GvDataList.GvType.CHILDREN, DialogChildReview.class);
        mMap.put(GvDataList.GvType.COMMENTS, DialogComment.class);
        mMap.put(GvDataList.GvType.FACTS, DialogFact.class);
        mMap.put(GvDataList.GvType.IMAGES, DialogImage.class);
        mMap.put(GvDataList.GvType.TAGS, DialogTag.class);
    }

    static <T extends GvDataList.GvData> GvDataViewHolder<T> newHolder
            (DialogGvDataAddFragment<T> dialog) {
        if (sFactory == null) sFactory = new FactoryGvDataViewHolder();
        try {
            Constructor ctor = sFactory.mMap.get(dialog.getGvType())
                    .getDeclaredConstructor(DialogGvDataAddFragment.class);
            try {
                //TODO make type safe
                DialogGvData dialogGv = (DialogGvData) ctor.newInstance(dialog);
                return dialogGv.getViewHolder();
            } catch (InstantiationException e) {
                Log.e(TAG, "Problem constructing ReviewDataAdd dialog for " + dialog.getGvType()
                        .getDatumString(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, "Illegal access whilst constructing ReviewDataAdd dialog for " +
                        dialog.getGvType().getDatumString(), e);
            } catch (InvocationTargetException e) {
                Log.e(TAG, "Invocation exception whilst constructing ReviewDataAdd dialog for" +
                        " " + dialog.getGvType().getDatumString(), e);
            }
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "NoSuchMethodException finding constructor", e);
        }

        throw new RuntimeException("DialogReviewDataAddFragment Dialog Constructor problems for "
                + dialog.getGvType().getDatumString());
    }

    static <T extends GvDataList.GvData> GvDataViewHolder<T> newHolder
            (DialogGvDataEditFragment<T> dialog) {
        if (sFactory == null) sFactory = new FactoryGvDataViewHolder();
        try {
            Constructor ctor = sFactory.mMap.get(dialog.getGvType())
                    .getDeclaredConstructor(DialogGvDataEditFragment.class);
            try {
                //TODO make type safe
                DialogGvData dialogGv = (DialogGvData) ctor.newInstance(dialog);
                return dialogGv.getViewHolder();
            } catch (InstantiationException e) {
                Log.e(TAG, "Problem constructing edit dialog for " + dialog.getGvType()
                        .getDatumString(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, "Illegal access whilst constructing edit dialog for " + dialog
                        .getGvType().getDatumString(), e);
            } catch (InvocationTargetException e) {
                Log.e(TAG, "Invocation exception whilst constructing edit dialog for" + dialog
                        .getGvType().getDatumString(), e);
            }
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "NoSuchMethodException finding constructor", e);
        }

        throw new RuntimeException("DialogReviewDataEditFragment Dialog Constructor problem for "
                + dialog.getGvType().getDatumString());
    }
}
