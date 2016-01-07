package test.DataAlgorithms;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Factories.FactoryDataAggregator;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.DataAggregator;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
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
    protected DataAggregator<DataTag> newAggregator(FactoryDataAggregator factory) {
        return factory.newTagsAggregator();
    }

    @NonNull
    @Override
    protected DataTag randomDatum() {
        return new DatumTag(RandomReviewId.nextReviewId(), RandomString.nextWord());
    }

    @NonNull
    @Override
    protected DataTag newDatum(ReviewId reviewId, DataTag template) {
        return new DatumTag(reviewId, template.getTag());
    }
}
