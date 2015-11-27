package com.chdryra.android.reviewer.View.DataSorting;

import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvAuthor;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvComment;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvCriterion;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvDate;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvFact;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvImage;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvLocation;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvReviewOverview;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvSocialPlatform;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvSubject;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvTag;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvUrl;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Interfaces.GvData;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvDataType;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvText;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 03/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvDataComparators {
    private static GvDataComparators sComparator;
    private ComparatorMappings mMap;

    private GvDataComparators() {
        mMap = new ComparatorMappings();
        mMap.put(GvText.TYPE, TextComparators.getComparators());
        mMap.put(GvSubject.TYPE, SubjectComparators.getComparators());
        mMap.put(GvAuthor.TYPE, AuthorComparators.getComparators());
        mMap.put(GvCriterion.TYPE, ChildReviewComparators.getComparators());
        mMap.put(GvComment.TYPE, CommentComparators.getComparators());
        mMap.put(GvDate.TYPE, DateComparators.getComparators());
        mMap.put(GvFact.TYPE, FactComparators.getComparators());
        mMap.put(GvImage.TYPE, ImageComparators.getComparators());
        mMap.put(GvLocation.TYPE, LocationComparators.getComparators());
        mMap.put(GvReviewOverview.TYPE, ReviewOverviewComparators
                .getComparators());
        mMap.put(GvSocialPlatform.TYPE, SocialPlatformComparators
                .getComparators());
        mMap.put(GvTag.TYPE, TagComparators.getComparators());
        mMap.put(GvUrl.TYPE, UrlComparators.getComparators());
    }

    //Static methods
    public static <T extends GvData> Comparator<T> getDefaultComparator(GvDataType<T> elementType) {
        ComparatorCollection<T> sorters = get().mMap.get(elementType);
        return sorters != null ? sorters.getDefault() : new Comparator<T>() {
            //Overridden
            @Override
            public int compare(T lhs, T rhs) {
                return 0;
            }
        };
    }

    private static GvDataComparators get() {
        if (sComparator == null) sComparator = new GvDataComparators();
        return sComparator;
    }

    //To help with type safety
    private class ComparatorMappings {
        private Map<GvDataType<? extends GvData>, ComparatorCollection<? extends GvData>> mMap =
                new HashMap<>();

        private <T extends GvData> void put(GvDataType<T> dataType, ComparatorCollection<T>
                sorters) {
            mMap.put(dataType, sorters);
        }

        //TODO make type safe (although it kind of is really...)
        private <T extends GvData> ComparatorCollection<T> get(GvDataType<T> dataType) {
            return (ComparatorCollection<T>) mMap.get(dataType);
        }
    }
}
