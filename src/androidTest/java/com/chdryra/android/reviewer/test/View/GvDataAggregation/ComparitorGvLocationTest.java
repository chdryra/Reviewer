package com.chdryra.android.reviewer.test.View.GvDataAggregation;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.GvDataAggregation.ComparitorGvLocation;
import com.chdryra.android.reviewer.View.GvDataAggregation.ComparitorGvLocationDistance;
import com.chdryra.android.reviewer.View.GvDataAggregation.ComparitorGvLocationName;
import com.chdryra.android.reviewer.View.GvDataAggregation.DifferenceFloat;
import com.chdryra.android.reviewer.View.GvDataAggregation.DifferenceLocation;
import com.chdryra.android.reviewer.View.GvDataAggregation.DifferencePercentage;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvLocation;
import com.google.android.gms.maps.model.LatLng;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 16/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorGvLocationTest extends TestCase {
    private static final String TAYYABS = "Tayyabs";
    private static final String TAYYBBS = "Tayybbs";
    private static final String LKH = "Lahore Kebab House";
    private static final LatLng TAYYABS_LL1 = new LatLng(51.517972, -0.063291);
    private static final LatLng TAYYABS_LL2 = new LatLng(51.518072, -0.063291);
    private static final LatLng LKH_LL = new LatLng(51.514764, -0.063143);

    private static final GvLocation TAYYABS_LOC
            = new GvLocation(TAYYABS_LL1, TAYYABS);
    private static final GvLocation TAYYBBS_LOC
            = new GvLocation(TAYYABS_LL1, TAYYBBS);
    private static final GvLocation TAYYABS_LOC2
            = new GvLocation(TAYYABS_LL2, TAYYABS);
    private static final GvLocation TAYYBBS_LOC2
            = new GvLocation(TAYYABS_LL2, TAYYBBS);
    private static final GvLocation LKH_LOC
            = new GvLocation(LKH_LL, LKH);
    private static final DifferencePercentage ZERO_NAME = new DifferencePercentage(0.0);
    private static final DifferenceFloat ZERO_DIST = new DifferenceFloat(0f);
    private ComparitorGvLocation mLocationComparitor;
    private DifferencePercentage mTypoDiff;
    private DifferencePercentage mTayyabsLkhNameDiff;
    private DifferenceFloat mDistDiff;
    private DifferenceFloat mTayyabsLkhDistDiff;

    @SmallTest
    public void testCompareSame() {
        DifferenceLocation differenceLocation = mLocationComparitor.compare(TAYYABS_LOC,
                TAYYABS_LOC);
        DifferenceLocation expected = new DifferenceLocation(ZERO_DIST, ZERO_NAME);
        DifferenceLocation maxDiff = new DifferenceLocation(mTayyabsLkhDistDiff,
                mTayyabsLkhNameDiff);
        assertTrue(differenceLocation.lessThanOrEqualTo(expected));
        assertTrue(differenceLocation.lessThanOrEqualTo(maxDiff));
    }

    @SmallTest
    public void testCompareTypo() {
        DifferenceLocation differenceLocation = mLocationComparitor.compare(TAYYABS_LOC,
                TAYYBBS_LOC);
        DifferenceLocation zeroDiff = new DifferenceLocation(ZERO_DIST, ZERO_NAME);
        DifferenceLocation expected = new DifferenceLocation(ZERO_DIST, mTypoDiff);
        DifferenceLocation maxDiff = new DifferenceLocation(mTayyabsLkhDistDiff,
                mTayyabsLkhNameDiff);
        assertFalse(differenceLocation.lessThanOrEqualTo(zeroDiff));
        assertTrue(differenceLocation.lessThanOrEqualTo(expected));
        assertTrue(differenceLocation.lessThanOrEqualTo(maxDiff));

        differenceLocation = mLocationComparitor.compare(TAYYBBS_LOC, TAYYABS_LOC);
        assertFalse(differenceLocation.lessThanOrEqualTo(zeroDiff));
        assertTrue(differenceLocation.lessThanOrEqualTo(expected));
        assertTrue(differenceLocation.lessThanOrEqualTo(maxDiff));
    }

    @SmallTest
    public void testCompareDistanceDiff() {
        DifferenceLocation differenceLocation = mLocationComparitor.compare(TAYYABS_LOC,
                TAYYABS_LOC2);
        DifferenceLocation zeroDiff = new DifferenceLocation(ZERO_DIST, ZERO_NAME);
        DifferenceLocation expected = new DifferenceLocation(mDistDiff, ZERO_NAME);
        DifferenceLocation maxDiff = new DifferenceLocation(mTayyabsLkhDistDiff,
                mTayyabsLkhNameDiff);
        assertFalse(differenceLocation.lessThanOrEqualTo(zeroDiff));
        assertTrue(differenceLocation.lessThanOrEqualTo(expected));
        assertTrue(differenceLocation.lessThanOrEqualTo(maxDiff));

        differenceLocation = mLocationComparitor.compare(TAYYABS_LOC2, TAYYABS_LOC);
        assertFalse(differenceLocation.lessThanOrEqualTo(zeroDiff));
        assertTrue(differenceLocation.lessThanOrEqualTo(expected));
        assertTrue(differenceLocation.lessThanOrEqualTo(maxDiff));
    }

    @SmallTest
    public void testCompareTypoAndDistanceDiff() {
        DifferenceLocation differenceLocation = mLocationComparitor.compare(TAYYABS_LOC,
                TAYYBBS_LOC2);
        DifferenceLocation zeroDiff = new DifferenceLocation(ZERO_DIST, ZERO_NAME);
        DifferenceLocation expected = new DifferenceLocation(mDistDiff, mTypoDiff);
        DifferenceLocation maxDiff = new DifferenceLocation(mTayyabsLkhDistDiff,
                mTayyabsLkhNameDiff);
        assertFalse(differenceLocation.lessThanOrEqualTo(zeroDiff));
        assertTrue(differenceLocation.lessThanOrEqualTo(expected));
        assertTrue(differenceLocation.lessThanOrEqualTo(maxDiff));

        differenceLocation = mLocationComparitor.compare(TAYYBBS_LOC2, TAYYABS_LOC);
        assertFalse(differenceLocation.lessThanOrEqualTo(zeroDiff));
        assertTrue(differenceLocation.lessThanOrEqualTo(expected));
        assertTrue(differenceLocation.lessThanOrEqualTo(maxDiff));
    }

    @SmallTest
    public void testCompareTayyabsLkh() {
        DifferenceLocation differenceLocation = mLocationComparitor.compare(TAYYABS_LOC, LKH_LOC);
        DifferenceLocation zeroDiff = new DifferenceLocation(ZERO_DIST, ZERO_NAME);
        DifferenceLocation midDiff = new DifferenceLocation(mDistDiff, mTypoDiff);
        DifferenceLocation expected = new DifferenceLocation(mTayyabsLkhDistDiff,
                mTayyabsLkhNameDiff);
        assertFalse(differenceLocation.lessThanOrEqualTo(zeroDiff));
        assertFalse(differenceLocation.lessThanOrEqualTo(midDiff));
        assertTrue(differenceLocation.lessThanOrEqualTo(expected));

        differenceLocation = mLocationComparitor.compare(LKH_LOC, TAYYABS_LOC);
        assertFalse(differenceLocation.lessThanOrEqualTo(zeroDiff));
        assertFalse(differenceLocation.lessThanOrEqualTo(midDiff));
        assertTrue(differenceLocation.lessThanOrEqualTo(expected));
    }

    //Overridden
    @Override
    protected void setUp() throws Exception {
        mLocationComparitor = new ComparitorGvLocation();
        ComparitorGvLocationName nameComparitor = new ComparitorGvLocationName();
        ComparitorGvLocationDistance distanceComparitor = new ComparitorGvLocationDistance();
        mTypoDiff = nameComparitor.compare(TAYYABS_LOC, TAYYBBS_LOC);
        mDistDiff = distanceComparitor.compare(TAYYABS_LOC, TAYYABS_LOC2);
        mTayyabsLkhNameDiff = nameComparitor.compare(TAYYABS_LOC, LKH_LOC);
        mTayyabsLkhDistDiff = distanceComparitor.compare(TAYYABS_LOC, LKH_LOC);
    }
}
