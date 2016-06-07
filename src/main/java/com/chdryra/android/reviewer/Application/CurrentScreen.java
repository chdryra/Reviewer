/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Application;

import android.os.Bundle;

/**
 * Created by: Rizwan Choudrey
 * On: 07/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface CurrentScreen {
    void close();

    void showAlert(String alert, int requestCode, Bundle args);

    void showDeleteConfirm(String deleteWhat, int requestCode);

    void showToast(String toast);

    void returnToPrevious();

    void setTitle(String title);

    boolean hasActionBar();

    void setHomeAsUp(boolean homeAsUp);
}
