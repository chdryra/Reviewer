/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Interfaces;

import android.widget.EditText;

import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 24/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface DatumLayoutEdit<T extends GvData> extends DatumLayoutView<T> {
    EditText getEditTextForKeyboardAction();

    T createGvDataFromInputs();

    void clearViews();

    void onAdd(T data);
}
