/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 December, 2014
 */

package com.chdryra.android.reviewer.test;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.View;
import android.widget.EditText;

import com.chdryra.android.reviewer.ConfigGvDataAddEdit;
import com.chdryra.android.reviewer.GvFactList;
import com.chdryra.android.reviewer.LayoutFact;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.testutils.RandomString;

/**
 * Created by: Rizwan Choudrey
 * On: 23/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class LayoutFactTest extends AndroidTestCase {
    private LayoutFact mLayout;

    @Override
    public void setUp() throws Exception {
        mLayout = new LayoutFact(new ConfigGvDataAddEdit.AddFact());
    }

    @SmallTest
    public void testGetDialogTitleOnAdd() {
        GvFactList.GvFact fact = GvDataMocker.newFact();

        String title = mLayout.getTitleOnAdd(fact);
        assertNotNull(title);
        assertTrue(title.contains(fact.getLabel()));
        assertTrue(title.contains(fact.getValue()));
    }

    @SmallTest
    public void testGetDeleteConfirmDialogTitle() {
        GvFactList.GvFact fact = GvDataMocker.newFact();
        String deleteConfirm = mLayout.getDeleteConfirmDialogTitle(fact);
        assertNotNull(deleteConfirm);
        assertTrue(deleteConfirm.contains(fact.getLabel()));
        assertTrue(deleteConfirm.contains(fact.getValue()));
    }

    @SmallTest
    public void testCreateGvDataFromViews() {
        View v = inflate();
        EditText labelET = (EditText) v.findViewById(LayoutFact.LABEL);
        EditText valueET = (EditText) v.findViewById(LayoutFact.VALUE);
        assertNotNull(labelET);
        assertNotNull(valueET);

        String label = RandomString.nextWord();
        String value = RandomString.nextWord();

        labelET.setText(label);
        valueET.setText(value);

        GvFactList.GvFact factOut = mLayout.createGvDataFromViews();
        assertNotNull(factOut);
        assertEquals(label, factOut.getLabel());
        assertEquals(value, factOut.getValue());
    }

    @SmallTest
    public void testUpdateViews() {
        View v = inflate();
        EditText labelET = (EditText) v.findViewById(LayoutFact.LABEL);
        EditText valueET = (EditText) v.findViewById(LayoutFact.VALUE);

        GvFactList.GvFact factIn = GvDataMocker.newFact();
        assertFalse(labelET.getText().toString().trim().equals(factIn.getLabel()));
        assertFalse(valueET.getText().toString().trim().equals(factIn.getValue()));

        mLayout.updateViews(factIn);

        assertTrue(labelET.getText().toString().trim().equals(factIn.getLabel()));
        assertTrue(valueET.getText().toString().trim().equals(factIn.getValue()));
    }

    @SmallTest
    public void testGetViewHolder() {
        assertNotNull(mLayout.getViewHolder());
    }

    @SmallTest
    public void testGetEditTextForKeyboardAction() {
        View v = inflate();
        EditText valueET = (EditText) v.findViewById(LayoutFact.VALUE);
        assertEquals(valueET, mLayout.getEditTextForKeyboardAction());
    }

    private View inflate() {
        mLayout.getViewHolder().inflate(getContext());
        return mLayout.getViewHolder().getView();
    }
}
