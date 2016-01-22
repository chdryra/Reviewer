package test.Plugins.DataAggregationPlugin;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Algorithms.DataAggregation.Interfaces.DataAggregator;
import com.chdryra.android.reviewer.Algorithms.DataAggregation.Interfaces.DataAggregatorParams;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.PlugIns.DataAggregatorsPlugin.Api.FactoryDataAggregator;
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
    protected DataAggregator<DataCriterion> newAggregator(FactoryDataAggregator factory, DataAggregatorParams params) {
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
