package com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation;

import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.AggregatedList;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.CanonicalDatumMaker;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.DataAggregator;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.DifferenceComparitor;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.DifferenceLevel;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 02/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DataAggregatorImpl<T extends HasReviewId, D2 extends DifferenceLevel<D2>>
        implements DataAggregator<T> {
    private DifferenceComparitor<T, D2> mComparitor;
    private D2 mMinimumDifference;
    private CanonicalDatumMaker<T> mCanonical;

    //Constructors
    public DataAggregatorImpl(DifferenceComparitor<T, D2> comparitor,
                              D2 minimumDifference,
                              CanonicalDatumMaker<T> canonical) {
        mComparitor = comparitor;
        mMinimumDifference = minimumDifference;
        mCanonical = canonical;
    }

    @Override
    public AggregatedList<T> aggregate(IdableList<? extends T> data) {
        ArrayList<T> allocated = new ArrayList<>();
        AggregatedListImpl<T> results = new AggregatedListImpl<>(data.getReviewId());
        for (T reference : data) {
            if (allocated.contains(reference)) continue;
            IdableList<T> similar = new IdableDataList<>(data.getReviewId());
            similar.add(reference);
            allocated.add(reference);
            for (T candidate : data) {
                if (allocated.contains(candidate)) continue;
                D2 difference = mComparitor.compare(reference, candidate);
                if (difference.lessThanOrEqualTo(mMinimumDifference)) {
                    similar.add(candidate);
                    allocated.add(candidate);
                }
            }

            results.add(new AggregatedDataImpl<>(mCanonical.getCanonical(similar), similar));
        }

        return results;
    }
}


