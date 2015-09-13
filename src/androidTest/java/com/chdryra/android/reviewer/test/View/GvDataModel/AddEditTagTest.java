/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 December, 2014
 */

package com.chdryra.android.reviewer.test.View.GvDataModel;

import android.widget.EditText;

import com.chdryra.android.reviewer.View.Configs.ConfigGvDataAddEditView;
import com.chdryra.android.reviewer.View.Dialogs.AddEditTag;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;

/**
 * Created by: Rizwan Choudrey
 * On: 23/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class AddEditTagTest extends AddEditLayoutTest<GvTagList.GvTag> {
    public AddEditTagTest() {
        super(GvTagList.GvTag.TYPE, new AddEditTag(new ConfigGvDataAddEditView.AddTag()));
    }

    @Override
    protected void enterData(GvTagList.GvTag comment) {
        mEditText.setText(comment.get());
    }

    @Override
    protected void checkViewAndDataEquivalence(GvTagList.GvTag datum, boolean result) {
        assertEquals(result, mEditText.getText().toString().trim().equals(datum.get()));
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mEditText = (EditText) getView(AddEditTag.TAG);
        assertNotNull(mEditText);
    }
}
