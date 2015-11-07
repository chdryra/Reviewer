/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 15 December, 2014
 */

package com.chdryra.android.reviewer.View.GvDataModel;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 15/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryGvData {
    private static final String NO_CTOR_ERR = "Constructor not found: ";
    private static final String INSTANTIATION_ERR = "Constructor not found: ";
    private static final String INVOCATION_ERR = "Exception thrown by constructor: ";
    private static final String ILLEGAL_ACCESS_ERR = "Access not allowed to this constructor: ";

    private static FactoryGvData sFactory;
    private final ListsMap mMap = new ListsMap();

    private FactoryGvData() {
    }

    //Static methods
    public static <T extends GvData> GvDataList<T> newDataList(GvDataType<T> dataType) {
        return newDataList(get().mMap.get(dataType));
    }

    public static <T extends GvData> GvDataList<T> newDataList(GvDataType<T> dataType,
                                                               GvReviewId id) {
        return newDataList(get().mMap.get(dataType), id);
    }

    public static <T1 extends GvData, T2 extends GvDataList<T1>> T2 newDataList(Class<T2>
                                                                                        listClass) {
        try {
            return listClass.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(INSTANTIATION_ERR + listClass.getName());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(ILLEGAL_ACCESS_ERR + listClass.getName());
        }
    }

    public static <T1 extends GvData, T2 extends GvDataList<T1>> T2 newDataList(
            Class<T2> listClass, GvReviewId id) {
        if (id == null) return newDataList(listClass);

        try {
            Constructor<T2> ctor = listClass.getConstructor(GvReviewId.class);
            return ctor.newInstance(id);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(NO_CTOR_ERR + listClass.getName(), e);
        } catch (InstantiationException e) {
            throw new RuntimeException(INSTANTIATION_ERR + listClass.getName(), e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(ILLEGAL_ACCESS_ERR + listClass.getName(), e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(INVOCATION_ERR + listClass.getName());
        }
    }

    public static <T extends GvData> T newNull(Class<T> dataClass) {
        try {
            return dataClass.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(INSTANTIATION_ERR + dataClass.getName());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(ILLEGAL_ACCESS_ERR + dataClass.getName());
        }
    }

    private static FactoryGvData get() {
        if (sFactory == null) sFactory = new FactoryGvData();
        return sFactory;
    }

    //To aid type safety
    private class ListsMap {
        private Map<GvDataType, Class> mClasses = new HashMap<>();

        //Constructors
        public ListsMap() {
            add(GvTagList.GvTag.TYPE, GvTagList.class);
            add(GvCriterionList.GvCriterion.TYPE, GvCriterionList.class);
            add(GvCommentList.GvComment.TYPE, GvCommentList.class);
            add(GvFactList.GvFact.TYPE, GvFactList.class);
            add(GvImageList.GvImage.TYPE, GvImageList.class);
            add(GvLocationList.GvLocation.TYPE, GvLocationList.class);
            add(GvUrlList.GvUrl.TYPE, GvUrlList.class);
            add(GvAuthorList.GvAuthor.TYPE, GvAuthorList.class);
            add(GvDateList.GvDate.TYPE, GvDateList.class);
            add(GvSubjectList.GvSubject.TYPE, GvSubjectList.class);
            add(GvReviewOverviewList.GvReviewOverview.TYPE, GvReviewOverviewList.class);
        }

        private <T1 extends GvData, T2 extends GvDataList<T1>> void add(GvDataType<T1> dataType,
                                                                        Class<T2> listType) {
            mClasses.put(dataType, listType);
        }

        //TODO make type safe but it is really....
        private <T extends GvData> Class<? extends GvDataList<T>> get(GvDataType<T> dataType) {
            return mClasses.get(dataType);
        }
    }
}
