package com.chdryra.android.reviewer.test.View.GvDataModel;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.GvDataModel.GvAuthorList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataMap;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewId;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewOverviewList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.GvDataParcelableTester;
import com.chdryra.android.reviewer.test.TestUtils.RandomReviewId;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 14/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvDataMapTest extends TestCase {
    private static final int NUM = 10;

    private GvReviewId mId;
    private GvDataMap<GvAuthorList.GvAuthor, GvReviewOverviewList> mMap;


    @Override
    protected void setUp() throws Exception {
        mId = RandomReviewId.nextGvReviewId();
        newMap();
    }

    @SmallTest
    public void testParcelable() {
        GvReviewId id = RandomReviewId.nextGvReviewId();
        newMap();
        mMap.put(GvDataMocker.newAuthor(id), newData(id));
        GvDataParcelableTester.testParcelable(mMap);
    }

    private void newMap() {
        mMap = new GvDataMap<>(GvAuthorList.GvAuthor.TYPE, GvReviewOverviewList.TYPE, mId);
    }

    @SmallTest
    public void testGetGvDataType() {
        assertNotNull(mMap.getGvDataType());
        assertEquals(GvAuthorList.GvAuthor.TYPE, mMap.getGvDataType().getElementType());
    }

    @SmallTest
    public void testRemoveAll() {
        addData();
        addData();
        assertTrue(mMap.size() == 2);
        mMap.removeAll();
        assertEquals(0, mMap.size());
    }

    @SmallTest
    public void testGetStringSummary() {
        addData();
        addData();
        GvDataType keyType = GvAuthorList.GvAuthor.TYPE;
        GvDataType valueType = GvReviewOverviewList.TYPE;
        String expected = String.valueOf(2) + " " + keyType.getDatumName() + ":" + valueType
                .getDatumName() + " pairs";
        assertEquals(expected, mMap.getStringSummary());
    }

    @SmallTest
    public void testGetReviewId() {
        assertEquals(mId, mMap.getReviewId());
    }

    @SmallTest
    public void testHasElements() {
        assertFalse(mMap.hasElements());
        addData();
        assertTrue(mMap.hasElements());
    }

    @SmallTest
    public void testIsCollection() {
        assertTrue(mMap.isCollection());
    }

    @SmallTest
    public void testSize() {
        for(int i = 0; i < 100; ++i) {
            assertEquals(i, mMap.size());
            addData();
        }
    }

    @SmallTest
    public void testGetItem() {
        GvAuthorList.GvAuthor key1 = GvDataMocker.newAuthor(null);
        GvAuthorList.GvAuthor key2 = GvDataMocker.newAuthor(null);
        GvAuthorList.GvAuthor key3 = GvDataMocker.newAuthor(null);
        GvReviewOverviewList value1 = GvDataMocker.newReviewList(NUM, false);
        GvReviewOverviewList value2 = GvDataMocker.newReviewList(NUM, false);
        GvReviewOverviewList value3 = GvDataMocker.newReviewList(NUM, false);

        assertNull(mMap.getItem(0));
        mMap.put(key1, value1);
        mMap.put(key2, value2);
        mMap.put(key3, value3);
        assertEquals(key1, mMap.getItem(0));
        assertEquals(key2, mMap.getItem(1));
        assertEquals(key3, mMap.getItem(2));
    }

    @SmallTest
    public void testGetViewHolder() {
        assertNotNull(mMap.getViewHolder());
    }

    @SmallTest
    public void testIsValidForDisplay() {
        assertFalse(mMap.isValidForDisplay());
        addData();
        assertTrue(mMap.isValidForDisplay());
    }

    private void addData() {
        GvAuthorList.GvAuthor author = GvDataMocker.newAuthor(null);
        GvReviewOverviewList reviews = GvDataMocker.newReviewList(NUM, false);
        mMap.put(author, reviews);
    }

    private GvReviewOverviewList newData(GvReviewId id) {
        GvReviewOverviewList list = new GvReviewOverviewList(id);
        for (int i = 0; i < NUM; ++i) {
            list.add(GvDataMocker.newReviewOverview(id));
        }

        return list;
    }
}
