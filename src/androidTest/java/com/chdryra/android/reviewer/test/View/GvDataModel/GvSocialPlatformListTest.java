/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 15 January, 2015
 */

package com.chdryra.android.reviewer.test.View.GvDataModel;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvSocialPlatform;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvSocialPlatformList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;

/**
 * Created by: Rizwan Choudrey
 * On: 15/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvSocialPlatformListTest extends AndroidTestCase {
    private GvSocialPlatformList mList;

    @SmallTest
    public void testGetGvType() {
        assertEquals(GvSocialPlatform.TYPE, mList.getGvDataType());
    }

    @SmallTest
    public void testComparator() {
        for (int i = 0; i < 100; ++i) {
            mList.add(GvDataMocker.newSocialPlatform());
        }

        assertEquals(100, mList.size());

        mList.sort();
        GvSocialPlatform prev = mList.getItem(0);
        for (int i = 1; i < mList.size(); ++i) {
            GvSocialPlatform next = mList.getItem(i);
            assertTrue(prev.getFollowers() >= next.getFollowers());
            prev = next;
        }
    }

    @SmallTest
    public void testGvSocialPlatform() {
        GvSocialPlatform platform1 = GvDataMocker.newSocialPlatform();
        GvSocialPlatform platform2 = GvDataMocker.newSocialPlatform();

        String name1 = platform1.getName();
        int followers1 = platform1.getFollowers();
        String name2 = platform2.getName();
        int followers2 = platform2.getFollowers();

        GvSocialPlatform gvPlatform = new GvSocialPlatform(name1, followers1);
        GvSocialPlatform gvPlatformEquals = new GvSocialPlatform(name1,
                followers1);
        GvSocialPlatform gvPlatformNotEquals1 = new GvSocialPlatform
                (name1, followers2);
        GvSocialPlatform gvPlatformNotEquals2 = new GvSocialPlatform
                (name2, followers1);
        GvSocialPlatform gvPlatformNotEquals3 = new GvSocialPlatform
                (name2, followers2);
        GvSocialPlatform gvPlatformNull = new GvSocialPlatform();
        GvSocialPlatform gvPlatformEmpty = new GvSocialPlatform("", followers1);

        assertNotNull(gvPlatform.getViewHolder());
        assertTrue(gvPlatform.isValidForDisplay());

        assertEquals(name1, gvPlatform.getName());
        assertEquals(followers1, gvPlatform.getFollowers());

        assertTrue(gvPlatform.equals(gvPlatformEquals));
        assertFalse(gvPlatform.equals(gvPlatformNotEquals1));
        assertFalse(gvPlatform.equals(gvPlatformNotEquals2));
        assertFalse(gvPlatform.equals(gvPlatformNotEquals3));

        assertFalse(gvPlatformNull.isValidForDisplay());
        assertFalse(gvPlatformEmpty.isValidForDisplay());
    }

    @SmallTest
    public void testGvSocialPlatformIsChosenAndPress() {
        GvSocialPlatform platform = GvDataMocker.newSocialPlatform();
        assertFalse(platform.isChosen());
        platform.press();
        assertTrue(platform.isChosen());
    }

    @SmallTest
    public void testGetLatest() {
        GvSocialPlatformList latest = GvSocialPlatformList.getLatest(getContext());
        assertTrue(latest.size() > 0);
        for (GvSocialPlatform platform : latest) {
            assertNotNull(platform.getName());
            assertTrue(platform.getFollowers() == 0);
        }
    }

    //Overridden
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mList = new GvSocialPlatformList();
    }
}
