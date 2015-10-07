package com.chdryra.android.reviewer.View.GvDataAggregation;

import com.chdryra.android.reviewer.View.GvDataModel.GvAuthorList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCanonicalCollection;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCriterionList;
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

    //Static methods
    public static GvCanonicalCollection<GvAuthorList.GvAuthor> aggregate(GvAuthorList data) {
        return aggregate(data, new ComparitorGvAuthor(), SAME_BOOL, new CanonicalAuthor());
    }

    public static GvCanonicalCollection<GvSubjectList.GvSubject> aggregate(GvSubjectList data) {
        return aggregate(data, new ComparitorGvSubject(), SAME_PCNT, new CanonicalSubjectMode());
    }

    public static GvCanonicalCollection<GvTagList.GvTag> aggregate(GvTagList data) {
        return aggregate(data, new ComparitorGvTag(), SAME_PCNT, new CanonicalTagMode());
    }

    public static GvCanonicalCollection<GvCommentList.GvComment> aggregate(GvCommentList data) {
        return aggregate(data, new ComparitorGvComment(), SAME_PCNT, new CanonicalCommentMode());
    }

    public static GvCanonicalCollection<GvDateList.GvDate> aggregate(GvDateList data) {
        return aggregate(data, new ComparitorGvDate(), SAME_DAY, new CanonicalDate());
    }

    public static GvCanonicalCollection<GvImageList.GvImage> aggregate(GvImageList data) {
        return aggregate(data, new ComparitorGvImageBitmap(), SAME_BOOL, new CanonicalImage());
    }

    public static GvCanonicalCollection<GvLocationList.GvLocation> aggregate(GvLocationList data) {
        return aggregate(data, new ComparitorGvLocation(), SAME_LOC, new CanonicalLocation());
    }

    public static GvCanonicalCollection<GvCriterionList.GvCriterion> aggregate(GvCriterionList
                                                                                       data) {
        return aggregate(data, new ComparitorGvCriterionSubject(), SAME_PCNT, new
                CanonicalCriterionAverage());
    }

    public static GvCanonicalCollection<GvCriterionList.GvCriterion> aggregateCriteriaMode
            (GvCriterionList data) {
        return aggregate(data, new ComparitorGvCriterion(), SAME_BOOL, new
                CanonicalCriterionMode());
    }

    public static GvCanonicalCollection<GvFactList.GvFact> aggregate(GvFactList data) {
        return aggregate(data, new ComparitorGvFactLabel(), SAME_PCNT, new CanonicalFact());
    }

    private static <T extends GvData, D1, D2 extends DifferenceLevel<D1>> GvCanonicalCollection<T>
    aggregate(GvDataList<T> data,
              DifferenceComparitor<T, D2> comparitor,
              D1 threshold,
              CanonicalDatumMaker<T> maker) {
        GvDataAggregater<T, D1, D2> aggregater = new GvDataAggregater<>(comparitor, threshold,
                maker);
        return aggregater.aggregate(data);
    }
}
