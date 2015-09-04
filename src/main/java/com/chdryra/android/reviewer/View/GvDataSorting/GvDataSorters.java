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
public class GvDataSorters {
    private static GvDataSorters sComparator;
    private ComparatorMappings mMap;

    private GvDataSorters() {
        mMap = new ComparatorMappings();
        mMap.put(GvText.TYPE, TextSorters.getSorters());
        mMap.put(GvSubjectList.GvSubject.TYPE, SubjectSorters.getSorters());
        mMap.put(GvAuthorList.GvAuthor.TYPE, AuthorSorters.getSorters());
        mMap.put(GvChildReviewList.GvChildReview.TYPE, ChildReviewSorters.getSorters());
        mMap.put(GvCommentList.GvComment.TYPE, CommentSorters.getSorters());
        mMap.put(GvDateList.GvDate.TYPE, DateSorters.getSorters());
        mMap.put(GvFactList.GvFact.TYPE, FactSorters.getSorters());
        mMap.put(GvImageList.GvImage.TYPE, ImageSorters.getSorters());
        mMap.put(GvLocationList.GvLocation.TYPE, LocationSorters.getSorters());
        mMap.put(GvReviewOverviewList.GvReviewOverview.TYPE, ReviewOverviewSorters.getSorters());
        mMap.put(GvSocialPlatformList.GvSocialPlatform.TYPE, SocialPlatformSorters.getSorters());
        mMap.put(GvTagList.GvTag.TYPE, TagSorters.getSorters());
        mMap.put(GvUrlList.GvUrl.TYPE, UrlSorters.getSorters());
    }

    private static GvDataSorters get() {
        if (sComparator == null) sComparator = new GvDataSorters();
        return sComparator;
    }

    public static <T extends GvData> Comparator<T> getDefaultComparator(GvDataType<T> elementType) {
        SortingCollection<T> sorters = get().mMap.get(elementType);
        return sorters != null ? sorters.getDefault() : new Comparator<T>() {
            @Override
            public int compare(T lhs, T rhs) {
                return 0;
            }
        };
    }

    //To help with type safety
    private class ComparatorMappings {
        private Map<GvDataType<? extends GvData>, SortingCollection<? extends GvData>> mMap =
                new HashMap<>();

        private <T extends GvData> void put(GvDataType<T> dataType, SortingCollection<T> sorters) {
            mMap.put(dataType, sorters);
        }

        //TODO make type safe (although it kind of is really...)
        private <T extends GvData> SortingCollection<T> get(GvDataType<T> dataType) {
            return (SortingCollection<T>) mMap.get(dataType);
        }
    }
}
