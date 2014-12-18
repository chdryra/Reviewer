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

/**
 * A hotchpotch of methods required so as to be able to use the same xml layout for both add and
 * edit dialogs for a given type of {@link GvDataList.GvData}.
 *
 * @param <T>: {@link GvDataList.GvData} type
 */
public interface DialogGvDataMethods<T extends GvDataList.GvData> {
    public String getDialogTitleOnAdd(T data);

    public String getDeleteConfirmDialogTitle(T data);

    public EditText getEditTextForKeyboardAction();

    public T createGvDataFromViews();

    public void updateViews(T data);

    public GvDataViewHolder<T> getViewHolder();
}
