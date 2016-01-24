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
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.PlugIns.DataAggregatorsPlugin.Api.FactoryDataAggregator;
import com.chdryra.android.testutils.RandomString;

import test.TestUtils.RandomReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 07/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class AggregatorTagsTest extends AggregatedDistinctItemsTest<DataTag>{
    @NonNull
    @Override
    protected DataAggregator<DataTag> newAggregator(FactoryDataAggregator factory, DataAggregatorParams params) {
        return factory.newTagsAggregator(params.getSimilarPercentage());
    }

    @NonNull
    @Override
    protected DataTag randomDatum() {
        return new DatumTag(RandomReviewId.nextReviewId(), RandomString.nextWord());
    }

    @NonNull
    @Override
    protected DataTag newSimilarDatum(ReviewId reviewId, DataTag template) {
        return new DatumTag(reviewId, template.getTag());
    }
}
