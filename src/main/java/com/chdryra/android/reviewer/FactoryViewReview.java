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

    private static ViewReview newBuildScreen(FragmentViewReview parent) {
        ViewReviewAdapter adapter = getAdapter(parent, GvDataList.GvType.BUILD_REVIEW);

        ViewReview view = new ViewReview(parent, adapter, true, new ViewReviewBuildModifier
                ((ReviewBuilder) adapter));

        setActions(view, GvDataList.GvType.BUILD_REVIEW, parent.getResources().getString(R.string
                .button_add_review_data));

        return view;
    }

    private static ViewReview newShareScreen(FragmentViewReview parent) {
        ViewReviewAdapter adapter = getAdapter(parent, GvDataList.GvType.SHARE);

        ViewReview view = new ViewReview(parent, adapter, false, new ViewReviewShareModifier());

        setActions(view, GvDataList.GvType.SHARE, parent.getResources()
                .getString(R.string.button_social));

        return view;
    }

    private static ViewReview newEditScreen(FragmentViewReview parent, GvDataList.GvType dataType) {
        ViewReviewAdapter adapter = getAdapter(parent, dataType);

        ViewReview view = new ViewReview(parent, adapter, true);

        setActions(view, dataType, parent.getResources().getString(R.string.button_add) + " " +
                dataType.getDatumString());

        return view;
    }

    private static ViewReview newFeedScreen(FragmentViewReview parent) {
        ViewReviewAdapter adapter = getAdapter(parent, GvDataList.GvType.FEED);

        ViewReview view = new ViewReview(parent, adapter, false);

        view.setAction(new MenuFeed());

        return view;
    }

    private static void setActions(ViewReview view, GvDataList.GvType dataType,
            String buttonTitle) {
        view.setAction(newSubjectViewAction(dataType));
        view.setAction(newRatingBarAction(dataType));
        view.setAction(newBannerButtonAction(dataType, buttonTitle));
        view.setAction(newGridItemAction(dataType));
        view.setAction(newMenuAction(dataType));
    }

    private static ViewReviewAction.MenuAction newMenuAction(GvDataList.GvType dataType) {
        if (dataType == GvDataList.GvType.BUILD_REVIEW) {
            return new MenuBuildReview();
        } else if (dataType == GvDataList.GvType.COMMENTS) {
            return new MenuEditComments();
        } else if (dataType == GvDataList.GvType.CHILDREN) {
            return new MenuEditChildren();
        } else if (dataType == GvDataList.GvType.SHARE) {
            return new ViewReviewAction.MenuAction(dataType.getDataString(), true);
        } else {
            return new MenuDeleteDone(dataType.getDataString());
        }
    }

    private static ViewReviewAction.GridItemAction newGridItemAction(GvDataList.GvType dataType) {
        if (dataType == GvDataList.GvType.COMMENTS) {
            return new GridItemEditComment();
        } else if (dataType == GvDataList.GvType.IMAGES) {
            return new GridItemEditImage();
        } else if (dataType == GvDataList.GvType.BUILD_REVIEW) {
            return new GridItemBuildReview();
        } else if (dataType == GvDataList.GvType.SHARE) {
            return new GridItemSocial();
        } else {
            return new GridItemEdit(ConfigGvDataUi.getConfig(dataType).getEditorConfig());
        }
    }

    private static ViewReviewAction.BannerButtonAction newBannerButtonAction(
            GvDataList.GvType dataType, String title) {
        if (dataType == GvDataList.GvType.BUILD_REVIEW || dataType == GvDataList.GvType.SHARE) {
            return ViewReviewAction.BannerButtonAction.newDisplayButton(title);
        } else if (dataType == GvDataList.GvType.IMAGES) {
            return new BannerButtonAddImage(title);
        } else {
            return new BannerButtonAdd(ConfigGvDataUi.getConfig(dataType).getAdderConfig(), title);
        }
    }

    private static ViewReviewAction.RatingBarAction newRatingBarAction(GvDataList.GvType dataType) {
        if (dataType == GvDataList.GvType.BUILD_REVIEW) {
            return new RatingEditBuildReview();
        } else if (dataType == GvDataList.GvType.SHARE) {
            return new ViewReviewAction.RatingBarAction();
        } else {
            return new RatingEdit();
        }
    }

    private static ViewReviewAction.SubjectViewAction newSubjectViewAction(GvDataList.GvType
            dataType) {
        if (dataType == GvDataList.GvType.BUILD_REVIEW) {
            return new SubjectEdit();
        } else {
            return new ViewReviewAction.SubjectViewAction();

        }
    }

    private static ViewReviewAdapter getAdapter(FragmentViewReview parent,
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
            params.coverManager = false;
        } else if (dataType == GvDataList.GvType.BUILD_REVIEW || dataType == GvDataList.GvType
                .SHARE) {
            params.gridAlpha = ViewReview.GridViewImageAlpha.TRANSPARENT;
        }
    }
}
