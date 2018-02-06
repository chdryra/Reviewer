/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Dialogs.Layouts.Implementation;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Dialogs.Layouts.Interfaces.GvDataAdder;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Dialogs.Layouts.Interfaces.GvDataEditor;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.startouch.R;

/**
 * Created by: Rizwan Choudrey
 * On: 17/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class AddEditComment extends AddEditLayoutBasic<GvComment> {
    private static final int LAYOUT = R.layout.dialog_comment_add_edit;
    private static final int COMMENT = R.id.comment_edit_text;
    private static final int BUTTON = R.id.comment_hash;
    private static final String HASHTAG = Strings.Buttons.CommentEdit.HASHTAG;

    private GvComment mCurrent;

    //By reflection
    public AddEditComment(GvDataAdder adder) {
        super(GvComment.class, newHolder(), COMMENT, adder);
    }

    public AddEditComment(GvDataEditor editor) {
        super(GvComment.class, newHolder(), COMMENT, editor);
    }

    @NonNull
    private static LayoutHolder newHolder() {
        return new LayoutHolder(LAYOUT, COMMENT, BUTTON);
    }

    @Override
    public GvComment createGvDataFromInputs() {
        boolean isHeadline = mCurrent != null && mCurrent.isHeadline();
        mCurrent = new GvComment(getComment().getText().toString().trim(), isHeadline);

        return mCurrent;
    }

    @Override
    public void updateView(GvComment comment) {
        mCurrent = comment;
        getComment().setText(mCurrent.getComment());
    }

    @Override
    protected void onLayoutInflated() {
        Button button = (Button) getView(BUTTON);
        button.setText(HASHTAG);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getComment().getText().insert(getComment().getSelectionStart(), HASHTAG);
            }
        });
    }

    private EditText getComment() {
        return (EditText) getView(COMMENT);
    }
}
