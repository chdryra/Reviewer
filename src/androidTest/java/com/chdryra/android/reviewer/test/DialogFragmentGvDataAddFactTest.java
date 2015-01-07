/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 7 January, 2015
 */

package com.chdryra.android.reviewer.test;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.ConfigGvDataAddEditDisplay;
import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.GvFactList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;

/**
 * Created by: Rizwan Choudrey
 * On: 07/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DialogFragmentGvDataAddFactTest extends
        DialogFragmentGvDataAddTest<GvFactList.GvFact> {

    public DialogFragmentGvDataAddFactTest() {
        super(ConfigGvDataAddEditDisplay.AddFact.class);
    }

    @Override
    protected GvDataList.GvData enterData() {
        GvFactList.GvFact fact = GvDataMocker.newFact();

        mSolo.enterText(mSolo.getEditText(0), fact.getLabel());
        mSolo.enterText(mSolo.getEditText(1), fact.getValue());

        assertEquals(mSolo.getEditText(0).getText().toString(), fact.getLabel());
        assertEquals(mSolo.getEditText(1).getText().toString(), fact.getValue());

        return fact;
    }

    @Override
    protected boolean isDataEntered() {
        return mSolo.getEditText(0).getText().toString().length() > 0 && mSolo.getEditText(1)
                .getText().toString().length() > 0;
    }

    @Override
    protected boolean isDataNulled() {
        return mSolo.getEditText(0).getText().toString().length() == 0 && mSolo.getEditText(1)
                .getText().toString().length() == 0;
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
