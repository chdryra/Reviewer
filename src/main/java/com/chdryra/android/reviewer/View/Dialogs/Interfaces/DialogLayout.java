package com.chdryra.android.reviewer.View.Dialogs.Interfaces;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 24/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface DialogLayout<T extends GvData> {
    View getView(int viewId);

    void onActivityAttached(Activity activity, Bundle args);

    View createLayoutUi(Context context, T data);

    void initialise(T data);

    //abstract methods
    //abstract
    void updateLayout(T data);
}
