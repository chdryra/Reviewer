/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 24 January, 2015
 */

package com.chdryra.android.reviewer.View.Screens;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RatingBar;

import com.chdryra.android.mygenerallibrary.DialogAlertFragment;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.ApplicationSingletons.Administrator;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.Dialogs.DialogGvDataAdd;
import com.chdryra.android.reviewer.View.Dialogs.DialogGvDataEdit;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCriterionList;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;

/**
 * Created by: Rizwan Choudrey
 * On: 24/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class EditScreenReviewData<T extends GvData> implements
        DialogAlertFragment.DialogAlertListener,
        DialogGvDataAdd.GvDataAddListener<T>,
        DialogGvDataEdit.GvDataEditListener<T>,
        ActivityResultListener {

    private Context mContext;
    private ReviewDataEditor<T> mEditor;
    private GvDataType<T> mDataType;
    private MenuDataEdit<T> mMenu;
    private SubjectEdit<T> mSubject;
    private RatingBarEdit mRatingBar;
    private BannerButtonEdit<T> mBannerButton;
    private GridItemEdit<T> mGriditem;

    public EditScreenReviewData(Context context, GvDataType<T> dataType) {
        mContext = context;
        mDataType = dataType;

        //Adapter
        ReviewBuilderAdapter builder = Administrator.get(context).getReviewBuilder();
        ReviewBuilderAdapter.DataBuilderAdapter<T> adapter = builder.getDataBuilder(mDataType);

        //Parameters
        ReviewViewParams params = DefaultParameters.getParams(mDataType);

        //Actions
        mMenu = newMenuAction();
        mSubject = newSubjectAction();
        mRatingBar = newRatingBarAction();
        mBannerButton = newBannerButtonAction();
        mGriditem = newGridItemAction();
        ReviewViewActions actions = new ReviewViewActions();
        actions.setAction(mMenu);
        actions.setAction(mSubject);
        actions.setAction(mRatingBar);
        actions.setAction(mBannerButton);
        actions.setAction(mGriditem);

        mEditor = new ReviewDataEditor<>(adapter, params, actions);
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

    //Static methods
    public static <T extends GvData> EditScreenReviewData<T> newScreen(Context context, GvDataType<T> dataType) {
        EditScreenReviewData screen;
        if (dataType.equals(GvCommentList.GvComment.TYPE)) {
            screen = new EditScreenComments(context);
        } else if (dataType.equals(GvCriterionList.GvCriterion.TYPE)) {
            screen = new EditScreenCriteria(context);
        } else if (dataType.equals(GvFactList.GvFact.TYPE)) {
            screen = new EditScreenFacts(context);
        } else if (dataType.equals(GvImageList.GvImage.TYPE)) {
            screen = new EditScreenImages(context);
        } else if (dataType.equals(GvLocationList.GvLocation.TYPE)) {
            screen = new EditScreenLocations(context);
        } else if (dataType.equals(GvTagList.GvTag.TYPE)) {
            screen = new EditScreenTags(context);
        } else {
            screen = new EditScreenReviewData<>(context, dataType);
        }

        return screen;
    }

    //protected methods
    protected String getBannerButtonTitle() {
        String title = mContext.getResources().getString(R.string.button_add);
        title += " " + mDataType.getDatumName();
        return title;
    }

    protected SubjectEdit<T> newSubjectAction() {
        return new SubjectEdit<>(mDataType);
    }

    protected RatingBarEdit newRatingBarAction() {
        return new RatingBarEdit();
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

    public ReviewDataEditor<T> getEditor() {
        return mEditor;
    }

    protected class RatingBarEdit extends ReviewViewAction.RatingBarAction {
        //Overridden
        @Override
        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
            if (fromUser) mEditor.setRating(rating, true);
        }
    }
}
