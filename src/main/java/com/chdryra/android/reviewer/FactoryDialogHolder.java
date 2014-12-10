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
 * Creates appropriate {@link com.chdryra.android.reviewer.DialogHolder} for given
 * {@link GvDataList.GvType}
 */
class FactoryDialogHolder {
    private static final String TAG = "FactoryDialogHolder";
    private static FactoryDialogHolder                                      sFactory;
    private final  HashMap<GvDataList.GvType, Class<? extends UIHolder<?>>> mDHClassesMap;

    private FactoryDialogHolder() {
        mDHClassesMap = new HashMap<GvDataList.GvType, Class<? extends UIHolder<?>>>();
        mDHClassesMap.put(GvDataList.GvType.CHILDREN, DHChild.class);
        mDHClassesMap.put(GvDataList.GvType.COMMENTS, DHComment.class);
        mDHClassesMap.put(GvDataList.GvType.FACTS, DHFact.class);
        mDHClassesMap.put(GvDataList.GvType.IMAGES, DHImageEdit.class);
        mDHClassesMap.put(GvDataList.GvType.TAGS, DHTag.class);
    }

    static <T extends GvDataList.GvData> UIHolder<T> newDialogHolder
            (DialogReviewDataAddFragment<T> dialog) {
        if (sFactory == null) sFactory = new FactoryDialogHolder();
        try {
            Constructor ctor = sFactory.mDHClassesMap.get(dialog.getGVType())
                    .getDeclaredConstructor
                            (DialogReviewDataAddFragment.class);

            try {
                //TODO make type safe
                return (UIHolder<T>) ctor.newInstance(dialog);
            } catch (InstantiationException e) {
                Log.e(TAG, "Problem constructing ReviewDataAdd dialog for " + dialog.getGVType()
                        .getDatumString(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, "Illegal access whilst constructing ReviewDataAdd dialog for " +
                        dialog.getGVType().getDatumString(), e);
            } catch (InvocationTargetException e) {
                Log.e(TAG, "Invocation exception whilst constructing ReviewDataAdd dialog for" +
                        " " + dialog.getGVType().getDatumString(), e);
            }

        } catch (NoSuchMethodException e) {
            Log.e(TAG, "DialogReviewDataAddFragment Constructor missing for DialogHolder for" +
                    dialog.getGVType().getDatumString(), e);
        }

        return null;
    }

    static <T extends GvDataList.GvData> UIHolder<T> newDialogHolder
            (DialogReviewDataEditFragment<T> dialog) {
        if (sFactory == null) sFactory = new FactoryDialogHolder();
        try {
            Constructor ctor = sFactory.mDHClassesMap.get(dialog.getGVType())
                    .getDeclaredConstructor
                            (DialogReviewDataEditFragment.class);
            try {
                //TODO make type safe
                return (UIHolder<T>) ctor.newInstance(dialog);
            } catch (InstantiationException e) {
                Log.e(TAG, "Problem constructing edit dialog for " + dialog.getGVType()
                        .getDatumString(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, "Illegal access whilst constructing edit dialog for " +
                        dialog.getGVType().getDatumString(), e);
            } catch (InvocationTargetException e) {
                Log.e(TAG, "Invocation exception whilst constructing edit dialog for" +
                        " " + dialog.getGVType().getDatumString(), e);
            }
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "DialogReviewDataEditFragment Constructor missing for DialogHolder for" +
                    dialog.getGVType().getDatumString(), e);
        }

        return null;
    }
}
