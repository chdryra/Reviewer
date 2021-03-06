/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid
        .Implementation.Dialogs.Implementation;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.chdryra.android.corelibrary.Dialogs.AlertListener;
import com.chdryra.android.corelibrary.Dialogs.DialogAlertFragment;
import com.chdryra.android.startouch.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.startouch.Application.Interfaces.ApplicationInstance;

/**
 * Created by: Rizwan Choudrey
 * On: 26/09/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class DialogAlert extends DialogAlertFragment {
    public static DialogAlert newDialog(String alert, @Nullable Fragment targetFragment, int
            requestCode) {
        return newDialog(alert, targetFragment, requestCode, new Bundle());
    }

    public static DialogAlert newDialog(String alert, int requestCode, Bundle args) {
        return newDialog(alert, null, requestCode, args);
    }

    public static DialogAlert newDialog(String alert, @Nullable Fragment targetFragment,
                                        int requestCode, Bundle args) {
        args.putString(ALERT_TAG, alert);
        DialogAlert dialog = new DialogAlert();
        dialog.setArguments(args);
        dialog.setTargetFragment(targetFragment, requestCode);
        return dialog;
    }

    @Override
    protected AlertListener getTargetListener() {
        ApplicationInstance app = AppInstanceAndroid.getInstance(getActivity());
        AlertListener listener = app.getUi().getCurrentScreen().getAlertListener(getRequestCode());
        return listener != null ? listener : super.getTargetListener();
    }
}
