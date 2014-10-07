/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import com.chdryra.android.mygenerallibrary.DialogAlert;

public class DialogSetImageAsBackgroundFragment extends DialogAlert {
    @Override
    protected String getAlertString() {
        return getResources().getString(R.string.dialog_set_image_as_background);
    }

    @Override
    protected void onRightButtonClick() {
        getNewReturnDataIntent().putExtras(getArguments());
        super.onRightButtonClick();
    }
}
