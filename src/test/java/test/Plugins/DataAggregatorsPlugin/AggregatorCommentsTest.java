/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.DataAggregatorsPlugin;

import android.support.annotation.NonNull;

import com.chdryra.android.startouch.Algorithms.DataAggregation.Interfaces.DataAggregator;
import com.chdryra.android.startouch.Algorithms.DataAggregation.Interfaces.DataAggregatorParams;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.Api
        .DataAggregatorsApi;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumComment;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.testutils.RandomString;

import java.util.ArrayList;

import test.TestUtils.RandomReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 07/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class AggregatorCommentsTest extends AggregatedDistinctItemsTest<DataComment> {
    @NonNull
    @Override
    protected DataAggregator<DataComment> newAggregator(DataAggregatorsApi factory,
                                                        DataAggregatorParams params) {
        return factory.newCommentsAggregator(params.getSimilarPercentage());
    }

    @NonNull
    @Override
    protected DataComment randomDatum() {
        return new DatumComment(RandomReviewId.nextReviewId(), RandomString.nextWord(), getRAND()
                .nextBoolean());
    }

    @NonNull
    @Override
    protected DataComment newSimilarDatum(ReviewId reviewId, DataComment template) {
        return new DatumComment(reviewId, template.getComment(), getRAND().nextBoolean());
    }

    @Override
    protected DataComment getExampleCanonical(ReviewId id, ArrayList<DataComment> data) {
        return new DatumComment(id, data.get(0).getComment(), false);
    }
}
