/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Implementation;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.chdryra.android.mygenerallibrary.Dialogs.DialogAlertFragment;

/**
 * Takes care of showing requested dialogs via a static <code>show(.)</code> method.
 */
public class DialogShower {
    private static final String DELETE = "Delete";

    public static void show(DialogFragment dialog, Activity activity, int requestCode,
                            String tag, Bundle args) {
        dialog.setArguments(args);
        show(dialog, activity, requestCode, tag);
    }

    public static void showAlert(String alert, Activity activity, int requestCode, Bundle args) {
        DialogAlert dialog = DialogAlert.newDialog(alert, requestCode, args);
        show(dialog, activity, requestCode, DialogAlertFragment.ALERT_TAG);
    }

    public static void showDeleteConfirm(String deleteWhat, Activity activity, int requestCode) {
        String alert = deleteWhat != null ? DELETE + " " + deleteWhat + "?" : DELETE + "?";
        showAlert(alert, activity, requestCode, new Bundle());
    }

    public static void show(DialogFragment dialog, Activity activity, int requestCode, String
            tag) {
        dialog.setTargetFragment(null, requestCode);
        FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
        ft.add(dialog, tag);
        ft.commitAllowingStateLoss();
    }
}
