package com.chdryra.android.reviewer.test.Adapter.ReviewAdapterModel;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ViewerGvDataMap;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataMap;
import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;
import com.chdryra.android.testutils.RandomString;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 25/08/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewerGvDataMapTest extends TestCase {
    private static final int NUM = 4;
    private GvDataMap<GvLocationList.GvLocation, GvCommentList> mMap;
    private ViewerGvDataMap<GvLocationList.GvLocation> mViewer;


    @SmallTest
    public void testGetGridData() {
        assertEquals(mMap.getKeyList(), mViewer.getGridData());
    }

    @SmallTest
    public void testIsExpandableAndExpandItem() {
        GvLocationList keys = (GvLocationList) mMap.getKeyList();
        for (GvLocationList.GvLocation key : keys) {
            GvCommentList comments = mMap.get(key);
            if (comments.hasElements()) {
                assertTrue(mViewer.isExpandable(key));
                assertNotNull(mViewer.expandItem(key));
            } else {
                assertFalse(mViewer.isExpandable(key));
                assertNull(mViewer.expandItem(key));
            }
        }

        GvDataMap<GvLocationList.GvLocation, GvCommentList.GvComment> map;
        map = new GvDataMap<>(GvLocationList.GvLocation.TYPE, GvCommentList.GvComment.TYPE, null);

        GvLocationList.GvLocation key1 = GvDataMocker.newLocation(null);
        GvLocationList.GvLocation key2 = GvDataMocker.newLocation(null);
        GvCommentList.GvComment comment1 = new GvCommentList.GvComment(RandomString.nextSentence());
        GvCommentList.GvComment comment2 = new GvCommentList.GvComment();
        map.put(key1, comment1);
        map.put(key2, comment2);

        ReviewNode node = ReviewMocker.newReviewNode(false);
        ReviewViewAdapter<GvData> parent = FactoryReviewViewAdapter.newTreeDataAdapter(node);
        ViewerGvDataMap<GvLocationList.GvLocation> viewer = new ViewerGvDataMap<>(parent, map);
        assertTrue(viewer.isExpandable(key1));
        assertNotNull(viewer.expandItem(key1));
        assertFalse(viewer.isExpandable(key2));
        assertNull(viewer.expandItem(key2));
    }

    @Override
    protected void setUp() throws Exception {
        mMap = new GvDataMap<>(GvLocationList.GvLocation.TYPE, GvCommentList.TYPE, null);

        GvLocationList keyList = new GvLocationList();
        for (int i = 0; i < NUM - 1; ++i) {
            GvLocationList.GvLocation location = GvDataMocker.newLocation(null);
            GvCommentList comments = GvDataMocker.newCommentList(3, false);
            mMap.put(location, comments);
            keyList.add(location);
        }
        GvLocationList.GvLocation location = GvDataMocker.newLocation(null);
        GvCommentList comments = GvDataMocker.newCommentList(0, false);
        mMap.put(location, comments);
        keyList.add(location);

        ReviewNode node = ReviewMocker.newReviewNode(false);
        ReviewViewAdapter<GvData> parent = FactoryReviewViewAdapter.newTreeDataAdapter(node);
        mViewer = new ViewerGvDataMap<>(parent, mMap);
    }
}
