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
import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.DataBuilderAdapter;

/**
 * Created by: Rizwan Choudrey
 * On: 13/11/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class MaiDeleteAction<T extends GvData> extends MaiDataEditor<T> implements AlertListener {
    private static final int ALERT_DIALOG = RequestCodeGenerator.getCode("DeleteConfirm");

    private final String mDeleteWhat;

    public MaiDeleteAction(String deleteWhat) {
        mDeleteWhat = deleteWhat;
    }

    @Override
    public void doAction(MenuItem item) {
        if (hasDataToDelete()) showDeleteConfirmDialog();
    }

    @Override
    public void onAlertNegative(int requestCode, Bundle args) {

    }

    @Override
    public void onAlertPositive(int requestCode, Bundle args) {
        if (requestCode == ALERT_DIALOG) doDelete();
    }

    private DataBuilderAdapter<?> getBuilder() {
        return (DataBuilderAdapter<?>) getEditor().getAdapter();
    }

    private boolean hasDataToDelete() {
        GvDataList<T> data = getReviewView().getGridData();
        return data != null && data.size() > 0;
    }

    private void showDeleteConfirmDialog() {
        String deleteWhat = "all " + mDeleteWhat;
        getCurrentScreen().showDeleteConfirm(deleteWhat, ALERT_DIALOG, this);
    }

    private void doDelete() {
        getBuilder().deleteAll();
    }
}
