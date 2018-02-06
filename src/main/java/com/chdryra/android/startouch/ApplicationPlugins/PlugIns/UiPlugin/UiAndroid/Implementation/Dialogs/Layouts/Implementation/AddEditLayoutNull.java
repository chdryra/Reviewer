/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Implementation;



import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Interfaces.DatumLayoutEdit;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 05/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class AddEditLayoutNull<T extends GvData> implements DatumLayoutEdit<T> {
    private Context mContext;
    private T mNullData;

    public AddEditLayoutNull(Context context, T nullData) {
        mContext = context;
        mNullData = nullData;
    }

    @Override
    public EditText getEditTextForKeyboardAction() {
        return new EditText(mContext);
    }

    @Override
    public T createGvDataFromInputs() {
        return mNullData;
    }

    @Override
    public void clearViews() {

    }

    @Override
    public void onAdd(T data) {

    }

    @Override
    public View getView(int viewId) {
        return new View(mContext);
    }

    @Override
    public void onActivityAttached(Activity activity, Bundle args) {

    }

    @Override
    public View createLayoutUi(Context context, T data) {
        return new View(context);
    }

    @Override
    public void initialise(T data) {

    }

    @Override
    public void updateView(T data) {

    }

    @Override
    public void onActivityStopped() {

    }
}
