/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation;


import android.os.Bundle;
import android.view.MenuItem;

import com.chdryra.android.mygenerallibrary.Dialogs.AlertListener;
import com.chdryra.android.mygenerallibrary.OtherUtils.ActivityResultCode;
import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 13/11/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class MaiUpDataEditor<T extends GvData> extends MaiDataEditor<T> implements AlertListener{
    private static final int ALERT = RequestCodeGenerator.getCode(MaiDataEditor.class);
    private static final ActivityResultCode RESULT_UP = ActivityResultCode.UP;

    @Override
    public void doAction(MenuItem item) {
        getCurrentScreen().showAlert(Strings.Alerts.DISCARD_EDITS, ALERT, this, new Bundle());
    }

    @Override
    public void onAlertNegative(int requestCode, Bundle args) {

    }

    @Override
    public void onAlertPositive(int requestCode, Bundle args) {
        getEditor().discardEdits();
        getApp().setReturnResult(RESULT_UP);
        getCurrentScreen().closeAndGoUp();
    }
}
