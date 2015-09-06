package com.chdryra.android.reviewer.View.GvDataSorting;

import com.chdryra.android.reviewer.View.GvDataModel.GvAuthorList;
import com.chdryra.android.reviewer.View.GvDataModel.GvChildReviewList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvDateList;
import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewOverviewList;
import com.chdryra.android.reviewer.View.GvDataModel.GvSocialPlatformList;
import com.chdryra.android.reviewer.View.GvDataModel.GvSubjectList;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;
import com.chdryra.android.reviewer.View.GvDataModel.GvText;
import com.chdryra.android.reviewer.View.GvDataModel.GvUrlList;

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
        mMap.put(GvSubjectList.GvSubject.TYPE, SubjectComparators.getComparators());
        mMap.put(GvAuthorList.GvAuthor.TYPE, AuthorComparators.getComparators());
        mMap.put(GvChildReviewList.GvChildReview.TYPE, ChildReviewComparators.getComparators());
        mMap.put(GvCommentList.GvComment.TYPE, CommentComparators.getComparators());
        mMap.put(GvDateList.GvDate.TYPE, DateComparators.getComparators());
        mMap.put(GvFactList.GvFact.TYPE, FactComparators.getComparators());
        mMap.put(GvImageList.GvImage.TYPE, ImageComparators.getComparators());
        mMap.put(GvLocationList.GvLocation.TYPE, LocationComparators.getComparators());
        mMap.put(GvReviewOverviewList.GvReviewOverview.TYPE, ReviewOverviewComparators
                .getComparators());
        mMap.put(GvSocialPlatformList.GvSocialPlatform.TYPE, SocialPlatformComparators
                .getComparators());
        mMap.put(GvTagList.GvTag.TYPE, TagComparators.getComparators());
        mMap.put(GvUrlList.GvUrl.TYPE, UrlComparators.getComparators());
    }

    private static GvDataComparators get() {
        if (sComparator == null) sComparator = new GvDataComparators();
        return sComparator;
    }

    public static <T extends GvData> Comparator<T> getDefaultComparator(GvDataType<T> elementType) {
        ComparatorCollection<T> sorters = get().mMap.get(elementType);
        return sorters != null ? sorters.getDefault() : new Comparator<T>() {
            @Override
            public int compare(T lhs, T rhs) {
                return 0;
            }
        };
    }

    //To help with type safety
    private class ComparatorMappings {
        private Map<GvDataType<? extends GvData>, ComparatorCollection<? extends GvData>> mMap =
                new HashMap<>();

        private <T extends GvData> void put(GvDataType<T> dataType, ComparatorCollection<T> sorters) {
            mMap.put(dataType, sorters);
        }

        //TODO make type safe (although it kind of is really...)
        private <T extends GvData> ComparatorCollection<T> get(GvDataType<T> dataType) {
            return (ComparatorCollection<T>) mMap.get(dataType);
        }
    }
}
