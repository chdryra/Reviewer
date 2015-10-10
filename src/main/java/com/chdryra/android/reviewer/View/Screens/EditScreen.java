/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 24 January, 2015
 */

package com.chdryra.android.reviewer.View.Screens;

import android.content.Context;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.ApplicationSingletons.Administrator;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCriterionList;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;

/**
 * Created by: Rizwan Choudrey
 * On: 24/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class EditScreen<T extends GvData> {
    private Context mContext;
    private ReviewDataEditor<T> mEditor;
    private GvDataType<T> mDataType;

    private EditScreen(Context context, GvDataType<T> dataType) {
        mContext = context;
        mDataType = dataType;

        //Actions
        ReviewViewActionCollection actions = getActions();

        //Parameters
        ReviewViewParams params = DefaultParameters.getParams(mDataType);

        //Builder
        ReviewBuilderAdapter builder = Administrator.get(context).getReviewBuilder();
        ReviewBuilderAdapter.DataBuilderAdapter<T> adapter = builder.getDataBuilder(mDataType);

        mEditor = new ReviewDataEditor<>(adapter, params, actions);
    }

    //Static methods
    public static <T extends GvData> ReviewView newScreen(Context context, GvDataType<T> dataType) {
        return new EditScreen<>(context, dataType).getEditor();
    }

    //private methods
    private ReviewView getEditor() {
        return mEditor;
    }

    private ReviewViewActionCollection getActions() {
        ReviewViewActionCollection actions = new ReviewViewActionCollection();
        actions.setAction(new RatingBar());
        actions.setAction(newBannerButtonAction());
        actions.setAction(newGridItemAction());
        actions.setAction(newMenuAction());
        return actions;
    }

    private ReviewViewAction.MenuAction newMenuAction() {
        if (mDataType == GvCommentList.GvComment.TYPE) {
            return new EditScreenComments.MenuEditComment();
        } else if (mDataType == GvCriterionList.GvCriterion.TYPE) {
            return new EditScreenCriteria.MenuEditCriteria();
        } else {
            return new MenuDataEdit<>(mDataType);
        }
    }

    private ReviewViewAction.GridItemAction newGridItemAction() {
        if (mDataType == GvCommentList.GvComment.TYPE) {
            return new EditScreenComments.GridItemAddEditComments();
        } else if (mDataType == GvImageList.GvImage.TYPE) {
            return new EditScreenImages.GridItemAddEditImage();
        } else if (mDataType == GvLocationList.GvLocation.TYPE) {
            return new EditScreenLocations.GridItemEditLocation();
        } else if (mDataType == GvFactList.GvFact.TYPE) {
            return new EditScreenFacts.GridItemAddEditFact();
        } else {
            return new GridItemAddEdit<>(mDataType);
        }
    }

    private ReviewViewAction.BannerButtonAction newBannerButtonAction() {
        String title = mContext.getResources().getString(R.string.button_add);
        title += " " + mDataType.getDatumName();
        if (mDataType == GvImageList.GvImage.TYPE) {
            return new EditScreenImages.BannerButtonAddImage(title);
        } else if (mDataType == GvLocationList.GvLocation.TYPE) {
            return new EditScreenLocations.BannerButtonAddLocation(title);
        } else if (mDataType == GvFactList.GvFact.TYPE) {
            return new EditScreenFacts.BannerButtonAddFacts(title);
        } else {
            return new BannerButtonAdd<>(mDataType, title);
        }
    }

    private class RatingBar extends ReviewViewAction.RatingBarAction {
        //Overridden
        @Override
        public void onRatingChanged(android.widget.RatingBar ratingBar, float rating,
                                    boolean fromUser) {
            if (fromUser) mEditor.setRating(rating, true);
        }
    }
}
