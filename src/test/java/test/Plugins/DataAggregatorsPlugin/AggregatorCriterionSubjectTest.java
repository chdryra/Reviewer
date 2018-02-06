/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.DataAggregatorsPlugin;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.startouch.Algorithms.DataAggregation.Interfaces.DataAggregator;
import com.chdryra.android.startouch.Algorithms.DataAggregation.Interfaces.DataAggregatorParams;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumCriterion;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataCriterion;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.Api.DataAggregatorsApi;
import com.chdryra.android.testutils.RandomString;

import junit.framework.Assert;

import java.util.ArrayList;

import test.TestUtils.RandomRating;
import test.TestUtils.RandomReviewId;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 08/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class AggregatorCriterionSubjectTest extends AggregatedDistinctItemsTest<DataCriterion>{
    @NonNull
    @Override
    protected DataAggregator<DataCriterion> newAggregator(DataAggregatorsApi factory, DataAggregatorParams params) {
        return factory.newCriteriaAggregatorSameSubject(params.getSimilarPercentage());
    }

    @Override
    @NonNull
    protected DataCriterion newSimilarDatum(ReviewId reviewId, DataCriterion template) {
        return new DatumCriterion(reviewId, template.getSubject(), RandomRating.nextRating());
    }

    @Override
    @NonNull
    protected DataCriterion randomDatum() {
        return new DatumCriterion(RandomReviewId.nextReviewId(), RandomString.nextWord(),
                RandomRating.nextRating());
    }

    @Override
    protected DataCriterion getExampleCanonical(ReviewId id, ArrayList<DataCriterion> data) {
        float averageRating = 0f;
        for(DataCriterion criterion : data) {
            averageRating += criterion.getRating() / (float)data.size();
        }

        return new DatumCriterion(id, data.get(0).getSubject(), averageRating);
    }

    @Override
    protected void checkCanonicalItemsSize(DataCriterion canonical, int size) {
        DataCriterion keyEquivalent = findKey(canonical);
        Assert.assertNotNull(keyEquivalent);
        super.checkCanonicalItemsSize(keyEquivalent, size);
    }

    @Override
    protected void checkCanonicalInMap(DataCriterion canonical) {
        DataCriterion keyEquivalent = findKey(canonical);
        assertThat(keyEquivalent, not(nullValue()));
    }

    //because key dependent on calculated float value that might differ by eps
    @Nullable
    private DataCriterion findKey(DataCriterion canonical) {
        float rating = canonical.getRating();
        DataCriterion keyEquivalent = null;
        for(DataCriterion key : mCanonicalsMap.keySet()) {
            float diff = key.getRating() - rating;
            if(Math.sqrt(diff*diff) < 1e-5) keyEquivalent = key;
        }
        return keyEquivalent;
    }
}
