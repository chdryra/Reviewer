package test.Plugins.DataAggregationPlugin;

import com.chdryra.android.reviewer.Algorithms.DataAggregation.Implementation.DifferenceFloat;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.PlugIns.DataAggregatorsPlugin.DataAggregationDefault
        .Implementation.ComparitorLocationDistance;
import com.chdryra.android.testutils.RandomLatLng;
import com.chdryra.android.testutils.RandomString;
import com.google.android.gms.maps.model.LatLng;

import org.junit.Before;
import org.junit.Test;

import test.TestUtils.RandomReviewId;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 06/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorLocationDistanceTest {
    private ComparitorLocationDistance mComparitor;

    @Before
    public void setUp() {
        mComparitor = new ComparitorLocationDistance();
    }

    @Test
    public void zeroDifferenceForSameLatLng() {
        String nameLhs = RandomString.nextWord();
        String nameRhs = RandomString.nextWord();
        LatLng latLng = RandomLatLng.nextLatLng();
        DataLocation lhs = new DatumLocation(RandomReviewId.nextReviewId(), latLng, nameLhs);
        DataLocation rhs = new DatumLocation(RandomReviewId.nextReviewId(), latLng, nameRhs);

        DifferenceFloat expected = new DifferenceFloat(0f);
        DifferenceFloat calculated = mComparitor.compare(lhs, rhs);
        assertThat(calculated.lessThanOrEqualTo(expected), is(true));
    }

    @Test
    public void someDifferenceIfSameNamesButDifferentLatLngs() {
        String name = RandomString.nextWord();
        LatLng latLngLhs = RandomLatLng.nextLatLng();
        LatLng latLngRhs = RandomLatLng.nextLatLng();
        DataLocation lhs =
                new DatumLocation(RandomReviewId.nextReviewId(), latLngLhs, name);
        DataLocation rhs =
                new DatumLocation(RandomReviewId.nextReviewId(), latLngRhs, name);

        DifferenceFloat zeroDifference = new DifferenceFloat(0f);
        DifferenceFloat calculated = mComparitor.compare(lhs, rhs);
        assertThat(calculated.lessThanOrEqualTo(zeroDifference), is(false));
    }

    @Test
    public void someDifferenceIfDifferentLocations() {
        String nameLhs = RandomString.nextWord();
        String nameRhs = RandomString.nextWord();
        LatLng latLngLhs = RandomLatLng.nextLatLng();
        LatLng latLngRhs = RandomLatLng.nextLatLng();
        DataLocation lhs =
                new DatumLocation(RandomReviewId.nextReviewId(), latLngLhs, nameLhs);
        DataLocation rhs =
                new DatumLocation(RandomReviewId.nextReviewId(), latLngRhs, nameRhs);

        DifferenceFloat zeroDifference = new DifferenceFloat(0f);
        DifferenceFloat calculated = mComparitor.compare(lhs, rhs);
        assertThat(calculated.lessThanOrEqualTo(zeroDifference), is(false));
    }
}
