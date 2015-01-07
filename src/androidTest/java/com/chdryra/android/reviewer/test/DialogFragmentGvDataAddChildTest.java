/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 7 January, 2015
 */

package com.chdryra.android.reviewer.test;

import android.test.suitebuilder.annotation.SmallTest;
import android.widget.EditText;
import android.widget.RatingBar;

import com.chdryra.android.reviewer.ConfigGvDataAddEditDisplay;
import com.chdryra.android.reviewer.GvChildrenList;
import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;

/**
 * Created by: Rizwan Choudrey
 * On: 07/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DialogFragmentGvDataAddChildTest extends
        DialogFragmentGvDataAddTest<GvChildrenList.GvChildReview> {

    public DialogFragmentGvDataAddChildTest() {
        super(ConfigGvDataAddEditDisplay.AddChild.class);
    }

    @Override
    protected GvDataList.GvData enterData() {
        GvChildrenList.GvChildReview child = GvDataMocker.newChild();

        mSolo.enterText(mSolo.getEditText(0), child.getSubject());
        mSolo.setProgressBar(0, (int) (child.getRating() * 2f));

        return child;
    }

    @Override
    protected boolean isDataEntered() {
        return mSolo.getEditText(0).getText().toString().length() > 0;
    }

    @Override
    protected boolean isDataNulled() {
        EditText et = mSolo.getEditText(0);
        RatingBar rb = (RatingBar) mSolo.getView(com.chdryra.android.reviewer.R.id
                .child_rating_bar);

        return et.getText().toString().length() == 0 && rb.getRating() == 0;
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
