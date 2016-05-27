/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Application;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.widget.Toast;

import com.chdryra.android.mygenerallibrary.Dialogs.DialogDeleteConfirm;
import com.chdryra.android.mygenerallibrary.Dialogs.DialogShower;

/**
 * Created by: Rizwan Choudrey
 * On: 27/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class CurrentScreen {
    private Activity mActivity;

    public CurrentScreen(Activity activity) {
        mActivity = activity;
    }

    public void close() {
        mActivity.finish();
    }

    public void showAlert(String alert, int requestCode, Bundle args) {
        DialogShower.showAlert(alert, mActivity, requestCode, args);
    }

    public void showDeleteConfirm(String deleteWhat, int requestCode) {
        DialogDeleteConfirm.showDialog(deleteWhat, requestCode, mActivity.getFragmentManager());
    }

    public void showToast(String toast) {
        Toast.makeText(mActivity, toast, Toast.LENGTH_SHORT).show();
    }

    public void returnToPrevious() {
        if (NavUtils.getParentActivityName(mActivity) != null) {
            Intent i = NavUtils.getParentActivityIntent(mActivity);
            NavUtils.navigateUpTo(mActivity, i);
        }
    }

    public void setTitle(String title) {
        ActionBar actionBar = mActivity.getActionBar();
        if (actionBar != null) {
            if (title != null) actionBar.setTitle(title);
        }
    }

    public boolean hasActionBar() {
        ActionBar actionBar = mActivity.getActionBar();
        return actionBar != null;
    }

    public void setHomeAsUp(boolean homeAsUp) {
        ActionBar actionBar = mActivity.getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(homeAsUp);
            actionBar.setDisplayShowHomeEnabled(false);
        }
    }
}
