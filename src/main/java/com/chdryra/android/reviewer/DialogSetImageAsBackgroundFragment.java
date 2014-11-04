/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import com.chdryra.android.mygenerallibrary.DialogAlertFragment;

/**
 * Alert Dialog for confirming whether user wants to set currently selected image as review cover
 * (background).
 */
public class DialogSetImageAsBackgroundFragment extends DialogAlertFragment {
    @Override
    protected String getAlertString() {
        return getResources().getString(R.string.dialog_set_image_as_background);
    }

    @Override
    protected void onRightButtonClick() {
        createNewReturnData().putExtras(getArguments());
        super.onRightButtonClick();
    }
}
