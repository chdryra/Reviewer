/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 December, 2014
 */

package com.chdryra.android.reviewer.test;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.View;
import android.widget.EditText;

import com.chdryra.android.reviewer.ActivityFeed;
import com.chdryra.android.reviewer.ConfigGvDataAddEditDisplay;
import com.chdryra.android.reviewer.GvCommentList;
import com.chdryra.android.reviewer.LayoutComment;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.testutils.RandomStringGenerator;

/**
 * Created by: Rizwan Choudrey
 * On: 23/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class LayoutCommentTest extends ActivityInstrumentationTestCase2<ActivityFeed> {
    private LayoutComment mLayout;

    public LayoutCommentTest() {
        super(ActivityFeed.class);
    }

    @Override
    public void setUp() throws Exception {
        mLayout = new LayoutComment(new ConfigGvDataAddEditDisplay.AddComment());
    }

    @SmallTest
    public void testGetDialogTitleOnAdd() {
        GvCommentList.GvComment comment = GvDataMocker.newComment();

        String title = mLayout.getTitleOnAdd(comment);
        assertNotNull(title);
        assertTrue(title.contains(comment.getCommentHeadline()));
    }

    @SmallTest
    public void testGetDeleteConfirmDialogTitle() {
        GvCommentList.GvComment comment = GvDataMocker.newComment();
        String deleteConfirm = mLayout.getDeleteConfirmDialogTitle(comment);
        assertNotNull(deleteConfirm);
        assertTrue(deleteConfirm.contains(comment.getCommentHeadline()));
    }

    @SmallTest
    public void testCreateGvDataFromViews() {
        View v = inflate();
        EditText commentET = (EditText) v.findViewById(LayoutComment.COMMENT);
        assertNotNull(commentET);

        String comment = RandomStringGenerator.nextSentence();

        commentET.setText(comment);

        GvCommentList.GvComment commentOut = mLayout.createGvDataFromViews();
        assertNotNull(commentOut);
        assertEquals(comment, commentOut.getComment());
    }

    @SmallTest
    public void testUpdateViews() {
        View v = inflate();
        EditText commentET = (EditText) v.findViewById(LayoutComment.COMMENT);
        assertNotNull(commentET);

        GvCommentList.GvComment commentIn = GvDataMocker.newComment();
        assertFalse(commentET.getText().toString().trim().equals(commentIn.getComment()));

        mLayout.updateViews(commentIn);

        assertTrue(commentET.getText().toString().trim().equals(commentIn.getComment()));
    }

    @SmallTest
    public void testGetViewHolder() {
        assertNotNull(mLayout.getViewHolder());
    }

    @SmallTest
    public void testGetEditTextForKeyboardAction() {
        View v = inflate();
        EditText commentET = (EditText) v.findViewById(LayoutComment.COMMENT);
        assertEquals(commentET, mLayout.getEditTextForKeyboardAction());
    }

    private View inflate() {
        mLayout.getViewHolder().inflate(getInstrumentation().getTargetContext());
        return mLayout.getViewHolder().getView();
    }
}
