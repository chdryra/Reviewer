/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 4 February, 2015
 */

package com.chdryra.android.reviewer.test;

import android.app.Fragment;
import android.app.FragmentManager;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.mygenerallibrary.DialogAlertFragment;
import com.chdryra.android.reviewer.GvCommentList;
import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;

/**
 * Created by: Rizwan Choudrey
 * On: 04/02/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivityEditCommentsTest extends ActivityEditScreenTest {
    private static final int SPLIT = R.id.menu_item_split_comment;

    public ActivityEditCommentsTest() {
        super(GvCommentList.TYPE);
    }

    @SmallTest
    public void testMenuSplitUnsplitComments() {
        setUp(true);
        GvCommentList comments = (GvCommentList) mAdapter.getGridData();
        GvCommentList split = comments.getSplitComments();

        assertTrue(comments.size() > 0);
        assertTrue(split.size() > comments.size());

        assertEquals(comments.size(), getGridSize());
        checkInGrid(comments, true);
        checkInGrid(split, false);
        checkInBuilder(comments, true);

        mSolo.clickOnActionBarItem(SPLIT);

        assertEquals(split.size(), getGridSize());
        checkInGrid(comments, false);
        checkInGrid(split, true);
        checkInBuilder(comments, true);

        mSolo.clickOnActionBarItem(SPLIT);

        assertEquals(comments.size(), getGridSize());
        checkInGrid(comments, true);
        checkInGrid(split, false);
        checkInBuilder(comments, true);

        mSolo.clickOnActionBarItem(SPLIT);

        assertEquals(split.size(), getGridSize());
        checkInGrid(comments, false);
        checkInGrid(split, true);
        checkInBuilder(comments, true);

        mSolo.clickOnActionBarItem(DONE);
        checkInParentBuilder(comments, true);
    }


    @SmallTest
    public void testSetAsHeadline() {
        setUp(true);
        GvCommentList comments = (GvCommentList) mData;
        for (GvCommentList.GvComment comment : comments) {
            comment.setIsHeadline(false);
        }

        comments.getItem(0).setIsHeadline(true);
        GvCommentList.GvComment oldHeadline = (GvCommentList.GvComment) getGridItem(0);
        assertNotNull(oldHeadline);
        assertTrue(oldHeadline.isHeadline());
        for (int i = 0; i < comments.size(); ++i) {
            if (i == 0) {
                assertTrue(comments.getItem(i).isHeadline());
                assertEquals(comments.getItem(i), oldHeadline);
            } else {
                assertFalse(comments.getItem(i).isHeadline());
            }
        }

        String alert = getInstrumentation().getTargetContext().getResources().getString(R.string
                .dialog_set_comment_as_headline);

        assertFalse(mSolo.searchText(alert));
        clickLongOnGridItem(1);
        mSolo.waitForDialogToOpen(TIMEOUT);
        assertTrue(mSolo.searchText(alert));

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mSignaler.reset();
                getAlertDialog().clickPositiveButton();
                mSignaler.signal();
            }
        });

        mSignaler.waitForSignal();
        mSolo.waitForDialogToClose(TIMEOUT);
        assertFalse(mSolo.searchText(alert));

        GvCommentList.GvComment newHeadline = (GvCommentList.GvComment) getGridItem(0);
        assertNotNull(newHeadline);
        assertTrue(newHeadline.isHeadline());
        assertFalse(oldHeadline.isHeadline());
        assertFalse(oldHeadline.equals(newHeadline));
        for (int i = 0; i < comments.size(); ++i) {
            if (i == 1) {
                assertTrue(comments.getItem(i).isHeadline());
                assertEquals(comments.getItem(i), newHeadline);
            } else {
                assertFalse(comments.getItem(i).isHeadline());
            }
        }


        clickOnGridItem(0);
        waitForLaunchableToLaunch();
        clickEditDelete();
        mSolo.waitForDialogToOpen(TIMEOUT);
        clickDeleteConfirm();
        waitForLaunchableToClose();

        GvCommentList.GvComment newnewHeadline = (GvCommentList.GvComment) getGridItem(0);
        assertNotNull(newnewHeadline);
        assertTrue(newnewHeadline.isHeadline());
        assertFalse(newHeadline.isHeadline());
        assertFalse(newHeadline.equals(newnewHeadline));
    }

    @Override
    protected GvDataList.GvData newEditDatum(GvDataList.GvData oldDatum) {
        GvCommentList.GvComment newComment = (GvCommentList.GvComment) GvDataMocker.getDatum
                (mDataType);
        newComment.setIsHeadline(((GvCommentList.GvComment) oldDatum).isHeadline());

        return newComment;
    }

    @Override
    protected GvDataList newData() {
        GvCommentList comments = (GvCommentList) super.newData();
        for (GvCommentList.GvComment comment : comments) {
            comment.setIsHeadline(false);
        }

        comments.getItem(0).setIsHeadline(true);

        return comments;
    }

    private DialogAlertFragment getAlertDialog() {
        FragmentManager manager = getEditActivity().getFragmentManager();
        Fragment f = manager.findFragmentByTag(DialogAlertFragment.ALERT_TAG);
        return (DialogAlertFragment) f;
    }
}
