/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 15 December, 2014
 */

package com.chdryra.android.reviewer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 15/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryGvData {
    private static FactoryGvData sFactory;
    private final Map<GvDataType, GvTypeList> mClasses = new HashMap<>();

    private FactoryGvData() {
        mClasses.put(gvType(GvTagList.class), new GvTypeList<>(GvTagList.class));
        mClasses.put(gvType(GvChildList.class), new GvTypeList<>(GvChildList.class));
        mClasses.put(gvType(GvCommentList.class), new GvTypeList<>(GvCommentList.class));
        mClasses.put(gvType(GvFactList.class), new GvTypeList<>(GvFactList.class));
        mClasses.put(gvType(GvImageList.class), new GvTypeList<>(GvImageList.class));
        mClasses.put(gvType(GvLocationList.class), new GvTypeList<>(GvLocationList.class));
        mClasses.put(gvType(GvUrlList.class), new GvTypeList<>(GvUrlList.class));
    }

    private static FactoryGvData get() {
        if (sFactory == null) sFactory = new FactoryGvData();
        return sFactory;
    }

    public static <T extends GvData> GvDataType gvType
            (Class<? extends GvDataList<T>> dataClass) {
        return newList(dataClass).getGvDataType();
    }

    //TODO make type safe
    public static <T extends GvData> GvDataList<T> newList(GvDataType
            dataType) {
        return newList(get().mClasses.get(dataType).mList);
    }

    public static <T extends GvData> GvDataList<T> newList(Class<? extends
            GvDataList<T>> dataClass) {
        try {
            return dataClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't create data list: " + dataClass.getName());
        }
    }

    public static <T extends GvData> T newNull(Class<T> dataClass) {
        try {
            return dataClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't create data list: " + dataClass.getName());
        }
    }

    private class GvTypeList<L extends GvDataList<T>, T extends GvData> {
        private final Class<L> mList;

        private GvTypeList(Class<L> list) {
            mList = list;
        }
    }
}
