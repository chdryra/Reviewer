package test.DataAlgorithms;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Factories.FactoryDataAggregator;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.DataAggregator;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
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
    protected DataAggregator<DataSubject> newAggregator(FactoryDataAggregator factory) {
        return factory.newSubjectsAggregator();
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
