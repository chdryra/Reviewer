package com.chdryra.android.reviewer.test.View.GvDataAggregation;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.GvDataAggregation.CanonicalCommentMode;
import com.chdryra.android.reviewer.View.GvDataAggregation.ComparitorGvComment;
import com.chdryra.android.reviewer.View.GvDataAggregation.DifferencePercentage;
import com.chdryra.android.reviewer.View.GvDataAggregation.GvDataAggregater;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataMap;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;

import junit.framework.TestCase;

import java.util.Map;
import java.util.Set;

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

        GvDataAggregater<GvCommentList.GvComment> aggregater = new GvDataAggregater<>(data);
        GvDataMap<GvCommentList.GvComment, GvDataList<GvCommentList.GvComment>> results;
        results = aggregater.aggregate(comparitor, minDiff, canonical);

        assertTrue(results.size() > 0);
        int total = 0;
        Set<Map.Entry<GvCommentList.GvComment, GvDataList<GvCommentList.GvComment>>> entrySet;
        entrySet = results.entrySet();
        for (Map.Entry<GvCommentList.GvComment, GvDataList<GvCommentList.GvComment>> entry :
                entrySet) {
            GvCommentList.GvComment key = entry.getKey();
            GvCommentList values = (GvCommentList) entry.getValue();
            assertEquals(canonical.getCanonical(values), key);
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
