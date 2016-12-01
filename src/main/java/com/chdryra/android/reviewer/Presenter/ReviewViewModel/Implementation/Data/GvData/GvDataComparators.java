/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.Api.DataComparatorsApi;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 03/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
//TODO make this non-static and injectable
public class GvDataComparators {
    private static GvDataComparators sComparator;
    private ComparatorMappings mMap = new ComparatorMappings();

    private GvDataComparators(DataComparatorsApi comparatorsFactory) {
        mMap = new ComparatorMappings();
        mMap.put(GvSubject.TYPE, comparatorsFactory.newSubjectComparators().getDefault());
        mMap.put(GvAuthor.TYPE, comparatorsFactory.newAuthorComparators().getDefault());
        mMap.put(GvCriterion.TYPE, comparatorsFactory.newCriterionComparators().getDefault());
        mMap.put(GvComment.TYPE, comparatorsFactory.newCommentComparators().getDefault());
        mMap.put(GvDate.TYPE, comparatorsFactory.newDateTimeComparators().getDefault());
        mMap.put(GvFact.TYPE, comparatorsFactory.newFactComparators().getDefault());
        mMap.put(GvImage.TYPE, comparatorsFactory.newImageComparators().getDefault());
        mMap.put(GvLocation.TYPE, comparatorsFactory.newLocationComparators().getDefault());
        mMap.put(GvNode.TYPE, comparatorsFactory.newReviewComparators().getDefault());
        mMap.put(GvSocialPlatform.TYPE, comparatorsFactory.newSocialPlatformComparators().getDefault());
        mMap.put(GvTag.TYPE, comparatorsFactory.newTagComparators().getDefault());
        mMap.put(GvUrl.TYPE, comparatorsFactory.newUrlComparators().getDefault());
    }

    //Static methods
    public static void initialise(DataComparatorsApi comparators) {
        if (sComparator == null) sComparator = new GvDataComparators(comparators);
    }

    public static GvDataComparators getInstance() {
        return sComparator;
    }

    public static <T extends GvData> Comparator<? super T> getDefaultComparator(GvDataType<T> elementType) {
        Comparator<? super T> sorter = sComparator.mMap.get(elementType);
        if(sorter != null) {
            return sorter;
        } else {
            return newBasicComparator(elementType);
        }
    }

    @NonNull
    private static <T extends GvData> Comparator<T> newBasicComparator(GvDataType<T> elementType) {
        return new Comparator<T>() {
            @Override
            public int compare(T lhs, T rhs) {
                return lhs.toString().compareToIgnoreCase(rhs.toString());
            }
        };
    }

    private class ComparatorMappings {
        private final Map<GvDataType<? extends GvData>, Comparator<?>> mMap = new HashMap<>();


        private <T extends GvData> void put(GvDataType<T> dataType, Comparator<? super T> comparator) {
            mMap.put(dataType, comparator);
        }

        //TODO make type safe (although it kind of is really...)
        private <T extends GvData> Comparator<? super T> get(GvDataType<T> dataType) {
            return (Comparator<? super T>) mMap.get(dataType);
        }
    }
}
