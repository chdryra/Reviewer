/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 January, 2015
 */

package com.chdryra.android.reviewer.test;

import android.test.suitebuilder.annotation.SmallTest;
import android.widget.EditText;
import android.widget.RatingBar;

import com.chdryra.android.reviewer.ConfigGvDataAddEdit;
import com.chdryra.android.reviewer.GvChildrenList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;

/**
 * Created by: Rizwan Choudrey
 * On: 08/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DialogFragmentGvDataEditChildTest extends
        DialogFragmentGvDataEditTest<GvChildrenList.GvChildReview> {

    public DialogFragmentGvDataEditChildTest() {
        super(ConfigGvDataAddEdit.EditChild.class);
    }

    @Override
    protected GvChildrenList.GvChildReview editData() {
        GvChildrenList.GvChildReview child = GvDataMocker.newChild();
        mSolo.clearEditText(mSolo.getEditText(0));
        mSolo.enterText(mSolo.getEditText(0), child.getSubject());
        mSolo.setProgressBar(0, (int) (child.getRating() * 2f));

        return child;
    }

    @Override
    protected GvChildrenList.GvChildReview getDataShown() {
        EditText et = mSolo.getEditText(0);
        RatingBar rb = (RatingBar) mSolo.getView(com.chdryra.android.reviewer.R.id
                .child_rating_bar);
        return new GvChildrenList.GvChildReview(et.getText().toString(), rb.getRating());
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

