/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation;


import android.view.MenuItem;

import com.chdryra.android.mygenerallibrary.OtherUtils.ActivityResultCode;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 13/11/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class MaiUp<T extends GvData> extends MenuActionItemBasic<T> {
    private static final ActivityResultCode RESULT_UP = ActivityResultCode.UP;

    @Override
    public void doAction(MenuItem item) {
        getApp().setReturnResult(RESULT_UP);
        getCurrentScreen().closeAndGoUp();
    }
}
