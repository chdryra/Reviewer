package test.DataAlgorithms;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Factories.FactoryDataAggregator;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.DataAggregator;
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
    protected DataAggregator<DataAuthorReview> newAggregator(FactoryDataAggregator factory) {
        return factory.newAuthorsAggregator();
    }

    @NonNull
    @Override
    protected DataAuthorReview randomDatum() {
        return RandomAuthor.nextAuthorReview();
    }

    @NonNull
    @Override
    protected DatumAuthorReview newDatum(ReviewId reviewId, DataAuthorReview template) {
        return new DatumAuthorReview(reviewId, template.getName(), template.getUserId());
    }
}
