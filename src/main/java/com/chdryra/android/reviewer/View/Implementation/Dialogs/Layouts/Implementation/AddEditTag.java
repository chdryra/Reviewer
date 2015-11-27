/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 18 December, 2014
 */

package com.chdryra.android.reviewer.View.Implementation.Dialogs.Layouts.Implementation;

import android.widget.EditText;

import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvTag;

/**
 * Created by: Rizwan Choudrey
 * On: 18/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class AddEditTag extends AddEditLayoutBasic<GvTag> {
    public static final int LAYOUT = R.layout.dialog_tag_add_edit;
    public static final int TAG = R.id.tag_edit_text;
    public static final int[] VIEWS = new int[]{TAG};

    //Constructors
    public AddEditTag(GvDataAdder adder) {
        super(GvTag.class, LAYOUT, VIEWS, TAG, adder);
    }

    public AddEditTag(GvDataEditor editor) {
        super(GvTag.class, LAYOUT, VIEWS, TAG, editor);
    }

    //Overridden
    @Override
    public GvTag createGvData() {
        return new GvTag(((EditText) getView(TAG)).getText().toString().trim());
    }

    @Override
    public void updateLayout(GvTag tag) {
        ((EditText) getView(TAG)).setText(tag.getString());
    }
}