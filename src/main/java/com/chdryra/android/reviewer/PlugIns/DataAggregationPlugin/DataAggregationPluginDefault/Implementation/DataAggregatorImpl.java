package com.chdryra.android.reviewer.PlugIns.DataAggregationPlugin.DataAggregationPluginDefault.Implementation;



import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Algorithms.DataAggregation.Implementation.AggregatedDataImpl;
import com.chdryra.android.reviewer.Algorithms.DataAggregation.Implementation.AggregatedListImpl;
import com.chdryra.android.reviewer.Algorithms.DataAggregation.Interfaces.AggregatedList;
import com.chdryra.android.reviewer.Algorithms.DataAggregation.Interfaces.DataAggregator;
import com.chdryra.android.reviewer.Algorithms.DataAggregation.Interfaces.DifferenceLevel;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.PlugIns.DataAggregationPlugin.DataAggregationPluginDefault
        .Interfaces.CanonicalDatumMaker;
import com.chdryra.android.reviewer.PlugIns.DataAggregationPlugin.DataAggregationPluginDefault
        .Interfaces.DifferenceComparitor;


/**
 * Created by: Rizwan Choudrey
 * On: 02/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DataAggregatorImpl<T extends HasReviewId, D extends DifferenceLevel<D>>
        implements DataAggregator<T> {
    private DifferenceComparitor<? super T, D> mComparitor;
    private D mSimilarityThreshold;
    private CanonicalDatumMaker<T> mCanonical;

    public DataAggregatorImpl(DifferenceComparitor<? super T, D> comparitor,
                              D similarityThreshold,
                              CanonicalDatumMaker<T> canonical) {
        mComparitor = comparitor;
        mSimilarityThreshold = similarityThreshold;
        mCanonical = canonical;
    }

    @Override
    public AggregatedList<T> aggregate(IdableList<? extends T> data) {
        IdableList<T> unallocated = copyData(data);
        AggregatedListImpl<T> results = new AggregatedListImpl<>(data.getReviewId());
        while (unallocated.size() > 0) {
            IdableList<T> allocated = new IdableDataList<>(data.getReviewId());
            T reference = allocateFirstDatumAsReference(unallocated, allocated);
            allocateItemsSimilarToReference(reference, unallocated, allocated);
            addAggregatedDataToResults(results, allocated);
        }

        return results;
    }

    private void allocateItemsSimilarToReference(T reference,
                                                 IdableList<T> unallocated,
                                                 IdableList<T> allocated) {
        int candidate = 0;
        while (candidate < unallocated.size()) {
            candidate = allocateCandidateIfSimilar(unallocated, allocated, reference,
                    candidate);
        }
    }

    private void addAggregatedDataToResults(AggregatedListImpl<T> results, IdableList<T> aggregate) {
        results.add(new AggregatedDataImpl<>(mCanonical.getCanonical(aggregate), aggregate));
    }

    @NonNull
    private IdableList<T> copyData(IdableList<? extends T> data) {
        IdableList<T> unallocated = new IdableDataList<>(data.getReviewId());
        unallocated.addAll(data);
        return unallocated;
    }

    private int allocateCandidateIfSimilar(IdableList<T> unallocated, IdableList<T> aggregated,
                                           T reference, int index) {
        T candidate = unallocated.getItem(index);
        D difference = mComparitor.compare(reference, candidate);
        if (difference.lessThanOrEqualTo(mSimilarityThreshold)) {
            aggregated.add(candidate);
            unallocated.remove(candidate);
        } else {
            ++index;
        }

        return index;
    }

    private T allocateFirstDatumAsReference(IdableList<T> unallocated, IdableList<T> similar) {
        T reference = unallocated.getItem(0);
        similar.add(reference);
        unallocated.remove(reference);
        return reference;
    }
}


