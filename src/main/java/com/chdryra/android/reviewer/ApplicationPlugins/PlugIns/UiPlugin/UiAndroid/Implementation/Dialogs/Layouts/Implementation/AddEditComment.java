/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Dialogs.Layouts.Implementation;

import android.support.annotation.NonNull;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Interfaces.GvDataAdder;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Interfaces.GvDataEditor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.R;

/**
 * Created by: Rizwan Choudrey
 * On: 17/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class AddEditComment extends AddEditLayoutBasic<GvComment> {
    private static final int LAYOUT = R.layout.dialog_comment_add_edit;
    private static final int COMMENT = R.id.comment_edit_text;
    private static final int BUTTON_LEFT = R.id.comment_edit_left;
    private static final int BUTTON_MIDDLE = R.id.comment_edit_middle;
    private static final int BUTTON_RIGHT = R.id.comment_edit_right;

    private static final SparseArray<String> BUTTON_MAPPING = new SparseArray<>();

    static {
        BUTTON_MAPPING.put(BUTTON_LEFT, Strings.Buttons.CommentEdit.EQUALS);
        BUTTON_MAPPING.put(BUTTON_MIDDLE, Strings.Buttons.CommentEdit.STAR);
        BUTTON_MAPPING.put(BUTTON_RIGHT, Strings.Buttons.CommentEdit.HASHTAG);
    }

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
        return new LayoutHolder(LAYOUT, COMMENT, BUTTON_LEFT, BUTTON_MIDDLE, BUTTON_RIGHT);
    }

    @Override
    public GvComment createGvDataFromInputs() {
        boolean isHeadline = mCurrent != null && mCurrent.isHeadline();
        mCurrent = new GvComment(getComment().getText().toString().trim(), isHeadline);

        return mCurrent;
    }

    @Override
    public void updateLayout(GvComment comment) {
        mCurrent = comment;
        getComment().setText(mCurrent.getComment());
    }

    @Override
    protected void onLayoutInflated() {
        setupButton(BUTTON_LEFT);
        setupButton(BUTTON_MIDDLE);
        setupButton(BUTTON_RIGHT);
    }

    private EditText getComment() {
        return (EditText) getView(COMMENT);
    }

    private void setupButton(final int buttonId) {
        Button button = (Button) getView(buttonId);
        button.setText(BUTTON_MAPPING.get(buttonId));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getComment().getText().append(BUTTON_MAPPING.get(buttonId));
            }
        });
    }
}
