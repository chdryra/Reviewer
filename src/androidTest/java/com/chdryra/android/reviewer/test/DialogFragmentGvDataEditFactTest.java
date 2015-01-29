/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 January, 2015
 */

package com.chdryra.android.reviewer.test;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.ConfigGvDataAddEdit;
import com.chdryra.android.reviewer.GvFactList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;

/**
 * Created by: Rizwan Choudrey
 * On: 08/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DialogFragmentGvDataEditFactTest extends DialogFragmentGvDataEditTest<GvFactList
        .GvFact> {

    public DialogFragmentGvDataEditFactTest() {
        super(ConfigGvDataAddEdit.EditFact.class);
    }

    @Override
    protected GvFactList.GvFact editData() {
        GvFactList.GvFact fact = GvDataMocker.newFact();
        mSolo.clearEditText(mSolo.getEditText(0));
        mSolo.clearEditText(mSolo.getEditText(1));
        mSolo.enterText(mSolo.getEditText(0), fact.getLabel());
        mSolo.enterText(mSolo.getEditText(1), fact.getValue());

        return fact;
    }

    @Override
    protected GvFactList.GvFact getDataShown() {
        return new GvFactList.GvFact(mSolo.getEditText(0).getText().toString(),
                mSolo.getEditText(1).getText().toString());
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

