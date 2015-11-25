/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 December, 2014
 */

package com.chdryra.android.reviewer.test.View.GvDataModel;

import android.widget.EditText;

import com.chdryra.android.reviewer.View.Configs.Implementation.DefaultLaunchables;
import com.chdryra.android.reviewer.View.Implementation.Dialogs.Layouts.Implementation.AddEditComment;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvComment;

/**
 * Created by: Rizwan Choudrey
 * On: 23/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class AddEditCommentTest extends AddEditLayoutTest<GvComment> {
    //Constructors
    public AddEditCommentTest() {
        super(GvComment.TYPE,
                new AddEditComment(new DefaultLaunchables.AddComment()));
    }

    //Overridden
    @Override
    protected void enterData(GvComment comment) {
        mEditText.setText(comment.getComment());
    }

    @Override
    protected void checkViewAndDataEquivalence(GvComment datum, boolean result) {
        assertEquals(result, mEditText.getText().toString().trim().equals(datum.getComment()));
    }

    @Override
    protected GvComment newDatum() {
        GvComment data = super.newDatum();
        data.setIsHeadline(false);
        return data;
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mEditText = (EditText) getView(AddEditComment.COMMENT);
        assertNotNull(mEditText);
    }
}
