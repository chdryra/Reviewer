/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 5 January, 2015
 */

package com.chdryra.android.reviewer.test;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.ConfigGvDataAddEdit;
import com.chdryra.android.reviewer.GvCommentList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;

/**
 * Created by: Rizwan Choudrey
 * On: 05/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DialogFragmentGvDataAddCommentTest extends
        DialogFragmentGvDataAddTest<GvCommentList.GvComment> {

    public DialogFragmentGvDataAddCommentTest() {
        super(ConfigGvDataAddEdit.AddComment.class);
    }

    @Override
    protected GvCommentList.GvComment enterData() {
        GvCommentList.GvComment comment = GvDataMocker.newComment();

        mSolo.enterText(mSolo.getEditText(0), comment.getComment());
        assertEquals(mSolo.getEditText(0).getText().toString(), comment.getComment());

        return comment;
    }

    @Override
    protected boolean isDataEntered() {
        return mSolo.getEditText(0).getText().toString().length() > 0;
    }

    @Override
    protected boolean isDataNulled() {
        return !isDataEntered();
    }

    @SmallTest
    public void testCancelButton() {
        super.testCancelButton();
    }

    @SmallTest
    public void testAddButtonNotQuickSet() {
        super.testAddButtonNotQuickSet();
    }

    @SmallTest
    public void testDoneButtonNotQuickSet() {
        super.testDoneButtonNotQuickSet();
    }

    @SmallTest
    public void testQuickSet() {
        super.testQuickSet();
    }
}

