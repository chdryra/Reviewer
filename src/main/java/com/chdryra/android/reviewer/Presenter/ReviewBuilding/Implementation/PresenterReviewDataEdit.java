/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.content.Intent;
import android.os.Bundle;

import com.chdryra.android.mygenerallibrary.Dialogs.AlertListener;
import com.chdryra.android.reviewer.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ActivityResultListener;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewDataEditor;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewEditor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .Implementation.ReviewViewActions;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;

/**
 * Created by: Rizwan Choudrey
 * On: 24/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class PresenterReviewDataEdit<T extends GvDataParcelable> implements AlertListener,
        ActivityResultListener {
    private final MenuEdit<T> mMenu;
    private final BannerButtonAdd<T> mBannerButton;
    private final GridItemEdit<T> mGridItem;

    private PresenterReviewDataEdit(ReviewDataEditor<T> editor) {
        ReviewViewActions<T> actions = editor.getActions();
        mMenu = (MenuEdit<T>) actions.getMenuAction();
        mBannerButton = (BannerButtonAdd<T>) actions.getBannerButtonAction();
        mGridItem = (GridItemEdit<T>) actions.getGridItemAction();
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

    @Override
    public void onAlertNegative(int requestCode, Bundle args) {
        if (requestCode == mMenu.getAlertRequestCode()) {
            mMenu.onAlertNegative(requestCode, args);
        } else if (requestCode == mBannerButton.getAlertRequestCode()) {
            mBannerButton.onAlertNegative(requestCode, args);
        } else if (requestCode == mGridItem.getAlertRequestCode()) {
            mGridItem.onAlertNegative(requestCode, args);
        }
    }

    @Override
    public void onAlertPositive(int requestCode, Bundle args) {
        if (requestCode == mMenu.getAlertRequestCode()) {
            mMenu.onAlertPositive(requestCode, args);
        } else if (requestCode == mBannerButton.getAlertRequestCode()) {
            mBannerButton.onAlertPositive(requestCode, args);
        } else if (requestCode == mGridItem.getAlertRequestCode()) {
            mGridItem.onAlertPositive(requestCode, args);
        }
    }

    public static class Builder<T extends GvDataParcelable> {
        private final GvDataType<T> mDataType;
        private ReviewDataEditor<T> mEditor;

        public Builder(GvDataType<T> dataType) {
            mDataType = dataType;
        }

        public ReviewDataEditor<T> getEditor() {
            return mEditor;
        }

        public PresenterReviewDataEdit<T> build(ApplicationInstance app) {
            ReviewEditor<?> editor = app.getReviewBuilder().getReviewEditor();
            mEditor = editor.newDataEditor(mDataType);
            return new PresenterReviewDataEdit<>(mEditor);
        }
    }
}
