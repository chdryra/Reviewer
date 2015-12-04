/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 24 January, 2015
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.ReviewViewActions;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewDataEditScreen;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewDataEditor;

/**
 * Created by: Rizwan Choudrey
 * On: 24/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewDataEditScreenImpl<T extends GvData> implements ReviewDataEditScreen<T> {
    private Context mContext;
    private ReviewDataEditor<T> mEditor;
    private MenuDataEdit<T> mMenu;
    private BannerButtonAdd<T> mBannerButton;
    private GridItemDataEdit<T> mGriditem;

    public ReviewDataEditScreenImpl(Context context,
                                    ReviewDataEditor<T> editor) {
        mContext = context;
        mEditor = editor;

        ReviewViewActions<T> actions = mEditor.getActions();
        mMenu = (MenuDataEdit<T>) actions.getMenuAction();
        mBannerButton = (BannerButtonAdd<T>) actions.getBannerButtonAction();
        mGriditem = (GridItemDataEdit<T>) actions.getGridItemAction();
    }

    protected Context getContext() {
        return mContext;
    }

    @Override
    public void onAlertNegative(int requestCode, Bundle args) {
        if (requestCode == mMenu.getAlertRequestCode()) {
            mMenu.onAlertNegative(requestCode, args);
        } else if(requestCode == mGriditem.getAlertRequestCode()) {
            mGriditem.onAlertNegative(requestCode, args);
        }
    }

    @Override
    public void onAlertPositive(int requestCode, Bundle args) {
        if (requestCode == mMenu.getAlertRequestCode()) {
            mMenu.onAlertPositive(requestCode, args);
        } else if(requestCode == mGriditem.getAlertRequestCode()) {
            mGriditem.onAlertPositive(requestCode, args);
        }
    }

    @Override
    public boolean onGvDataAdd(T data, int requestCode) {
        boolean success = false;
        if(requestCode == mBannerButton.getLaunchableRequestCode()) {
            success = mBannerButton.onGvDataAdd(data, requestCode);
        }

        return success;
    }

    @Override
    public void onGvDataCancel(int requestCode) {
        if(requestCode == mBannerButton.getLaunchableRequestCode()) {
            mBannerButton.onGvDataCancel(requestCode);
        }
    }

    @Override
    public void onGvDataDone(int requestCode) {
        if(requestCode == mBannerButton.getLaunchableRequestCode()) {
            mBannerButton.onGvDataDone(requestCode);
        }
    }

    @Override
    public void onDelete(T data, int requestCode) {
        if(requestCode == mGriditem.getLaunchableRequestCode()) {
            mGriditem.onDelete(data, requestCode);
        }
    }

    @Override
    public void onEdit(T oldDatum, T newDatum, int requestCode) {
        if(requestCode == mGriditem.getLaunchableRequestCode()) {
            mGriditem.onEdit(oldDatum, newDatum, requestCode);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == mBannerButton.getLaunchableRequestCode()) {
            mBannerButton.onActivityResult(requestCode, resultCode, data);
        } else if(requestCode == mGriditem.getLaunchableRequestCode()) {
            mGriditem.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public ReviewDataEditor<T> getEditor() {
        return mEditor;
    }
}
