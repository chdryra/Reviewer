package com.chdryra.android.reviewer.test.View.GvDataModel;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.GvDataModel.GvCanonical;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.ParcelableTester;
import com.chdryra.android.reviewer.test.TestUtils.RandomReviewId;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 03/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvCanonicalTest extends TestCase {

    @SmallTest
    public void testParcelable() {
        GvCommentList.GvComment comment = GvDataMocker.newComment(null);
        GvCommentList.GvComment comment2 = GvDataMocker.newComment(RandomReviewId.nextGvReviewId());
        GvCommentList similar = GvDataMocker.newCommentList(10, false);
        GvCommentList similar2 = GvDataMocker.newCommentList(10, true);

        GvCanonical<GvCommentList.GvComment> canonical1 = new GvCanonical<>(comment, similar);
        GvCanonical<GvCommentList.GvComment> canonical2 = new GvCanonical<>(comment2, similar);
        GvCanonical<GvCommentList.GvComment> canonical3 = new GvCanonical<>(comment, similar2);
        GvCanonical<GvCommentList.GvComment> canonical4 = new GvCanonical<>(comment2, similar2);
        ParcelableTester.testParcelable(canonical1);
        ParcelableTester.testParcelable(canonical2);
        ParcelableTester.testParcelable(canonical3);
        ParcelableTester.testParcelable(canonical4);
    }
}
