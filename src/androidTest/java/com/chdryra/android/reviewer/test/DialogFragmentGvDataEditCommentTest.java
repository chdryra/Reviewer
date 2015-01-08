/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 January, 2015
 */

package com.chdryra.android.reviewer.test;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.ConfigGvDataAddEditDisplay;
import com.chdryra.android.reviewer.GvCommentList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;

/**
 * Created by: Rizwan Choudrey
 * On: 08/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DialogFragmentGvDataEditCommentTest extends
        DialogFragmentGvDataEditTest<GvCommentList.GvComment> {

    public DialogFragmentGvDataEditCommentTest() {
        super(ConfigGvDataAddEditDisplay.EditComment.class);
    }

    @Override
    protected GvCommentList.GvComment editData() {
        GvCommentList.GvComment comment = GvDataMocker.newComment();
        mSolo.clearEditText(mSolo.getEditText(0));
        mSolo.enterText(mSolo.getEditText(0), comment.getComment());

        return comment;
    }

    @Override
    protected GvCommentList.GvComment getDataShown() {
        return new GvCommentList.GvComment(mSolo.getEditText(0).getText().toString());
    }

    @SmallTest
    public void testCancelButton() {
        super.testCancelButton();
    }

    @SmallTest
    public void testDoneButton() {
        super.testDoneButton();
    }

    @SmallTest
    public void testDeleteButtonNoEditCancel() {
        super.testDeleteButtonNoEditCancel();
    }

    @SmallTest
    public void testDeleteButtonNoEditConfirm() {
        super.testDeleteButtonNoEditConfirm();
    }

    @SmallTest
    public void testDeleteButtonWithEditCancel() {
        super.testDeleteButtonWithEditCancel();
    }

    @SmallTest
    public void testDeleteButtonWithEditConfirm() {
        super.testDeleteButtonWithEditConfirm();
    }
}

