/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 21 October, 2014
 */

package com.chdryra.android.reviewer;

import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;


/**
 * Created by: Rizwan Choudrey
 * On: 21/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Creates appropriate {@link GvDataViewHolderBasic} for given
 * {@link GvDataList.GvType}
 */
class FactoryDialogHolder {
    private static final String TAG = "FactoryDialogHolder";
    private static FactoryDialogHolder                                              sFactory;
    private final  HashMap<GvDataList.GvType, Class<? extends GvDataViewHolder<?>>> mDHClassesMap;

    private FactoryDialogHolder() {
        mDHClassesMap = new HashMap<>();
        mDHClassesMap.put(GvDataList.GvType.CHILDREN, DialogHolderChild.class);
        mDHClassesMap.put(GvDataList.GvType.COMMENTS, DialogHolderComment.class);
        mDHClassesMap.put(GvDataList.GvType.FACTS, DialogHolderFact.class);
        mDHClassesMap.put(GvDataList.GvType.IMAGES, DialogHolderImage.class);
        mDHClassesMap.put(GvDataList.GvType.TAGS, DialogHolderTag.class);
    }

    static <T extends GvDataList.GvData> GvDataViewHolder<T> newHolder
            (DialogGvDataAddFragment<T> dialog) {
        if (sFactory == null) sFactory = new FactoryDialogHolder();
        try {
            Constructor ctor = sFactory.mDHClassesMap.get(dialog.getGvType())
                    .getDeclaredConstructor(DialogGvDataAddFragment.class);

            try {
                //TODO make type safe
                return (GvDataViewHolder<T>) ctor.newInstance(dialog);
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
            Log.e(TAG, "DialogReviewDataAddFragment Constructor missing for DialogHolder for" +
                    dialog.getGvType().getDatumString(), e);
        }

        return null;
    }

    static <T extends GvDataList.GvData> GvDataViewHolder<T> newHolder
            (DialogGvDataEditFragment<T> dialog) {
        if (sFactory == null) sFactory = new FactoryDialogHolder();
        try {
            Constructor ctor = sFactory.mDHClassesMap.get(dialog.getGvType())
                    .getDeclaredConstructor(DialogGvDataEditFragment.class);
            try {
                //TODO make type safe
                return (GvDataViewHolder<T>) ctor.newInstance(dialog);
            } catch (InstantiationException e) {
                Log.e(TAG, "Problem constructing edit dialog for " + dialog.getGvType()
                        .getDatumString(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, "Illegal access whilst constructing edit dialog for " +
                        dialog.getGvType().getDatumString(), e);
            } catch (InvocationTargetException e) {
                Log.e(TAG, "Invocation exception whilst constructing edit dialog for" +
                        " " + dialog.getGvType().getDatumString(), e);
            }
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "DialogReviewDataEditFragment Constructor missing for DialogHolder for" +
                    dialog.getGvType().getDatumString(), e);
        }

        return null;
    }
}
