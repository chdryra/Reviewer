/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.DataAggregatorsPlugin;

import android.support.annotation.NonNull;

import com.chdryra.android.corelibrary.Aggregation.ComparatorString;
import com.chdryra.android.corelibrary.Aggregation.DifferenceFloat;
import com.chdryra.android.corelibrary.Aggregation.DifferenceLocation;
import com.chdryra.android.corelibrary.Aggregation.DifferencePercentage;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin
        .DataAggregationDefault.Implementation.ComparatorLevenshteinDistance;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin
        .DataAggregationDefault.Implementation.ComparatorLocation;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin
        .DataAggregationDefault.Implementation.ComparatorLocationDistance;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin
        .DataAggregationDefault.Implementation.ComparatorLocationName;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumLocation;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataLocation;
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
public class ComparitorLocationTest {
    private ComparatorString mNameComparitor;
    private ComparatorLocationDistance mDistComparitor;
    private ComparatorLocation mComparitor;

    @Before
    public void setUp() {
        mNameComparitor = new ComparatorLevenshteinDistance();
        ComparatorLocationName nameComparitor = new ComparatorLocationName(mNameComparitor);
        mDistComparitor = new ComparatorLocationDistance();
        mComparitor = new ComparatorLocation(mDistComparitor, nameComparitor);
    }

    @Test
    public void sameLocationReturnsDifferenceLocationOfZeroDistanceAndSameName() {
        String name = RandomString.nextWord();
        LatLng latLng = RandomLatLng.nextLatLng();

        DataLocation lhs = new DatumLocation(RandomReviewId.nextReviewId(), latLng, name);
        DataLocation rhs = new DatumLocation(RandomReviewId.nextReviewId(), latLng, name);

        DifferenceLocation calculated = mComparitor.compare(lhs, rhs);
        assertThat(calculated.lessThanOrEqualTo(getZeroDifference()), is(true));
    }

    @Test
    public void differentLocationNamesSameLatLngsReturnsDifferenceLocationOfSomeDifference() {
        String nameLhs = RandomString.nextWord();
        String nameRhs = RandomString.nextWord();
        LatLng latLng = RandomLatLng.nextLatLng();

        DataLocation lhs = new DatumLocation(RandomReviewId.nextReviewId(), latLng, nameLhs);
        DataLocation rhs = new DatumLocation(RandomReviewId.nextReviewId(), latLng, nameRhs);

        DifferenceLocation calculated = mComparitor.compare(lhs, rhs);
        assertThat(calculated.lessThanOrEqualTo(getZeroDifference()), is(false));
    }

    @Test
    public void sameLocationNamesDifferentLatLngsReturnsDifferenceLocationOfSomeDifference() {
        String name = RandomString.nextWord();
        LatLng latLngLhs = RandomLatLng.nextLatLng();
        LatLng latLngRhs = RandomLatLng.nextLatLng();

        DataLocation lhs = new DatumLocation(RandomReviewId.nextReviewId(), latLngLhs, name);
        DataLocation rhs = new DatumLocation(RandomReviewId.nextReviewId(), latLngRhs, name);

        DifferenceLocation calculated = mComparitor.compare(lhs, rhs);
        assertThat(calculated.lessThanOrEqualTo(getZeroDifference()), is(false));
    }

    @Test
    public void differentLocationNamesSameLatLngsReturnsDifferenceLocationOfExpectedDifference() {
        String nameLhs = RandomString.nextWord();
        String nameRhs = RandomString.nextWord();
        LatLng latLng = RandomLatLng.nextLatLng();

        DataLocation lhs = new DatumLocation(RandomReviewId.nextReviewId(), latLng, nameLhs);
        DataLocation rhs = new DatumLocation(RandomReviewId.nextReviewId(), latLng, nameRhs);

        DifferencePercentage nameDiffExpeccted = mNameComparitor.compare(nameLhs, nameRhs);
        DifferenceFloat noDist = new DifferenceFloat(0f);
        DifferenceLocation expected = new DifferenceLocation(noDist, nameDiffExpeccted);

        DifferenceLocation tooNarrow = getAlmostSameNameDifference(nameLhs, nameDiffExpeccted,
                noDist);

        DifferenceLocation calculated = mComparitor.compare(lhs, rhs);
        assertThat(calculated.lessThanOrEqualTo(expected), is(true));
        assertThat(calculated.lessThanOrEqualTo(tooNarrow), is(false));
    }

    @Test
    public void sameLocationNamesDifferentLatLngsReturnsDifferenceLocationOfExpectedDifference() {
        String name = RandomString.nextWord();
        LatLng latLngLhs = RandomLatLng.nextLatLng();
        LatLng latLngRhs = RandomLatLng.nextLatLng();

        DataLocation lhs = new DatumLocation(RandomReviewId.nextReviewId(), latLngLhs, name);
        DataLocation rhs = new DatumLocation(RandomReviewId.nextReviewId(), latLngRhs, name);

        DifferencePercentage noDiffName = new DifferencePercentage(0.0);
        DifferenceFloat latLngDiffExpected = mDistComparitor.compare(lhs, rhs);
        DifferenceLocation expected = new DifferenceLocation(latLngDiffExpected, noDiffName);

        DifferenceLocation tooNarrow = getAlmostSameLocationDifference(name, latLngLhs, lhs,
                noDiffName, latLngDiffExpected);

        DifferenceLocation calculated = mComparitor.compare(lhs, rhs);
        assertThat(calculated.lessThanOrEqualTo(expected), is(true));
        assertThat(calculated.lessThanOrEqualTo(tooNarrow), is(false));
    }

    private DifferenceLocation getZeroDifference() {
        DifferenceFloat noDist = new DifferenceFloat(0f);
        DifferencePercentage sameName = new DifferencePercentage(0.0);
        return new DifferenceLocation(noDist, sameName);
    }

    @NonNull
    private DifferenceLocation getAlmostSameNameDifference(String nameLhs, DifferencePercentage
            nameDiffExpeccted, DifferenceFloat noDist) {
        DifferencePercentage nameDiffTooNarrow = mNameComparitor.compare(nameLhs, nameLhs + "a");
        assertThat(nameDiffTooNarrow.lessThanOrEqualTo(nameDiffExpeccted), is(true));
        return new DifferenceLocation(noDist, nameDiffTooNarrow);
    }

    @NonNull
    private DifferenceLocation getAlmostSameLocationDifference(String name, LatLng latLngLhs,
                                                               DataLocation lhs,
                                                               DifferencePercentage noDiffName,
                                                               DifferenceFloat latLngDiffExpected) {
        LatLng close = new LatLng(latLngLhs.latitude + 0.0001, latLngLhs.longitude + 0.0001);
        DataLocation closeby = new DatumLocation(RandomReviewId.nextReviewId(), close, name);
        DifferenceFloat latLngDiffTooNarrow = mDistComparitor.compare(lhs, closeby);
        assertThat(latLngDiffTooNarrow.lessThanOrEqualTo(latLngDiffExpected), is(true));
        return new DifferenceLocation(latLngDiffTooNarrow, noDiffName);
    }
}
