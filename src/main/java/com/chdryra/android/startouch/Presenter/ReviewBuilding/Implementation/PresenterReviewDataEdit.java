/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation;

import android.content.Intent;
import android.os.Bundle;

import com.chdryra.android.corelibrary.Dialogs.AlertListener;
import com.chdryra.android.corelibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ActivityResultListener;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.ReviewDataEditor;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.ReviewEditor;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Implementation.ReviewViewActions;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;

/**
 * Created by: Rizwan Choudrey
 * On: 24/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class PresenterReviewDataEdit<T extends GvDataParcelable> implements ActivityResultListener, AlertListener {
    private static final int ALERT = RequestCodeGenerator.getCode(PresenterReviewDataEdit.class);

    private final ReviewDataEditor<T> mEditor;
    private final ButtonAdd<T> mBannerButton;
    private final GridItemEdit<T> mGridItem;

    private PresenterReviewDataEdit(ReviewDataEditor<T> editor) {
        mEditor = editor;
        ReviewViewActions<T> actions = mEditor.getActions();
        mBannerButton = (ButtonAdd<T>) actions.getBannerButtonAction();
        mGridItem = (GridItemEdit<T>) actions.getGridItemAction();
    }

    public ReviewDataEditor<T> getEditor() {
        return mEditor;
    }

    public boolean onAdd(T data, int requestCode) {
        boolean success = false;
        if (requestCode == mBannerButton.getLaunchableRequestCode()) {
            success = mBannerButton.onAdd(data, requestCode);
        }

        return success;
    }

    public void onCancel(int requestCode) {
        if (requestCode == mBannerButton.getLaunchableRequestCode()) {
            mBannerButton.onCancel(requestCode);
        }
    }

    public void onDone(int requestCode) {
        if (requestCode == mBannerButton.getLaunchableRequestCode()) {
            mBannerButton.onDone(requestCode);
        }
    }

    public void onDelete(T data, int requestCode) {
        if (requestCode == mGridItem.getLaunchableRequestCode()) {
            mGridItem.onDelete(data, requestCode);
        }
    }

    public void onEdit(T oldDatum, T newDatum, int requestCode) {
        if (requestCode == mGridItem.getLaunchableRequestCode()) {
            mGridItem.onEdit(oldDatum, newDatum, requestCode);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == mBannerButton.getLaunchableRequestCode()) {
            mBannerButton.onActivityResult(requestCode, resultCode, data);
        } else if (requestCode == mGridItem.getLaunchableRequestCode()) {
            mGridItem.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void onBackPressed() {
        mEditor.getCurrentScreen().showAlert(Strings.Alerts.DISCARD_EDITS, ALERT, this, new Bundle());
    }

    @Override
    public void onAlertNegative(int requestCode, Bundle args) {

    }

    @Override
    public void onAlertPositive(int requestCode, Bundle args) {
        mEditor.discardEdits();
        mEditor.getCurrentScreen().close();
    }

    public static class Builder<T extends GvDataParcelable> {
        private final GvDataType<T> mDataType;
        public Builder(GvDataType<T> dataType) {
            mDataType = dataType;
        }

        public PresenterReviewDataEdit<T> build(ApplicationInstance app) {
            ReviewEditor<?> editor = app.getEditor().getEditor();
            return new PresenterReviewDataEdit<>(editor.newDataEditor(mDataType));
        }
    }
}
