/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Application.Interfaces;

import android.app.DialogFragment;
import android.os.Bundle;

import com.chdryra.android.corelibrary.Dialogs.AlertListener;

/**
 * Created by: Rizwan Choudrey
 * On: 07/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface CurrentScreen {
    void close();

    void showAlert(String alert, int requestCode, AlertListener listener, Bundle args);

    void showDeleteConfirm(String deleteWhat, int requestCode, AlertListener listener);

    void showDialog(DialogFragment dialog, int requestCode, String tag, Bundle args);

    void showToast(String toast);

    void closeAndGoUp();

    void setTitle(String title);

    boolean hasActionBar();

    void setHomeAsUp(boolean homeAsUp);

    AlertListener getAlertListener(int requestCode);
}
