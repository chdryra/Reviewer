package test.DataAlgorithms;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Factories.FactoryDataAggregator;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.DataAggregator;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
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
    protected DataAggregator<DataComment> newAggregator(FactoryDataAggregator factory) {
        return factory.newCommentsAggregator();
    }

    @NonNull
    @Override
    protected DataComment randomDatum() {
        return new DatumComment(RandomReviewId.nextReviewId(), RandomString.nextWord(), getRAND().nextBoolean());
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
