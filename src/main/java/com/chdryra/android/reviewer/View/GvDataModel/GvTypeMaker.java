/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 26 June, 2015
 */

package com.chdryra.android.reviewer.View.GvDataModel;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 26/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvTypeMaker {
    private static GvTypeMaker           sMaker;
    private        ArrayList<GvDataType> mTypes;

    private GvTypeMaker() {
        mTypes = new ArrayList<>();
    }

    private static GvTypeMaker get() {
        if (sMaker == null) sMaker = new GvTypeMaker();
        return sMaker;
    }

    public static <T extends GvData> GvDataType<T> newType(Class<T> dataClass, String datum) {
        return get().createType(dataClass, datum);
    }

    public static <T extends GvData> GvDataType<T> newType(Class<T> dataClass, String datum,
            String data) {
        return get().createType(dataClass, datum, data);
    }

    public static <T1 extends GvData, T2 extends GvDataCollection<T1>> GvDataType<T2>
    newType(Class<T2> dataClass, GvDataType<T1> elementType) {
        return get().createCompoundType(dataClass, elementType);
    }

    private <T extends GvData> GvDataType<T> createType(Class<T> dataClass, String datum) {
        return returnOrThrow(new GvDataType<>(dataClass, datum));
    }

    private <T extends GvData> GvDataType<T> createType(Class<T> dataClass, String datum, String
            data) {
        return returnOrThrow(new GvDataType<>(dataClass, datum, data));
    }

    private <T1 extends GvData, T2 extends GvDataCollection<T1>> GvDataType<T2> createCompoundType
            (Class<T2> dataClass, GvDataType<T1> elementType) {
        return returnOrThrow(GvDataType.compoundType(dataClass, elementType));
    }

    private <T extends GvData> GvDataType<T> returnOrThrow(GvDataType<T> dataType) {
//        if (mTypes.contains(dataType)) {
//            String message = "Compound (" + dataType.getDataClass().getName() + ", " +
//                    dataType.getDatumName() + ", " + dataType.getDataName() + " already exists!";
//            throw new IllegalArgumentException(message);
//        } else {
//            mTypes.add(dataType);
//            return dataType;
//        }

        return dataType;
    }
}
