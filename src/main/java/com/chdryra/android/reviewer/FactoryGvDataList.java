/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 15 December, 2014
 */

package com.chdryra.android.reviewer;

/**
 * Created by: Rizwan Choudrey
 * On: 15/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryGvDataList {
    public static <T extends GvDataList.GvData> GvDataList.GvType gvType(Class<? extends
            GvDataList<T>> dataClass) {
        return create(dataClass).getGvType();
    }

    public static <T extends GvDataList.GvData> GvDataList<T> create(Class<? extends
            GvDataList<T>> dataClass) {
        try {
            return dataClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't create data list: " + dataClass.getName());
        }
    }
}
