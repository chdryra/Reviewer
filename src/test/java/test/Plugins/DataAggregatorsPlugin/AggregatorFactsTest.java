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
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.Api.DataAggregatorsApi;
import com.chdryra.android.testutils.RandomString;

import java.util.ArrayList;

import test.TestUtils.RandomReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 07/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class AggregatorFactsTest extends AggregatedDistinctItemsTest<DataFact> {
    @NonNull
    @Override
    protected DataAggregator<DataFact> newAggregator(DataAggregatorsApi factory, DataAggregatorParams params) {
        return factory.newFactsAggregator(params.getSimilarPercentage());
    }

    @NonNull
    @Override
    protected DataFact randomDatum() {
        return new DatumFact(RandomReviewId.nextReviewId(), RandomString.nextWord(), RandomString.nextWord());
    }

    @NonNull
    @Override
    protected DataFact newSimilarDatum(ReviewId reviewId, DataFact template) {
        return new DatumFact(reviewId, template.getLabel(), RandomString.nextWord());
    }

    @Override
    protected DataFact getExampleCanonical(ReviewId id, ArrayList<DataFact> data) {
        return new DatumFact(id, data.get(0).getLabel(), String.valueOf(data.size()) + " values");
    }
}
