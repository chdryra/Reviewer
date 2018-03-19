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
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumAuthorName;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataAuthorName;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;

import test.TestUtils.RandomAuthor;

/**
 * Created by: Rizwan Choudrey
 * On: 07/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class AggregatorAuthorsTest extends AggregatedDistinctItemsTest<DataAuthorName> {
    @NonNull
    @Override
    protected DataAggregator<DataAuthorName> newAggregator(DataAggregatorsApi factory,
                                                           DataAggregatorParams params) {
        return factory.newAuthorsAggregator(params.getSimilarBoolean());
    }

    @NonNull
    @Override
    protected DataAuthorName randomDatum() {
        return RandomAuthor.nextDataAuthor();
    }

    @NonNull
    @Override
    protected DatumAuthorName newSimilarDatum(ReviewId reviewId, DataAuthorName template) {
        return new DatumAuthorName(reviewId, template.getName(), template.getAuthorId());
    }
}
