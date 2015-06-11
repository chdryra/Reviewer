/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 15 January, 2015
 */

package com.chdryra.android.reviewer.test.View;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.GvDataModel.GvSocialPlatformList;
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
        assertEquals(GvSocialPlatformList.TYPE, mList.getGvDataType());
    }

    @SmallTest
    public void testComparator() {
        for (int i = 0; i < 100; ++i) {
            mList.add(GvDataMocker.newSocialPlatform());
        }

        assertEquals(100, mList.size());

        mList.sort();
        GvSocialPlatformList.GvSocialPlatform prev = mList.getItem(0);
        for (int i = 1; i < mList.size(); ++i) {
            GvSocialPlatformList.GvSocialPlatform next = mList.getItem(i);
            assertTrue(prev.getFollowers() >= next.getFollowers());
            prev = next;
        }
    }

    @SmallTest
    public void testGvSocialPlatform() {
        GvSocialPlatformList.GvSocialPlatform platform1 = GvDataMocker.newSocialPlatform();
        GvSocialPlatformList.GvSocialPlatform platform2 = GvDataMocker.newSocialPlatform();

        String name1 = platform1.getName();
        int followers1 = platform1.getFollowers();
        String name2 = platform2.getName();
        int followers2 = platform2.getFollowers();

        GvSocialPlatformList.GvSocialPlatform gvPlatform = new GvSocialPlatformList
                .GvSocialPlatform(name1, followers1);
        GvSocialPlatformList.GvSocialPlatform gvPlatformEquals = new GvSocialPlatformList
                .GvSocialPlatform(name1,
                followers1);
        GvSocialPlatformList.GvSocialPlatform gvPlatformNotEquals1 = new GvSocialPlatformList
                .GvSocialPlatform
                (name1, followers2);
        GvSocialPlatformList.GvSocialPlatform gvPlatformNotEquals2 = new GvSocialPlatformList
                .GvSocialPlatform
                (name2, followers1);
        GvSocialPlatformList.GvSocialPlatform gvPlatformNotEquals3 = new GvSocialPlatformList
                .GvSocialPlatform
                (name2, followers2);
        GvSocialPlatformList.GvSocialPlatform gvPlatformNull = new GvSocialPlatformList
                .GvSocialPlatform();
        GvSocialPlatformList.GvSocialPlatform gvPlatformEmpty = new GvSocialPlatformList
                .GvSocialPlatform("", followers1);

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
        GvSocialPlatformList.GvSocialPlatform platform = GvDataMocker.newSocialPlatform();
        assertFalse(platform.isChosen());
        platform.press();
        assertTrue(platform.isChosen());
    }

    @SmallTest
    public void testGetLatest() {
        GvSocialPlatformList latest = GvSocialPlatformList.getLatest(getContext());
        assertTrue(latest.size() > 0);
        for (GvSocialPlatformList.GvSocialPlatform platform : latest) {
            assertNotNull(platform.getName());
            assertTrue(platform.getFollowers() == 0);
        }
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mList = new GvSocialPlatformList();
    }
}
