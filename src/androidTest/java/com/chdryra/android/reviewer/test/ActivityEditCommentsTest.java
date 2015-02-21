/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 4 February, 2015
 */

package com.chdryra.android.reviewer.test;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.GvCommentList;
import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.R;

/**
 * Created by: Rizwan Choudrey
 * On: 04/02/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivityEditCommentsTest extends ActivityEditScreenTest {
    private static final int SPLIT = R.id.menu_item_split_comment;

    public ActivityEditCommentsTest() {
        super(GvDataList.GvType.COMMENTS);
    }

    @SmallTest
    public void testMenuSplitUnsplitComments() {
        setUp(true);
        GvCommentList comments = (GvCommentList) mAdapter.getGridData();
        GvCommentList split = comments.getSplitComments();

        assertTrue(comments.size() > 0);
        assertTrue(split.size() > comments.size());

        assertEquals(comments.size(), getGridSize());
        testInGrid(comments, true);
        testInGrid(split, false);
        testInBuilder(comments, true);

        mSolo.clickOnActionBarItem(SPLIT);

        assertEquals(split.size(), getGridSize());
        testInGrid(comments, false);
        testInGrid(split, true);
        checkAdapterDataChanges(comments);

        mSolo.clickOnActionBarItem(SPLIT);

        assertEquals(comments.size(), getGridSize());
        testInGrid(comments, true);
        testInGrid(split, false);
        checkAdapterDataChanges(comments);

        mSolo.clickOnActionBarItem(SPLIT);

        assertEquals(split.size(), getGridSize());
        testInGrid(comments, false);
        testInGrid(split, true);

        mSolo.clickOnActionBarItem(DONE);
        checkAdapterDataChanges(comments);
    }
}
