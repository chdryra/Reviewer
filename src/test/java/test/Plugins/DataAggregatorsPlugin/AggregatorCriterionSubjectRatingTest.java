/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.DataAggregatorsPlugin;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Algorithms.DataAggregation.Interfaces.DataAggregator;
import com.chdryra.android.reviewer.Algorithms.DataAggregation.Interfaces.DataAggregatorParams;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.Api.DataAggregatorsApi;
import com.chdryra.android.testutils.RandomString;

import test.TestUtils.RandomRating;
import test.TestUtils.RandomReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 08/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class AggregatorCriterionSubjectRatingTest extends AggregatedDistinctItemsTest<DataCriterion>{
    @NonNull
    @Override
    protected DataAggregator<DataCriterion> newAggregator(DataAggregatorsApi factory, DataAggregatorParams params) {
        return factory.newCriteriaAggregatorSameSubjectRating(params.getSimilarBoolean());
    }

    @Override
    @NonNull
    protected DataCriterion newSimilarDatum(ReviewId reviewId, DataCriterion template) {
        return new DatumCriterion(reviewId, template.getSubject(), template.getRating());
    }

    @Override
    @NonNull
    protected DataCriterion randomDatum() {
        return new DatumCriterion(RandomReviewId.nextReviewId(), RandomString.nextWord(),
                RandomRating.nextRating());
    }
}
