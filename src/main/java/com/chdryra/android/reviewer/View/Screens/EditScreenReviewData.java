/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 24 January, 2015
 */

package com.chdryra.android.reviewer.View.Screens;

import android.content.Context;
import android.widget.RatingBar;

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
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;

/**
 * Created by: Rizwan Choudrey
 * On: 24/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class EditScreenReviewData<T extends GvData> {
    private Context mContext;
    private ReviewDataEditor<T> mEditor;
    private GvDataType<T> mDataType;

    protected EditScreenReviewData(Context context, GvDataType<T> dataType) {
        mContext = context;
        mDataType = dataType;

        //Adapter
        ReviewBuilderAdapter builder = Administrator.get(context).getReviewBuilder();
        ReviewBuilderAdapter.DataBuilderAdapter<T> adapter = builder.getDataBuilder(mDataType);

        //Parameters
        ReviewViewParams params = DefaultParameters.getParams(mDataType);

        //Actions
        ReviewViewActions actions = new ReviewViewActions();
        actions.setAction(newMenuAction());
        actions.setAction(newSubjectAction());
        actions.setAction(newRatingBarAction());
        actions.setAction(newBannerButtonAction());
        actions.setAction(newGridItemAction());

        mEditor = new ReviewDataEditor<>(adapter, params, actions);
    }

    //Static methods
    public static <T extends GvData> ReviewView newScreen(Context context, GvDataType<T> dataType) {
        EditScreenReviewData screen;
        if (dataType == GvCommentList.GvComment.TYPE) {
            screen = new EditScreenComments(context);
        } else if (dataType == GvCriterionList.GvCriterion.TYPE) {
            screen = new EditScreenCriteria(context);
        } else if (dataType == GvFactList.GvFact.TYPE) {
            screen = new EditScreenFacts(context);
        } else if (dataType == GvImageList.GvImage.TYPE) {
            screen = new EditScreenImages(context);
        } else if (dataType == GvLocationList.GvLocation.TYPE) {
            screen = new EditScreenLocations(context);
        } else if (dataType == GvTagList.GvTag.TYPE) {
            screen = new EditScreenTags(context);
        } else {
            screen = new EditScreenReviewData<>(context, dataType);
        }

        return screen.getEditor();
    }

    //protected methods
    protected String getBannerButtonTitle() {
        String title = mContext.getResources().getString(R.string.button_add);
        title += " " + mDataType.getDatumName();
        return title;
    }

    protected ReviewViewAction.SubjectAction newSubjectAction() {
        return new SubjectEdit<>(mDataType);
    }

    protected ReviewViewAction.RatingBarAction newRatingBarAction() {
        return new RatingBarEdit();
    }

    protected ReviewViewAction.MenuAction newMenuAction() {
        return new MenuDataEdit<>(mDataType);
    }

    protected ReviewViewAction.GridItemAction newGridItemAction() {
        return new GridItemAddEdit<>(mDataType);
    }

    protected ReviewViewAction.BannerButtonAction newBannerButtonAction() {
        return new BannerButtonAdd<>(mDataType, getBannerButtonTitle());
    }

    //private methods
    private ReviewView getEditor() {
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
