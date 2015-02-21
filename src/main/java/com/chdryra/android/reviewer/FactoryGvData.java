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
    private Map<GvDataList.GvType, Class<? extends GvDataList<? extends GvDataList.GvData>>>
            mClasses = new HashMap<>();

    private FactoryGvData() {
        mClasses.put(gvType(GvTagList.class), GvTagList.class);
        mClasses.put(gvType(GvChildrenList.class), GvChildrenList.class);
        mClasses.put(gvType(GvCommentList.class), GvCommentList.class);
        mClasses.put(gvType(GvFactList.class), GvFactList.class);
        mClasses.put(gvType(GvImageList.class), GvImageList.class);
        mClasses.put(gvType(GvLocationList.class), GvLocationList.class);
        mClasses.put(gvType(GvUrlList.class), GvUrlList.class);
    }

    private static FactoryGvData get() {
        if (sFactory == null) sFactory = new FactoryGvData();
        return sFactory;
    }

    public static <T extends GvDataList.GvData> GvDataList.GvType gvType(Class<? extends
            GvDataList<T>> dataClass) {
        return newList(dataClass).getGvType();
    }

    public static <T extends GvDataList.GvData> GvDataList<T> newList(GvDataList.GvType dataType) {
        return newList(get().mClasses.get(dataType));
    }

    public static <T extends GvDataList.GvData> GvDataList<T> newList(Class<? extends
            GvDataList<T>> dataClass) {
        try {
            return dataClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't create data list: " + dataClass.getName());
        }
    }

    public static <T extends GvDataList.GvData> T newNull(Class<T> dataClass) {
        try {
            return dataClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't create data list: " + dataClass.getName());
        }
    }

    private class GvTypePair<T extends GvDataList.GvData> {
        private GvDataList.GvType              dataType;
        private Class<? extends GvDataList<T>> list;

        private GvTypePair(Class<? extends GvDataList<T>> list) {
            dat
        }
    }
}
