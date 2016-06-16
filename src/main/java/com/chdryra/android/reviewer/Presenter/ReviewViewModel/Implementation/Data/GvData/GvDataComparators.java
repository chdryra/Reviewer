/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Algorithms.DataSorting.ComparatorCollection;
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
        mMap.put(GvSubject.TYPE, comparatorsFactory.getSubjectComparators());
        mMap.put(GvAuthor.TYPE, comparatorsFactory.getAuthorComparators());
        mMap.put(GvCriterion.TYPE, comparatorsFactory.getCriterionComparators());
        mMap.put(GvComment.TYPE, comparatorsFactory.getCommentComparators());
        mMap.put(GvDate.TYPE, comparatorsFactory.getDateComparators());
        mMap.put(GvFact.TYPE, comparatorsFactory.getFactComparators());
        mMap.put(GvImage.TYPE, comparatorsFactory.getImageComparators());
        mMap.put(GvLocation.TYPE, comparatorsFactory.getLocationComparators());
        mMap.put(GvReview.TYPE, comparatorsFactory.getReviewInfoComparators());
        mMap.put(GvSocialPlatform.TYPE, comparatorsFactory.getSocialPlatformComparators());
        mMap.put(GvTag.TYPE, comparatorsFactory.getTagComparators());
        mMap.put(GvUrl.TYPE, comparatorsFactory.getUrlComparators());
    }

    //Static methods
    public static void initialise(DataComparatorsApi comparators) {
        if (sComparator == null) sComparator = new GvDataComparators(comparators);
    }

    public static <T extends GvData> Comparator<? super T> getDefaultComparator(GvDataType<T> elementType) {
        ComparatorCollection<? super T> sorters = sComparator.mMap.get(elementType);
        if(sorters != null) {
            return sorters.getDefault();
        } else {
            return getComparator(elementType);
        }
    }

    @NonNull
    private static <T extends GvData> Comparator<T> getComparator(GvDataType<T> elementType) {
        return new Comparator<T>() {
            @Override
            public int compare(T lhs, T rhs) {
                return lhs.toString().compareToIgnoreCase(rhs.toString());
            }
        };
    }

    //To help with type safety
    private class ComparatorMappings {
        private Map<GvDataType<? extends GvData>, ComparatorCollection<?>> mMap =
                new HashMap<>();

        private <T extends GvData> void put(GvDataType<T> dataType, ComparatorCollection<? super T>
                sorters) {
            mMap.put(dataType, sorters);
        }

        //TODO make type safe (although it kind of is really...)
        private <T extends GvData> ComparatorCollection<? super T> get(GvDataType<T> dataType) {
            return (ComparatorCollection<? super T>) mMap.get(dataType);
        }
    }
}
