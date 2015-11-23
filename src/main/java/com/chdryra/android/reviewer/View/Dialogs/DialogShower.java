/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.View.Dialogs;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;

import com.chdryra.android.mygenerallibrary.DialogAlertFragment;

/**
 * Takes care of showing requested dialogs via a static <code>show(.)</code> method.
 */
public class DialogShower {
    //Static methods
    public static void show(DialogFragment dialog, Activity activity, int requestCode,
                            String tag, Bundle args) {
        dialog.setArguments(args);
        show(dialog, activity, requestCode, tag);
    }

    public static void show(DialogFragment dialog, Activity activity, int requestCode, String tag) {
        dialog.setTargetFragment(null, requestCode);
        dialog.show(activity.getFragmentManager(), tag);
    }

    public static void showAlert(String alert, Activity activity, int requestCode, String tag) {
        showAlert(alert, activity,requestCode, tag, new Bundle());
    }

    public static void showAlert(String alert, Activity activity, int requestCode, String tag, Bundle args) {
        DialogAlertFragment dialog = DialogAlertFragment.newDialog(alert, requestCode, args);
        show(dialog, activity, requestCode, tag);
    }
}
