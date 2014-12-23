/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 17 December, 2014
 */

package com.chdryra.android.reviewer;

import android.widget.EditText;

/**
 * Created by: Rizwan Choudrey
 * On: 17/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class GvDataViewEdit<T extends GvDataList.GvData> implements GvDataView<T> {

    private GvDataEditor        mEditor;
    private GvDataViewLayout<T> mLayout;

    public interface GvDataEditor {
        public void setKeyboardAction(EditText editText);

        public void setDeleteConfirmTitle(String title);
    }

    public GvDataViewEdit(GvDataEditor editor, GvDataViewLayout<T> layout) {
        mEditor = editor;
        mLayout = layout;
    }

    @Override
    public void initialiseView(T data) {
        mEditor.setKeyboardAction(mLayout.getEditTextForKeyboardAction());
        mEditor.setDeleteConfirmTitle(mLayout.getDeleteConfirmDialogTitle(data));
        mLayout.updateViews(data);
    }

    @Override
    public void updateView(T data) {
    }

    @Override
    public T getGvData() {
        return mLayout.createGvDataFromViews();
    }
}