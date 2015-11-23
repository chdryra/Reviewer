package com.chdryra.android.reviewer.View.GvDataAggregation;

import com.chdryra.android.reviewer.View.GvDataModel.FactoryGvData;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvAuthor;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvAuthorList;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvCanonicalCollection;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvComment;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvCriterion;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvCriterionList;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvDate;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvFact;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvImage;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvLocation;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvSubject;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvTag;
import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvDateList;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvFactList;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvLocationList;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvSubjectList;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvTagList;

/**
 * Created by: Rizwan Choudrey
 * On: 14/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvDataAggregater {
    private static final DifferenceBoolean SAME_BOOL = new DifferenceBoolean(false);
    private static final DifferencePercentage SAME_PCNT = new DifferencePercentage(0);
    private static final DifferenceDate SAME_DAY = new DifferenceDate(DifferenceDate.DateBucket
            .DAY);
    private static final DifferenceFloat TEN_METRES = new DifferenceFloat(10f);
    private static final DifferenceLocation SAME_LOC = new DifferenceLocation(TEN_METRES,
            SAME_PCNT);
    private FactoryGvData mDataFactory;

    public GvDataAggregater(FactoryGvData dataFactory) {
        mDataFactory = dataFactory;
    }

    public GvCanonicalCollection<GvAuthor> getAggregate(GvAuthorList data) {
        return newAggregate(data, new ComparitorGvAuthor(), SAME_BOOL, new CanonicalAuthor()).get();
    }

    public GvCanonicalCollection<GvSubject> getAggregate(GvSubjectList data) {
        return newAggregate(data, new ComparitorGvSubject(), SAME_PCNT, new CanonicalSubjectMode()).get();
    }

    public GvCanonicalCollection<GvTag> getAggregate(GvTagList data) {
        return newAggregate(data, new ComparitorGvTag(), SAME_PCNT, new CanonicalTagMode()).get();
    }

    public GvCanonicalCollection<GvComment> getAggregate(GvCommentList data) {
        return newAggregate(data, new ComparitorGvComment(), SAME_PCNT, new CanonicalCommentMode()).get();
    }

    public GvCanonicalCollection<GvDate> getAggregate(GvDateList data) {
        return newAggregate(data, new ComparitorGvDate(), SAME_DAY, new CanonicalDate()).get();
    }

    public GvCanonicalCollection<GvImage> getAggregate(GvImageList data) {
        return newAggregate(data, new ComparitorGvImageBitmap(), SAME_BOOL, new CanonicalImage()).get();
    }

    public GvCanonicalCollection<GvLocation> getAggregate(GvLocationList data) {
        return newAggregate(data, new ComparitorGvLocation(), SAME_LOC, new CanonicalLocation()).get();
    }

    public GvCanonicalCollection<GvCriterion> getAggregate(GvCriterionList data, boolean mode) {
        if(mode) {
            return newAggregate(data, new ComparitorGvCriterion(), SAME_BOOL,
                    new CanonicalCriterionMode()).get();
        } else {
            return newAggregate(data, new ComparitorGvCriterionSubject(), SAME_PCNT, new
                    CanonicalCriterionAverage()).get();
        }

    }

    public GvCanonicalCollection<GvFact> getAggregate(GvFactList data) {
        return newAggregate(data, new ComparitorGvFactLabel(), SAME_PCNT, new CanonicalFact()).get();
    }

    private <T extends GvData, D1, D2 extends DifferenceLevel<D1>> GvDataAggregate<T>
    newAggregate(GvDataList<T> data,
                 DifferenceComparitor<T, D2> comparitor,
                 D1 threshold,
                 CanonicalDatumMaker<T> maker) {
        return new GvDataAggregate<>(data, new GvDataAggregator<>(comparitor, threshold, maker, mDataFactory));
    }

    /**
     * Created by: Rizwan Choudrey
     * On: 03/11/2015
     * Email: rizwan.choudrey@gmail.com
     */
    private class GvDataAggregate<T extends GvData> {
        private GvDataList<T> mData;
        private GvDataAggregator<T, ?, ? extends DifferenceLevel<?>> mAggregator;

        private GvDataAggregate(GvDataList<T> data,
                               GvDataAggregator<T, ?, ? extends DifferenceLevel<?>> aggregator) {
            mData = data;
            mAggregator = aggregator;
        }

        private GvCanonicalCollection<T> get() {
            return mAggregator.aggregate(mData);
        }
    }
}
