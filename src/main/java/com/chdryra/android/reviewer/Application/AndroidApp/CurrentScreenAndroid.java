/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Application.AndroidApp;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.widget.Toast;

import com.chdryra.android.mygenerallibrary.Dialogs.DialogDeleteConfirm;
import com.chdryra.android.mygenerallibrary.Dialogs.DialogShower;
import com.chdryra.android.reviewer.Application.CurrentScreen;

/**
 * Created by: Rizwan Choudrey
 * On: 27/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class CurrentScreenAndroid implements CurrentScreen {
    private final Activity mActivity;

    public CurrentScreenAndroid(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void close() {
        mActivity.finish();
    }

    @Override
    public void showAlert(String alert, int requestCode, Bundle args) {
        DialogShower.showAlert(alert, mActivity, requestCode, args);
    }

    @Override
    public void showDeleteConfirm(String deleteWhat, int requestCode) {
        DialogDeleteConfirm.showDialog(deleteWhat, requestCode, mActivity.getFragmentManager());
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
}
