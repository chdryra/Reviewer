package com.chdryra.android.reviewer.View.GvDataAggregation;

import com.chdryra.android.reviewer.View.GvDataModel.GvAuthorList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCanonicalList;
import com.chdryra.android.reviewer.View.GvDataModel.GvChildReviewList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDateList;
import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;
import com.chdryra.android.reviewer.View.GvDataModel.GvSubjectList;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;

/**
 * Created by: Rizwan Choudrey
 * On: 14/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class Aggregater {
    private static final DifferenceBoolean SAME_BOOL = new DifferenceBoolean(false);
    private static final DifferencePercentage SAME_PCNT = new DifferencePercentage(0);
    private static final DifferenceDate SAME_DAY = new DifferenceDate(DifferenceDate.DateBucket
            .DAY);
    private static final DifferenceFloat TEN_METRES = new DifferenceFloat(10f);
    private static final DifferenceLocation SAME_LOC = new DifferenceLocation(TEN_METRES,
            SAME_PCNT);

    private Aggregater() {
    }

    public static GvCanonicalList<GvAuthorList.GvAuthor> aggregate(GvAuthorList data) {
        return aggregate(data, new ComparitorGvAuthor(), SAME_BOOL, new CanonicalAuthor());
    }

    public static GvCanonicalList<GvSubjectList.GvSubject> aggregate(GvSubjectList data) {
        return aggregate(data, new ComparitorGvSubject(), SAME_PCNT, new CanonicalSubjectMode());
    }

    public static GvCanonicalList<GvTagList.GvTag> aggregate(GvTagList data) {
        return aggregate(data, new ComparitorGvTag(), SAME_PCNT, new CanonicalTagMode());
    }

    public static GvCanonicalList<GvCommentList.GvComment> aggregate(GvCommentList data) {
        return aggregate(data, new ComparitorGvComment(), SAME_PCNT, new CanonicalCommentMode());
    }

    public static GvCanonicalList<GvDateList.GvDate> aggregate(GvDateList data) {
        return aggregate(data, new ComparitorGvDate(), SAME_DAY, new CanonicalDate());
    }

    public static GvCanonicalList<GvImageList.GvImage> aggregate(GvImageList data) {
        return aggregate(data, new ComparitorGvImageBitmap(), SAME_BOOL, new CanonicalImage());
    }

    public static GvCanonicalList<GvLocationList.GvLocation> aggregate(GvLocationList data) {
        return aggregate(data, new ComparitorGvLocation(), SAME_LOC, new CanonicalLocation());
    }

    public static GvCanonicalList<GvChildReviewList.GvChildReview> aggregate(GvChildReviewList
                                                                                     data) {
        return aggregate(data, new ComparitorGvChildReview(), SAME_PCNT, new CanonicalChildReview
                ());
    }

    public static GvCanonicalList<GvFactList.GvFact> aggregate(GvFactList data) {
        return aggregate(data, new ComparitorGvFactLabel(), SAME_PCNT, new CanonicalFact());
    }

    private static <T extends GvData, D1, D2 extends DifferenceLevel<D1>> GvCanonicalList<T>
    aggregate(GvDataList<T> data,
              DifferenceComparitor<T, D2> comparitor,
              D1 threshold,
              CanonicalDatumMaker<T> canonical) {
        GvDataAggregater<T, D1, D2> aggregater = new GvDataAggregater<>(comparitor, threshold,
                canonical);
        return aggregater.aggregate(data);
    }
}
