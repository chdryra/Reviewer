package test.Plugins;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.PlugIns.DataAggregationPlugin.DataAggregationPluginDefault.DataAggregationPluginDefault.FactoryDataAggregatorDefault;
import com.chdryra.android.reviewer.Algorithms.DataAggregation.Interfaces.DataAggregator;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;

import test.TestUtils.RandomAuthor;

/**
 * Created by: Rizwan Choudrey
 * On: 07/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class AggregatorAuthorsTest extends AggregatedDistinctItemsTest<DataAuthorReview>{
    @NonNull
    @Override
    protected DataAggregator<DataAuthorReview> newAggregator(FactoryDataAggregatorDefault factory) {
        return factory.newAuthorsAggregator();
    }

    @NonNull
    @Override
    protected DataAuthorReview randomDatum() {
        return RandomAuthor.nextAuthorReview();
    }

    @NonNull
    @Override
    protected DatumAuthorReview newSimilarDatum(ReviewId reviewId, DataAuthorReview template) {
        return new DatumAuthorReview(reviewId, template.getName(), template.getUserId());
    }
}
