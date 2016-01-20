package test.DataAlgorithms.DataAggregation;

import com.chdryra.android.reviewer.PlugIns.DataAggregationPlugin.DataAggregationPluginDefault.DataAggregationPluginDefault.FactoryDataAggregatorDefault.Implementation.CanonicalCriterionMode;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;

import test.TestUtils.RandomRating;
import test.TestUtils.RandomReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 06/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalCriterionModeTest extends CanonicalCriterionAverageTest{
    private float mModeRating;

    public CanonicalCriterionModeTest() {
        super(new CanonicalCriterionMode());
        mModeRating = RandomRating.nextRating();
    }

    @Override
    protected DataCriterion newDatum(String string) {
        //To ensure it's the most common rating...
        String modeString = getModeString();
        if(string.equals(modeString)) {
            return new DatumCriterion(RandomReviewId.nextReviewId(), modeString, mModeRating);
        } else {
            return super.newDatum(string);
        }
    }

    @Override
    protected float getExpectedRating() {
        return mModeRating;
    }
}
