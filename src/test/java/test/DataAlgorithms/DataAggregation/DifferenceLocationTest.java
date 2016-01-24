/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.DataAlgorithms.DataAggregation;

import com.chdryra.android.reviewer.Algorithms.DataAggregation.Implementation.DifferenceFloat;
import com.chdryra.android.reviewer.Algorithms.DataAggregation.Implementation.DifferenceLocation;
import com.chdryra.android.reviewer.Algorithms.DataAggregation.Implementation.DifferencePercentage;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by: Rizwan Choudrey
 * On: 05/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class DifferenceLocationTest {
    private static final Random RAND = new Random();
    private float mCloseBy;
    private double mSimilarName;
    private DifferenceLocation mLocationThreshold;

    @Before
    public void setup() {
        mCloseBy = RAND.nextFloat() / 2f;
        mSimilarName = RAND.nextDouble()/2.;
        DifferenceFloat locationDiff = new DifferenceFloat(mCloseBy);
        DifferencePercentage nameDiff = new DifferencePercentage(mSimilarName);
        mLocationThreshold = new DifferenceLocation(locationDiff, nameDiff);
    }

    @Test
    public void greaterDistanceDissimilarNameReturnsFalse() {
        DifferenceLocation veryDifferent = new DifferenceLocation(getDistant(), getDifferentName());
        assertThat(veryDifferent.lessThanOrEqualTo(mLocationThreshold), is(false));
    }

    @Test
    public void greaterDistanceSimilarNameReturnsFalse() {
        DifferenceLocation differentLoc = new DifferenceLocation(getDistant(), getSimilarName());
        assertThat(differentLoc.lessThanOrEqualTo(mLocationThreshold), is(false));
    }

    @Test
    public void closeDistanceDissimilarNameReturnsFalse() {
        DifferenceLocation differentName = new DifferenceLocation(getClose(), getDifferentName());
        assertThat(differentName.lessThanOrEqualTo(mLocationThreshold), is(false));
    }

    @Test
    public void closeDistanceSimilarNameReturnsTrue() {
        DifferenceLocation similar = new DifferenceLocation(getClose(), getSimilarName());
        assertThat(similar.lessThanOrEqualTo(mLocationThreshold), is(true));
    }

    @Test
    public void sameDistanceSameNameDifferenceReturnsTrue() {
        DifferenceFloat locationDiff = new DifferenceFloat(mCloseBy);
        DifferencePercentage nameDiff = new DifferencePercentage(mSimilarName);
        DifferenceLocation similar = new DifferenceLocation(locationDiff, nameDiff);
        assertThat(similar.lessThanOrEqualTo(mLocationThreshold), is(true));
    }

    private DifferencePercentage getDifferentName() {
        return new DifferencePercentage(mSimilarName * 2.);
    }

    private DifferencePercentage getSimilarName() {
        return new DifferencePercentage(mSimilarName / 2.);
    }

    private DifferenceFloat getDistant() {
        return new DifferenceFloat(mCloseBy * 2f);
    }

    private DifferenceFloat getClose() {
        return new DifferenceFloat(mCloseBy / 2f);
    }
}
