/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.PresenterReviewDataEdit;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewDataEditor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .ReviewViewActions;

/**
 * Created by: Rizwan Choudrey
 * On: 24/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class PresenterReviewDataEditImpl<T extends GvData> implements PresenterReviewDataEdit<T> {
    private Context mContext;
    private ReviewDataEditor<T> mEditor;
    private MenuDataEdit<T> mMenu;
    private BannerButtonAdd<T> mBannerButton;
    private GridItemDataEdit<T> mGridItem;

    public PresenterReviewDataEditImpl(Context context,
                                       ReviewDataEditor<T> editor) {
        mContext = context;
        mEditor = editor;

        ReviewViewActions<T> actions = mEditor.getActions();
        mMenu = (MenuDataEdit<T>) actions.getMenuAction();
        mBannerButton = (BannerButtonAdd<T>) actions.getBannerButtonAction();
        mGridItem = (GridItemDataEdit<T>) actions.getGridItemAction();
    }

    protected Context getContext() {
        return mContext;
    }

    @Override
    public void onAlertNegative(int requestCode, Bundle args) {
        if (requestCode == mMenu.getAlertRequestCode()) {
            mMenu.onAlertNegative(requestCode, args);
        } else if(requestCode == mBannerButton.getAlertRequestCode()) {
            mBannerButton.onAlertNegative(requestCode, args);
        } else if(requestCode == mGridItem.getAlertRequestCode()) {
            mGridItem.onAlertNegative(requestCode, args);
        }
    }

    @Override
    public void onAlertPositive(int requestCode, Bundle args) {
        if (requestCode == mMenu.getAlertRequestCode()) {
            mMenu.onAlertPositive(requestCode, args);
        } else if(requestCode == mBannerButton.getAlertRequestCode()) {
            mBannerButton.onAlertPositive(requestCode, args);
        } else if(requestCode == mGridItem.getAlertRequestCode()) {
            mGridItem.onAlertPositive(requestCode, args);
        }
    }

    @Override
    public boolean onAdd(T data, int requestCode) {
        boolean success = false;
        if(requestCode == mBannerButton.getLaunchableRequestCode()) {
            success = mBannerButton.onAdd(data, requestCode);
        }

        return success;
    }

    @Override
    public void onCancel(int requestCode) {
        if(requestCode == mBannerButton.getLaunchableRequestCode()) {
            mBannerButton.onCancel(requestCode);
        }
    }

    @Override
    public void onDone(int requestCode) {
        if(requestCode == mBannerButton.getLaunchableRequestCode()) {
            mBannerButton.onDone(requestCode);
        }
    }

    @Override
    public void onDelete(T data, int requestCode) {
        if(requestCode == mGridItem.getLaunchableRequestCode()) {
            mGridItem.onDelete(data, requestCode);
        }
    }

    @Override
    public void onEdit(T oldDatum, T newDatum, int requestCode) {
        if(requestCode == mGridItem.getLaunchableRequestCode()) {
            mGridItem.onEdit(oldDatum, newDatum, requestCode);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == mBannerButton.getLaunchableRequestCode()) {
            mBannerButton.onActivityResult(requestCode, resultCode, data);
        } else if(requestCode == mGridItem.getLaunchableRequestCode()) {
            mGridItem.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public ReviewDataEditor<T> getEditor() {
        return mEditor;
    }
}
