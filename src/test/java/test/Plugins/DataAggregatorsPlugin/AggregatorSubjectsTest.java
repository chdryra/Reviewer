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
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
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
public class AggregatorSubjectsTest extends AggregatedDistinctItemsTest<DataSubject> {
    @NonNull
    @Override
    protected DataAggregator<DataSubject> newAggregator(DataAggregatorsApi factory, DataAggregatorParams params) {
        return factory.newSubjectsAggregator(params.getSimilarPercentage());
    }

    @NonNull
    @Override
    protected DataSubject randomDatum() {
        return new DatumSubject(RandomReviewId.nextReviewId(), RandomString.nextWord());
    }

    @NonNull
    @Override
    protected DataSubject newSimilarDatum(ReviewId reviewId, DataSubject template) {
        return new DatumSubject(reviewId, RandomString.toRandomCase(template.getSubject()));
    }

    @Override
    protected DataSubject getExampleCanonical(ReviewId id, ArrayList<DataSubject> data) {
        return new DatumSubject(id, data.get(0).getSubject().toLowerCase());
    }
}
