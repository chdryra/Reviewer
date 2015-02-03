/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 18 December, 2014
 */

package com.chdryra.android.reviewer;

import android.widget.EditText;

/**
 * Created by: Rizwan Choudrey
 * On: 18/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class LayoutTag extends GvDataViewLayout<GvTagList.GvTag> {
    public static final int   LAYOUT = R.layout.dialog_tag;
    public static final int   TAG    = R.id.tag_edit_text;
    public static final int[] VIEWS  = new int[]{TAG};

    public LayoutTag(GvDataViewAdd.GvDataAdder adder) {
        super(GvTagList.GvTag.class, LAYOUT, VIEWS, TAG, adder);
    }

    public LayoutTag(GvDataViewEdit.GvDataEditor editor) {
        super(GvTagList.GvTag.class, LAYOUT, VIEWS, TAG, editor);
    }

    @Override
    public String getTitleOnAdd(GvTagList.GvTag data) {
        return "#" + data.get();
    }

    @Override
    public String getDeleteConfirmDialogTitle(GvTagList.GvTag data) {
        return data.get();
    }

    @Override
    public GvTagList.GvTag createGvDataFromViews() {
        return new GvTagList.GvTag(((EditText) mViewHolder.getView(TAG)).getText().toString()
                .trim());
    }

    @Override
    public void updateViews(GvTagList.GvTag tag) {
        ((EditText) mViewHolder.getView(TAG)).setText(tag.get());
    }
}