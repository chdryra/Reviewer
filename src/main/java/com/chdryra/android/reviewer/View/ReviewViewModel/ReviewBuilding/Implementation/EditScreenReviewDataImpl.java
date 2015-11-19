/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 24 January, 2015
 */

package com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RatingBar;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces
        .DataBuilderAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces
        .ReviewBuilderAdapter;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.RatingBarActionNone;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Factories.FactoryReviewDataEditor;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Interfaces.ReviewDataEditor;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.ReviewViewActions;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.ReviewViewParams;

/**
 * Created by: Rizwan Choudrey
 * On: 24/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class EditScreenReviewDataImpl<T extends GvData> implements EditScreenReviewData<T> {

    private Context mContext;
    private ReviewDataEditor<T> mEditor;
    private GvDataType<T> mDataType;
    private MenuDataEdit<T> mMenu;
    private BannerButtonEdit<T> mBannerButton;
    private GridItemEdit<T> mGriditem;

    public EditScreenReviewDataImpl(Context context,
                                    ReviewBuilderAdapter<?> builder,
                                    GvDataType<T> dataType,
                                    FactoryReviewDataEditor editorFactory) {
        mContext = context;
        mDataType = dataType;

        //Adapter
        DataBuilderAdapter<T> adapter = builder.getDataBuilderAdapter(mDataType);

        //Actions
        mMenu = newMenuAction();
        mBannerButton = newBannerButtonAction();
        mGriditem = newGridItemAction();
        ReviewViewActions<T> actions
                = new ReviewViewActions<>(newSubjectAction(), newRatingBarAction(), mBannerButton, mGriditem, mMenu);

        mEditor = editorFactory.newEditor(adapter, actions);
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
    public void onGvDataDelete(T data, int requestCode) {
        if(requestCode == mGriditem.getLaunchableRequestCode()) {
            mGriditem.onGvDataDelete(data, requestCode);
        }
    }

    @Override
    public void onGvDataEdit(T oldDatum, T newDatum, int requestCode) {
        if(requestCode == mGriditem.getLaunchableRequestCode()) {
            mGriditem.onGvDataEdit(oldDatum, newDatum, requestCode);
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

    //protected methods
    protected String getBannerButtonTitle() {
        String title = mContext.getResources().getString(R.string.button_add);
        title += " " + mDataType.getDatumName();
        return title;
    }

    protected SubjectEdit<T> newSubjectAction() {
        return new SubjectEdit<>();
    }

    protected RatingBarEdit<T> newRatingBarAction() {
        return new RatingBarEdit<T>();
    }

    protected MenuDataEdit<T> newMenuAction() {
        return new MenuDataEdit<>(mDataType);
    }

    protected GridItemEdit<T> newGridItemAction() {
        return new GridItemEdit<>(mDataType);
    }

    protected BannerButtonEdit<T> newBannerButtonAction() {
        return new BannerButtonEdit<>(mDataType, getBannerButtonTitle());
    }

    @Override
    public ReviewDataEditor<T> getEditor() {
        return mEditor;
    }
}
