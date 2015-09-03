package com.chdryra.android.reviewer.test.View.GvDataAggregation;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.GvDataAggregation.CanonicalCommentMode;
import com.chdryra.android.reviewer.View.GvDataAggregation.ComparitorGvComment;
import com.chdryra.android.reviewer.View.GvDataAggregation.DifferencePercentage;
import com.chdryra.android.reviewer.View.GvDataAggregation.GvDataAggregater;
import com.chdryra.android.reviewer.View.GvDataModel.GvCanonical;
import com.chdryra.android.reviewer.View.GvDataModel.GvCanonicalList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 16/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvDataAggregaterTest extends TestCase {
    private static final int NUM = 100;

    @SmallTest
    public void testAggregate() {
        GvCommentList data = GvDataMocker.newCommentList(NUM, true);

        ComparitorGvComment comparitor = new ComparitorGvComment();
        DifferencePercentage minDiff = new DifferencePercentage(0.85);
        CanonicalCommentMode canonical = new CanonicalCommentMode();

        GvDataAggregater<GvCommentList.GvComment, DifferencePercentage, DifferencePercentage>
                aggregater = new GvDataAggregater<>(comparitor, minDiff, canonical);
        GvCanonicalList<GvCommentList.GvComment> results = aggregater.aggregate(data);

        assertTrue(results.size() > 0);
        int total = 0;
        for (GvCanonical<GvCommentList.GvComment> gvCanonical : results) {
            GvCommentList values = (GvCommentList) gvCanonical.toList();
            assertEquals(canonical.getCanonical(values), gvCanonical.getCanonical());
            int numVals = values.size();
            assertTrue(numVals > 0 && numVals < NUM);
            total += numVals;
            GvCommentList.GvComment reference = values.getItem(0);
            for (int i = 1; i < numVals; ++i) {
                DifferencePercentage diff = comparitor.compare(reference, values.getItem(i));
                assertTrue(diff.lessThanOrEqualTo(minDiff));
            }
        }
        assertEquals(data.size(), total);
    }
}
