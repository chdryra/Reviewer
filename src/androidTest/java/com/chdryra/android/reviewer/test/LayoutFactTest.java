/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 December, 2014
 */

package com.chdryra.android.reviewer.test;

import android.widget.EditText;

import com.chdryra.android.reviewer.ConfigGvDataAddEdit;
import com.chdryra.android.reviewer.GvFactList;
import com.chdryra.android.reviewer.LayoutFact;

/**
 * Created by: Rizwan Choudrey
 * On: 23/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class LayoutFactTest extends GvDataEditLayoutTest<GvFactList.GvFact> {
    private EditText mLabel;

    public LayoutFactTest() {
        super(GvFactList.TYPE, new LayoutFact(new ConfigGvDataAddEdit.AddFact()));
    }

    @Override
    protected void enterData(GvFactList.GvFact datum) {
        mLabel.setText(datum.getLabel());
        mEditText.setText(datum.getValue());
    }

    @Override
    protected void checkViewAndDataEquivalence(GvFactList.GvFact datum, boolean result) {
        assertEquals(result, mLabel.getText().toString().trim().equals(datum.getLabel()));
        assertEquals(result, mEditText.getText().toString().trim().equals(datum.getValue()));
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mLabel = (EditText) getView(LayoutFact.LABEL);
        mEditText = (EditText) getView(LayoutFact.VALUE);
        assertNotNull(mLabel);
        assertNotNull(mEditText);
    }
}
