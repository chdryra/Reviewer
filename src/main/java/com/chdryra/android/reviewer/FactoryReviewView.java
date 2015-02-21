/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 24 January, 2015
 */

package com.chdryra.android.reviewer;

import android.app.Activity;

/**
 * Created by: Rizwan Choudrey
 * On: 24/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewView {
    private FactoryReviewView() {
    }

    public static ReviewView newViewReview(FragmentReviewView parent, GvDataList.GvType dataType,
            boolean isEdit) {
        ReviewView view;
        if (dataType == GvDataList.GvType.SHARE) {
            view = newShareScreen(parent);
        } else if (dataType == GvDataList.GvType.BUILD_REVIEW) {
            view = newBuildScreen(parent);
        } else if (dataType == GvDataList.GvType.FEED) {
            view = newFeedScreen(parent);
        } else {
            view = newEditScreen(parent, dataType);
        }

        setParams(dataType, view);

        return view;
    }

    private static ReviewView newBuildScreen(FragmentReviewView parent) {
        ReviewViewAdapter adapter = getAdapter(parent, GvDataList.GvType.BUILD_REVIEW);

        ReviewView view = new ReviewView(parent, adapter, true, new BuildScreenModifier
                ((ReviewViewBuilder) adapter));

        setActions(view, GvDataList.GvType.BUILD_REVIEW, parent.getResources().getString(R.string
                .button_add_review_data));

        return view;
    }

    private static ReviewView newShareScreen(FragmentReviewView parent) {
        ReviewViewAdapter adapter = getAdapter(parent, GvDataList.GvType.SHARE);

        ReviewView view = new ReviewView(parent, adapter, false, new ShareScreenModifier());

        setActions(view, GvDataList.GvType.SHARE, parent.getResources()
                .getString(R.string.button_social));

        return view;
    }

    private static ReviewView newEditScreen(FragmentReviewView parent, GvDataList.GvType dataType) {
        ReviewViewAdapter adapter = getAdapter(parent, dataType);

        ReviewView view = new ReviewView(parent, adapter, true);

        setActions(view, dataType, parent.getResources().getString(R.string.button_add) + " " +
                dataType.getDatumString());

        return view;
    }

    private static ReviewView newFeedScreen(FragmentReviewView parent) {
        ReviewViewAdapter adapter = getAdapter(parent, GvDataList.GvType.FEED);

        ReviewView view = new ReviewView(parent, adapter, false);

        view.setAction(new FeedScreenMenu());

        return view;
    }

    private static void setActions(ReviewView view, GvDataList.GvType dataType,
            String buttonTitle) {
        view.setAction(newSubjectViewAction(dataType));
        view.setAction(newRatingBarAction(dataType));
        view.setAction(newBannerButtonAction(dataType, buttonTitle));
        view.setAction(newGridItemAction(dataType));
        view.setAction(newMenuAction(dataType));
    }

    private static ReviewViewAction.MenuAction newMenuAction(GvDataList.GvType dataType) {
        if (dataType == GvDataList.GvType.BUILD_REVIEW) {
            return new BuildScreenMenu();
        } else if (dataType == GvDataList.GvType.COMMENTS) {
            return new EditCommentsMenu();
        } else if (dataType == GvDataList.GvType.CHILDREN) {
            return new EditChildrenMenu();
        } else if (dataType == GvDataList.GvType.SHARE) {
            return new ReviewViewAction.MenuAction(dataType.getDataString(), true);
        } else {
            return new EditScreenMenu(dataType.getDataString());
        }
    }

    private static ReviewViewAction.GridItemAction newGridItemAction(GvDataList.GvType dataType) {
        if (dataType == GvDataList.GvType.COMMENTS) {
            return new EditCommentsGridItem();
        } else if (dataType == GvDataList.GvType.IMAGES) {
            return new EditImagesGridItem();
        } else if (dataType == GvDataList.GvType.BUILD_REVIEW) {
            return new BuildScreenGridItem();
        } else if (dataType == GvDataList.GvType.SHARE) {
            return new ShareScreenGridItem();
        } else {
            return new EditScreenGridItem(ConfigGvDataUi.getConfig(dataType).getEditorConfig());
        }
    }

    private static ReviewViewAction.BannerButtonAction newBannerButtonAction(
            GvDataList.GvType dataType, String title) {
        if (dataType == GvDataList.GvType.BUILD_REVIEW || dataType == GvDataList.GvType.SHARE) {
            return ReviewViewAction.BannerButtonAction.newDisplayButton(title);
        } else if (dataType == GvDataList.GvType.IMAGES) {
            return new EditImagesBannerButton(title);
        } else {
            return new EditScreenBannerButton(ConfigGvDataUi.getConfig(dataType).getAdderConfig()
                    , title);
        }
    }

    private static ReviewViewAction.RatingBarAction newRatingBarAction(GvDataList.GvType dataType) {
        if (dataType == GvDataList.GvType.BUILD_REVIEW) {
            return new BuildScreenRatingBar();
        } else if (dataType == GvDataList.GvType.SHARE) {
            return new ReviewViewAction.RatingBarAction();
        } else {
            return new EditScreenRatingBar();
        }
    }

    private static ReviewViewAction.SubjectAction newSubjectViewAction(GvDataList.GvType
            dataType) {
        if (dataType == GvDataList.GvType.BUILD_REVIEW) {
            return new SubjectEdit();
        } else {
            return new ReviewViewAction.SubjectAction();

        }
    }

    private static ReviewViewAdapter getAdapter(FragmentReviewView parent,
            GvDataList.GvType dataType) {
        Activity activity = parent.getActivity();
        Administrator admin = Administrator.get(activity);

        if (dataType == GvDataList.GvType.FEED) {
            return admin.getPublishedReviews();
        } else if (dataType == GvDataList.GvType.BUILD_REVIEW) {
            return admin.newReviewBuilder(parent.getActivity());
        } else if (dataType == GvDataList.GvType.SHARE) {
            return admin.getShareScreenAdapter();
        } else {
            return admin.getReviewBuilder().getDataBuilder(dataType);
        }
    }

    private static void setParams(GvDataList.GvType dataType, ReviewView view) {
        ReviewView.ViewReviewParams params = view.getParams();
        if (dataType == GvDataList.GvType.IMAGES) {
            params.cellHeight = ReviewView.CellDimension.HALF;
        } else if (dataType == GvDataList.GvType.FEED) {
            params.cellHeight = ReviewView.CellDimension.FULL;
            params.cellWidth = ReviewView.CellDimension.FULL;
            params.subjectIsVisibile = false;
            params.ratingIsVisibile = false;
            params.bannerButtonIsVisibile = false;
            params.gridAlpha = ReviewView.GridViewImageAlpha.TRANSPARENT;
            params.coverManager = false;
        } else if (dataType == GvDataList.GvType.BUILD_REVIEW || dataType == GvDataList.GvType
                .SHARE) {
            params.gridAlpha = ReviewView.GridViewImageAlpha.TRANSPARENT;
        }
    }
}
