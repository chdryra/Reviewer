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
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.Api.DataAggregatorsApi;

import test.TestUtils.RandomAuthor;

/**
 * Created by: Rizwan Choudrey
 * On: 07/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class AggregatorAuthorsTest extends AggregatedDistinctItemsTest<DataAuthorReview>{
    @NonNull
    @Override
    protected DataAggregator<DataAuthorReview> newAggregator(DataAggregatorsApi factory, DataAggregatorParams params) {
        return factory.newAuthorsAggregator(params.getSimilarBoolean());
    }

    @NonNull
    @Override
    protected DataAuthorReview randomDatum() {
        return RandomAuthor.nextAuthorReview();
    }

    @NonNull
    @Override
    protected DatumAuthorReview newSimilarDatum(ReviewId reviewId, DataAuthorReview template) {
        return new DatumAuthorReview(reviewId, template.getName(), template.getAuthorId());
    }
}
