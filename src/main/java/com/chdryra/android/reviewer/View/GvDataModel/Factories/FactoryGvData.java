/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 15 December, 2014
 */

package com.chdryra.android.reviewer.View.GvDataModel.Factories;

import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvAuthor;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvAuthorList;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvComment;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvCriterion;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvCriterionList;
import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvDate;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvDateList;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvFact;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvFactList;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvImage;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvLocation;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvLocationList;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvReviewId;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvReviewOverview;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvReviewOverviewList;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvSubject;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvSubjectList;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvTag;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvTagList;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvUrl;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvUrlList;
import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvData;

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
    private static final String INVOCATION_ERR = "Exception thrown by constructor: ";
    private static final String INSTANTIATION_ERR = "Constructor not found: ";
    private static final String ILLEGAL_ACCESS_ERR = "Access not allowed to this constructor: ";

    private final ListsMap mMap = new ListsMap();

    //Static methods
    public <T extends GvData> GvDataList<T> newDataList(GvDataType<T> dataType) {
        return newDataList(mMap.get(dataType));
    }

    public <T extends GvData> GvDataList<T> newDataList(GvDataType<T> dataType,
                                                               GvReviewId id) {
        return newDataList(mMap.get(dataType), id);
    }

    public <T1 extends GvData, T2 extends GvDataList<T1>> T2 newDataList(Class<T2> listClass) {
        try {
            return listClass.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(INSTANTIATION_ERR + listClass.getName());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(ILLEGAL_ACCESS_ERR + listClass.getName());
        }
    }

    public <T1 extends GvData, T2 extends GvDataList<T1>> T2 newDataList(
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

    public <T extends GvData> T newNull(Class<T> dataClass) {
        try {
            return dataClass.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(INSTANTIATION_ERR + dataClass.getName());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(ILLEGAL_ACCESS_ERR + dataClass.getName());
        }
    }

    //To aid type safety
    private class ListsMap {
        private Map<GvDataType<?>, Class<? extends GvDataList<?>>> mClasses = new HashMap<>();

        //Constructors
        public ListsMap() {
            add(GvTag.TYPE, GvTagList.class);
            add(GvCriterion.TYPE, GvCriterionList.class);
            add(GvComment.TYPE, GvCommentList.class);
            add(GvFact.TYPE, GvFactList.class);
            add(GvImage.TYPE, GvImageList.class);
            add(GvLocation.TYPE, GvLocationList.class);
            add(GvUrl.TYPE, GvUrlList.class);
            add(GvAuthor.TYPE, GvAuthorList.class);
            add(GvDate.TYPE, GvDateList.class);
            add(GvSubject.TYPE, GvSubjectList.class);
            add(GvReviewOverview.TYPE, GvReviewOverviewList.class);
        }

        private <T1 extends GvData, T2 extends GvDataList<T1>> void add(GvDataType<T1> dataType,
                                                                        Class<T2> listType) {
            mClasses.put(dataType, listType);
        }

        //TODO make type safe but it is really....
        private <T extends GvData> Class<? extends GvDataList<T>> get(GvDataType<T> dataType) {
            return (Class<? extends GvDataList<T>>) mClasses.get(dataType);
        }
    }
}
