/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.DataAggregatorsPlugin;

import com.chdryra.android.mygenerallibrary.Aggregation.DifferencePercentage;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumLocation;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault.Implementation.ComparatorLevenshteinDistance;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault.Implementation.ComparatorLocationName;
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
 * On: 05/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorLocationNameTest {
    private ComparatorLocationName mComparitor;

    @Before
    public void setUp() {
        mComparitor = new ComparatorLocationName(new ComparatorLevenshteinDistance());
    }

    @Test
    public void zeroDifferenceForSameLocation() {
        String name = RandomString.nextWord();
        LatLng latLng = RandomLatLng.nextLatLng();
        DataLocation lhs = new DatumLocation(RandomReviewId.nextReviewId(), latLng, name);
        DataLocation rhs = new DatumLocation(RandomReviewId.nextReviewId(), latLng, name);

        DifferencePercentage expected = new DifferencePercentage(0.0);
        DifferencePercentage calculated = mComparitor.compare(lhs, rhs);
        assertThat(calculated.lessThanOrEqualTo(expected), is(true));
    }

    @Test
    public void zeroDifferenceRegardlessOfCaseForSameNameDifferentLatLngs() {
        String name = RandomString.nextWord();
        LatLng latLngLhs = RandomLatLng.nextLatLng();
        LatLng latLngRhs = RandomLatLng.nextLatLng();
        DataLocation lhs =
                new DatumLocation(RandomReviewId.nextReviewId(), latLngLhs, name.toUpperCase());
        DataLocation rhs =
                new DatumLocation(RandomReviewId.nextReviewId(), latLngRhs, name.toLowerCase());

        DifferencePercentage expected = new DifferencePercentage(0.0);
        DifferencePercentage calculated = mComparitor.compare(lhs, rhs);
        assertThat(calculated.lessThanOrEqualTo(expected), is(true));
    }

    @Test
    public void someDifferenceIfDifferentNamesButSameLatLngs() {
        String nameLhs = RandomString.nextWord();
        String nameRhs = RandomString.nextWord();
        LatLng latLng = RandomLatLng.nextLatLng();
        DataLocation lhs =
                new DatumLocation(RandomReviewId.nextReviewId(), latLng, nameLhs);
        DataLocation rhs =
                new DatumLocation(RandomReviewId.nextReviewId(), latLng, nameRhs);

        DifferencePercentage zeroDifference = new DifferencePercentage(0.0);
        DifferencePercentage calculated = mComparitor.compare(lhs, rhs);
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

        DifferencePercentage zeroDifference = new DifferencePercentage(0.0);
        DifferencePercentage calculated = mComparitor.compare(lhs, rhs);
        assertThat(calculated.lessThanOrEqualTo(zeroDifference), is(false));
    }
}
