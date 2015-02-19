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
public class FactoryViewReview {
    private FactoryViewReview() {
    }

    public static ViewReview newViewReview(FragmentViewReview parent, GvDataList.GvType dataType,
            boolean isEdit) {
        ViewReview view;
        if (dataType == GvDataList.GvType.SOCIAL) {
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

    private static ViewReview newBuildScreen(FragmentViewReview parent) {
        Activity activity = parent.getActivity();
        Administrator admin = Administrator.get(activity);

        ReviewBuilder builder = admin.getNewReviewBuilder(activity);

        ViewReview view = new ViewReview(parent, builder, true, new ViewReviewBuildModifier
                (builder));

        GvDataList.GvType dataType = GvDataList.GvType.BUILD_REVIEW;

        view.setAction(newSubjectEdit());
        view.setAction(newRatingBarEdit(dataType));
        view.setAction(ViewReviewAction.BannerButtonAction.newDisplayButton(
                parent.getResources().getString(R.string.button_add_review_data)));
        view.setAction(newGridItemEdit(dataType));
        view.setAction(newMenuAction(dataType));

        view.setCoverManager(newCoverManager(builder));

        return view;
    }

    private static ViewReview newShareScreen(FragmentViewReview parent) {
        Activity activity = parent.getActivity();
        Administrator admin = Administrator.get(activity);

        ViewReviewAdapter adapter = admin.getShareScreenAdapter();
        ViewReview view = new ViewReview(parent, adapter, true, new ViewReviewShareModifier());

        GvDataList.GvType dataType = GvDataList.GvType.SOCIAL;
        view.setAction(newSubjectViewAction());
        view.setAction(newRatingBarAction());
        view.setAction(ViewReviewAction.BannerButtonAction.newDisplayButton(parent.getResources()
                .getString(R.string.button_social)));
        view.setAction(newGridItemAction(dataType));
        view.setAction(newMenuAction(dataType));

        view.setCoverManager(newCoverManager(adapter));

        return view;
    }

    private static ViewReview newFeedScreen(FragmentViewReview parent) {
        Administrator admin = Administrator.get(parent.getActivity());

        ViewReview view = new ViewReview(parent, admin.getPublishedReviews(), false);
        view.setAction(new MenuFeed());

        return view;
    }

    private static ViewReview newEditScreen(FragmentViewReview parent, GvDataList.GvType dataType) {
        Activity activity = parent.getActivity();
        Administrator admin = Administrator.get(activity);

        ViewReviewAdapter adapter = admin.getReviewBuilder().getDataAdapter(dataType);
        ViewReview view = new ViewReview(parent, adapter, true);

        String buttonTitle = parent.getResources().getString(R.string.button_add) + " " + dataType
                .getDatumString();
        view.setAction(newSubjectViewAction());
        view.setAction(newRatingBarEdit(dataType));
        view.setAction(newBannerButtonAdd(dataType, buttonTitle));
        view.setAction(newGridItemEdit(dataType));
        view.setAction(newMenuEdit(dataType));

        CoverManager coverManager = newCoverManager(adapter);
        view.setCoverManager(coverManager);

        return view;
    }

    private static ViewReviewAction.MenuAction newMenuAction(GvDataList.GvType dataType) {
        if (dataType == GvDataList.GvType.BUILD_REVIEW) {
            return new MenuBuildReview();
        } else {
            return new ViewReviewAction.MenuAction(dataType.getDataString(), true);
        }
    }

    private static ViewReviewAction.MenuAction newMenuEdit(GvDataList.GvType dataType) {
        if (dataType == GvDataList.GvType.COMMENTS) {
            return new MenuEditComments();
        } else if (dataType == GvDataList.GvType.CHILDREN) {
            return new MenuEditChildren();
        } else {
            return new MenuDeleteDone(dataType.getDataString());
        }
    }

    private static ViewReviewAction.GridItemAction newGridItemAction(GvDataList.GvType dataType) {
        if (dataType == GvDataList.GvType.SOCIAL) {
            return new GridItemSocial();
        } else {
            return new ViewReviewAction.GridItemAction();
        }
    }

    private static ViewReviewAction.GridItemAction newGridItemEdit(GvDataList.GvType dataType) {
        if (dataType == GvDataList.GvType.COMMENTS) {
            return new GridItemEditComment();
        } else if (dataType == GvDataList.GvType.IMAGES) {
            return new GridItemEditImage();
        } else if (dataType == GvDataList.GvType.BUILD_REVIEW) {
            return new GridItemBuildReview();
        } else {
            return new GridItemEdit(ConfigGvDataUi.getConfig(dataType).getEditorConfig());
        }
    }

    private static ViewReviewAction.BannerButtonAction newBannerButtonAdd(
            GvDataList.GvType dataType, String title) {
        ConfigGvDataUi.LaunchableConfig config = ConfigGvDataUi.getConfig(dataType).getAdderConfig();
        if (dataType == GvDataList.GvType.IMAGES) {
            return new BannerButtonAddImage(title);
        } else {
            return new BannerButtonAdd(config, title);
        }
    }

    private static ViewReviewAction.RatingBarAction newRatingBarAction() {
        return new ViewReviewAction.RatingBarAction();
    }

    private static ViewReviewAction.RatingBarAction newRatingBarEdit(GvDataList.GvType dataType) {
        if (dataType == GvDataList.GvType.BUILD_REVIEW) {
            return new RatingEditBuildReview();
        } else {
            return new RatingEdit();
        }
    }

    private static ViewReviewAction.SubjectViewAction newSubjectViewAction() {
        return new ViewReviewAction.SubjectViewAction();
    }

    private static ViewReviewAction.SubjectViewAction newSubjectEdit() {
        return new SubjectEdit();
    }

    private static CoverManager newCoverManager(ViewReviewAdapter adapter) {
        return new CoverManager(adapter);
    }

    private static void setParams(GvDataList.GvType dataType, ViewReview view) {
        ViewReview.ViewReviewParams params = view.getParams();
        if (dataType == GvDataList.GvType.IMAGES) {
            params.cellHeight = ViewReview.CellDimension.HALF;
        } else if (dataType == GvDataList.GvType.FEED) {
            params.cellHeight = ViewReview.CellDimension.FULL;
            params.cellWidth = ViewReview.CellDimension.FULL;
            params.subjectIsVisibile = false;
            params.ratingIsVisibile = false;
            params.bannerButtonIsVisibile = false;
            params.gridAlpha = ViewReview.GridViewImageAlpha.TRANSPARENT;
        }
        if (dataType == GvDataList.GvType.BUILD_REVIEW || dataType == GvDataList.GvType.SOCIAL) {
            params.gridAlpha = ViewReview.GridViewImageAlpha.TRANSPARENT;
        }
    }
}
