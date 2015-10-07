package com.chdryra.android.reviewer.test.View.GvDataModel;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.GvDataModel.FactoryGvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvAuthorList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCanonical;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
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
    private static final int NUM = 10;
    private GvCanonical<GvAuthorList.GvAuthor> mGvCanonical;
    private GvAuthorList.GvAuthor mCanonical;
    private GvDataList<GvAuthorList.GvAuthor> mData;

    @SmallTest
    public void testParcelable() {
        GvCommentList.GvComment comment = GvDataMocker.newComment(null);
        GvCommentList.GvComment comment2 = GvDataMocker.newComment(RandomReviewId.nextGvReviewId());
        GvCommentList similar = GvDataMocker.newCommentList(NUM, false);
        GvCommentList similar2 = GvDataMocker.newCommentList(NUM, true);

        GvCanonical<GvCommentList.GvComment> canonical1 = new GvCanonical<>(comment, similar);
        GvCanonical<GvCommentList.GvComment> canonical2 = new GvCanonical<>(comment2, similar);
        GvCanonical<GvCommentList.GvComment> canonical3 = new GvCanonical<>(comment, similar2);
        GvCanonical<GvCommentList.GvComment> canonical4 = new GvCanonical<>(comment2, similar2);
        ParcelableTester.testParcelable(canonical1);
        ParcelableTester.testParcelable(canonical2);
        ParcelableTester.testParcelable(canonical3);
        ParcelableTester.testParcelable(canonical4);
    }

    @SmallTest
    public void testEquals() {
        GvAuthorList.GvAuthor author1 = GvDataMocker.newAuthor(null);
        GvAuthorList data1 = GvDataMocker.newAuthorList(NUM, true);
        GvAuthorList.GvAuthor author2 = GvDataMocker.newAuthor(null);
        GvAuthorList data2 = GvDataMocker.newAuthorList(NUM, true);

        GvCanonical<GvAuthorList.GvAuthor> canonical1 = new GvCanonical<>(author1, data1);
        GvCanonical<GvAuthorList.GvAuthor> canonical2 = new GvCanonical<>(author2, data2);
        GvCanonical<GvAuthorList.GvAuthor> canonical1Equals = new GvCanonical<>(author1, data1);
        GvCanonical<GvAuthorList.GvAuthor> canonical2Equals = new GvCanonical<>(author2, data2);
        GvCanonical<GvAuthorList.GvAuthor> canonicalNotEquals1 = new GvCanonical<>(author1, data2);
        GvCanonical<GvAuthorList.GvAuthor> canonicalNotEquals2 = new GvCanonical<>(author2, data1);

        assertTrue(canonical1.equals(canonical1Equals));
        assertFalse(canonical1.equals(canonical2));
        assertTrue(canonical2.equals(canonical2Equals));
        assertFalse(canonical2.equals(canonical1));
        assertFalse(canonical1.equals(canonicalNotEquals1));
        assertFalse(canonical1.equals(canonicalNotEquals2));
        assertFalse(canonical2.equals(canonicalNotEquals1));
        assertFalse(canonical2.equals(canonicalNotEquals2));
    }

    @SmallTest
    public void testGetCanonical() {
        assertEquals(mCanonical, mGvCanonical.getCanonical());
    }

    @SmallTest
    public void testGetData() {
        assertEquals(mData, mGvCanonical.toList());
    }

    @SmallTest
    public void testSize() {
        assertEquals(mData.size(), mGvCanonical.size());
    }

    @SmallTest
    public void testSort() {
        GvDataList<GvAuthorList.GvAuthor> fromCanonical = mGvCanonical.toList();
        GvDataList<GvAuthorList.GvAuthor> copy = FactoryGvData.copy(fromCanonical);
        assertTrue(fromCanonical.equals(copy));
        copy.sort();
        assertFalse(fromCanonical.equals(copy));
        mGvCanonical.sort();
        assertTrue(fromCanonical.equals(copy));
    }

    @SmallTest
    public void testGetItem() {
        assertEquals(mData.size(), mGvCanonical.size());
        for (int i = 0; i < mGvCanonical.size(); ++i) {
            assertEquals(mData.getItem(i), mGvCanonical.getItem(i));
        }
    }

    @SmallTest
    public void testTList() {
        assertEquals(mData, mGvCanonical.toList());
    }

    @SmallTest
    public void testGetStringSummary() {
        assertEquals(mCanonical.getStringSummary(), mGvCanonical.getStringSummary());
    }

    @SmallTest
    public void testGetReviewId() {
        assertEquals(mCanonical.getReviewId(), mGvCanonical.getReviewId());
    }

    @SmallTest
    public void testHasElements() {
        assertTrue(mGvCanonical.hasElements());
    }

    @SmallTest
    public void testtestIsCollection() {
        assertTrue(mGvCanonical.isCollection());
    }

    @SmallTest
    public void testGetViewHolder() {
        assertNotNull(mGvCanonical.getViewHolder());
    }

    @SmallTest
    public void testIsValidForDisplay() {
        assertTrue(mGvCanonical.isValidForDisplay());
        GvAuthorList.GvAuthor author = new GvAuthorList.GvAuthor();
        GvCanonical<GvAuthorList.GvAuthor> canonical = new GvCanonical<>(author, mData);
        assertFalse(canonical.isValidForDisplay());
    }

    //Overridden
    @Override
    protected void setUp() throws Exception {
        mCanonical = GvDataMocker.newAuthor(RandomReviewId.nextGvReviewId());
        GvDataList<GvAuthorList.GvAuthor> data = null;
        do {
            data = GvDataMocker.newAuthorList(NUM, true);
            mData = FactoryGvData.copy(data);
            data.sort();
        } while (mData.equals(data));
        mGvCanonical = new GvCanonical<>(mCanonical, mData);
    }
}
