/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 24 January, 2015
 */

package com.chdryra.android.reviewer;

import android.content.Context;

/**
 * Created by: Rizwan Choudrey
 * On: 24/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewView {
    private FactoryReviewView() {
    }

    public static ReviewView newBuildScreen(Context context) {
        ReviewBuilder builder = Administrator.get(context).getReviewBuilder();

        ReviewView view = new ReviewView.Editor(builder, new BuildScreenModifier(builder));

        String screenTitle = context.getResources().getString(R.string.screen_title_build_review);
        String buttonTitle = context.getResources().getString(R.string.button_add_review_data);
        view.setAction(new SubjectEdit());
        view.setAction(new BuildScreenRatingBar());
        view.setAction(ReviewViewAction.BannerButtonAction.newDisplayButton(buttonTitle));
        view.setAction(new BuildScreenGridItem());
        view.setAction(new BuildScreenMenu(screenTitle));

        view.getParams().gridAlpha = ReviewView.GridViewImageAlpha.TRANSPARENT;

        return view;
    }

    public static ReviewView newShareScreen(Context context) {
        ReviewViewAdapter adapter = Administrator.get(context).getShareScreenAdapter();

        ReviewView view = new ReviewView(adapter, new ShareScreenModifier());

        String title = context.getResources().getString(R.string.button_social);
        view.setAction(ReviewViewAction.BannerButtonAction.newDisplayButton(title));
        view.setAction(new ShareScreenGridItem());
        view.setAction(new ReviewViewAction.MenuAction(title, true));

        view.getParams().gridAlpha = ReviewView.GridViewImageAlpha.TRANSPARENT;

        return view;
    }

    public static ReviewView newFeedScreen(Context context) {
        ReviewView view = new ReviewView(Administrator.get(context).getPublishedReviews());

        view.setAction(new FeedScreenMenu());

        ReviewView.ReviewViewParams params = view.getParams();
        params.cellHeight = ReviewView.CellDimension.FULL;
        params.cellWidth = ReviewView.CellDimension.FULL;
        params.subjectIsVisible = false;
        params.ratingIsVisible = false;
        params.bannerButtonIsVisible = false;
        params.gridAlpha = ReviewView.GridViewImageAlpha.TRANSPARENT;
        params.coverManager = false;

        return view;
    }

    public static ReviewView newEditScreen(Context context, GvDataList.GvDataType dataType) {
        ReviewBuilder.DataBuilder builder = Administrator.get(context).getReviewBuilder()
                .getDataBuilder(dataType);

        ReviewView view = new ReviewView.Editor(builder);

        setActions(view, dataType, context.getResources().getString(R.string.button_add) + " " +
                dataType.getDatumName());

        if (dataType == GvImageList.TYPE) {
            view.getParams().cellHeight = ReviewView.CellDimension.HALF;
        }

        return view;
    }

    private static void setActions(ReviewView view, GvDataList.GvDataType dataType,
            String buttonTitle) {
        view.setAction(new EditScreenRatingBar());
        view.setAction(newBannerButtonAction(dataType, buttonTitle));
        view.setAction(newGridItemAction(dataType));
        view.setAction(newMenuAction(dataType));
    }

    private static ReviewViewAction.MenuAction newMenuAction(GvDataList.GvDataType dataType) {
        if (dataType == GvCommentList.TYPE) {
            return new EditCommentsMenu();
        } else if (dataType == GvChildList.TYPE) {
            return new EditChildrenMenu();
        } else {
            return new EditScreenMenu(dataType.getDataName());
        }
    }

    private static ReviewViewAction.GridItemAction newGridItemAction(GvDataList.GvDataType
            dataType) {
        if (dataType == GvCommentList.TYPE) {
            return new EditCommentsGridItem();
        } else if (dataType == GvImageList.TYPE) {
            return new EditImagesGridItem();
        } else {
            return new EditScreenGridItem(ConfigGvDataUi.getConfig(dataType).getEditorConfig());
        }
    }

    private static ReviewViewAction.BannerButtonAction newBannerButtonAction(
            GvDataList.GvDataType dataType, String title) {
        if (dataType == GvImageList.TYPE) {
            return new EditImagesBannerButton(title);
        } else if (dataType == GvCommentList.TYPE) {
            return new EditCommentsBannerButton(title);
        } else {
            return new EditScreenBannerButton(ConfigGvDataUi.getConfig(dataType).getAdderConfig()
                    , title);
        }
    }
}
