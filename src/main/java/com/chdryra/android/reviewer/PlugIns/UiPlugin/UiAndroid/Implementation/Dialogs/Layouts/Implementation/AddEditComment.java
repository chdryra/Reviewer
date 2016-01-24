/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Implementation;

import android.widget.EditText;

import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Interfaces.GvDataAdder;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Interfaces.GvDataEditor;

/**
 * Created by: Rizwan Choudrey
 * On: 17/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class AddEditComment extends AddEditLayoutBasic<GvComment> {
    public static final int LAYOUT = R.layout.dialog_comment_add_edit;
    public static final int COMMENT = R.id.comment_edit_text;
    public static final int[] VIEWS = new int[]{COMMENT};

    private GvComment mCurrent;

    //Constructors
    public AddEditComment(GvDataAdder adder) {
        super(GvComment.class, LAYOUT, VIEWS, COMMENT, adder);
    }

    public AddEditComment(GvDataEditor editor) {
        super(GvComment.class, LAYOUT, VIEWS, COMMENT, editor);
    }

    //Overridden
    @Override
    public GvComment createGvDataFromInputs() {
        EditText commentET = (EditText) getView(COMMENT);
        boolean isHeadline = mCurrent != null && mCurrent.isHeadline();
        mCurrent = new GvComment(commentET.getText().toString().trim(), isHeadline);

        return mCurrent;
    }

    @Override
    public void updateLayout(GvComment comment) {
        ((EditText) getView(COMMENT)).setText(comment.getComment());
        mCurrent = comment;
    }
}
