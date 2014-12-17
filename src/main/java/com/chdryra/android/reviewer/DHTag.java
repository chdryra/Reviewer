/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 21 October, 2014
 */

package com.chdryra.android.reviewer;

import android.widget.EditText;

/**
 * Created by: Rizwan Choudrey
 * On: 21/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * {@link DialogHolderAddEdit}: tags
 */
class DHTag extends DialogHolderAddEdit<GvTagList.GvTag> {
    private static final int             LAYOUT    = R.layout.dialog_tag;
    private static final int             TAG       = R.id.tag_edit_text;
    private static final GvTagList.GvTag NULL_DATA = new GvTagList.GvTag();

    DHTag(DialogGvDataAddFragment<GvTagList.GvTag> dialogAdd) {
        super(LAYOUT, new int[]{TAG}, dialogAdd, NULL_DATA);
    }

    DHTag(DialogGvDataEditFragment<GvTagList.GvTag> dialogEdit) {
        super(LAYOUT, new int[]{TAG}, dialogEdit);
    }

    @Override
    protected EditText getEditTextForKeyboardAction() {
        return (EditText) getView(TAG);
    }

    @Override
    protected String getDialogTitleOnAdd(GvTagList.GvTag data) {
        return "#" + data.get();
    }

    @Override
    protected String getDeleteConfirmDialogTitle(GvTagList.GvTag data) {
        return data.get();
    }

    @Override
    protected GvTagList.GvTag createGvData() {
        return new GvTagList.GvTag(((EditText) getView(TAG)).getText().toString().trim());
    }

    @Override
    protected void update(GvTagList.GvTag tag) {
        ((EditText) getView(TAG)).setText(tag.get());
    }
}
