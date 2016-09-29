/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Application.Implementation;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.SparseArray;
import android.widget.Toast;

import com.chdryra.android.mygenerallibrary.Dialogs.AlertListener;
import com.chdryra.android.reviewer.Application.Interfaces.CurrentScreen;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Implementation.DialogShower;

/**
 * Created by: Rizwan Choudrey
 * On: 27/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
class CurrentScreenAndroid implements CurrentScreen {
    private final Activity mActivity;
    private SparseArray<AlertListener> mAlertListeners;

    CurrentScreenAndroid(Activity activity) {
        mActivity = activity;
        mAlertListeners = new SparseArray<>();
    }

    @Override
    public void close() {
        mActivity.finish();
    }

    @Override
    public void showAlert(String alert, int requestCode, AlertListener listener, Bundle args) {
        mAlertListeners.put(requestCode, listener);
        DialogShower.showAlert(alert, mActivity, requestCode, args);
    }

    @Override
    public void showDeleteConfirm(String deleteWhat, int requestCode, AlertListener listener) {
        mAlertListeners.put(requestCode, listener);
        DialogShower.showDeleteConfirm(deleteWhat, mActivity, requestCode);
    }

    @Override
    public void showDialog(DialogFragment dialog, int requestCode, String tag, Bundle args) {
        DialogShower.show(dialog, mActivity, requestCode, tag, args);
    }

    @Override
    public void showToast(String toast) {
        Toast.makeText(mActivity, toast, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void returnToPrevious() {
        if (NavUtils.getParentActivityName(mActivity) != null) {
            Intent i = NavUtils.getParentActivityIntent(mActivity);
            NavUtils.navigateUpTo(mActivity, i);
        }
    }

    @Override
    public void setTitle(String title) {
        ActionBar actionBar = mActivity.getActionBar();
        if (actionBar != null) {
            if (title != null) actionBar.setTitle(title);
        }
    }

    @Override
    public boolean hasActionBar() {
        ActionBar actionBar = mActivity.getActionBar();
        return actionBar != null;
    }

    @Override
    public void setHomeAsUp(boolean homeAsUp) {
        ActionBar actionBar = mActivity.getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(homeAsUp);
            actionBar.setDisplayShowHomeEnabled(false);
        }
    }

    @Override
    public AlertListener getAlertListener(int requestCode) {
        AlertListener alertListener = mAlertListeners.get(requestCode);
        mAlertListeners.remove(requestCode);
        return alertListener;
    }
}
