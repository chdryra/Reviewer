package com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Dialogs.Layouts.Interfaces;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 24/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface AddEditLayout<T extends GvData> extends DialogLayout<T> {
    EditText getEditTextForKeyboardAction();

    T createGvDataFromInputs();

    void clearViews();

    void onAdd(T data);

    @Override
    View getView(int viewId);

    @Override
    void onActivityAttached(Activity activity, Bundle args);

    @Override
    View createLayoutUi(Context context, T data);

    @Override
    void initialise(T data);

    @Override
    void updateLayout(T data);
}
