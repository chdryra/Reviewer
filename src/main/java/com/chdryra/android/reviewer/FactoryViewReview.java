/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 24 January, 2015
 */

package com.chdryra.android.reviewer;

import android.app.Activity;
import android.content.Intent;

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
        } else if (dataType == GvDataList.GvType.BUILD_UI) {
            view = newBuildScreen(parent);
        } else if (dataType == GvDataList.GvType.REVIEWS) {
            view = newFeedScreen(parent);
        } else {
            view = newEditScreen(parent, dataType);
        }

        setParams(dataType, view);

        return view;
    }

    private static ViewReview newBuildScreen(FragmentViewReview parent) {
        Activity activity = parent.getActivity();
        Intent i = activity.getIntent();
        Administrator admin = Administrator.get(activity);
        ControllerReviewEditable controller = (ControllerReviewEditable) admin.unpack(i);

        GvDataList.GvType dataType = GvDataList.GvType.BUILD_UI;
        ViewReviewAction.GridItemAction gia = newGridItemEdit(controller, dataType);
        GvDataList data = GvBuildUiList.newInstance(gia);

        String details = parent.getResources().getString(R.string.button_add_review_data);
        ViewReview view = new ViewReview(parent, data,
                newCoverManager(controller),
                newSubjectEdit(controller, dataType),
                newRatingBarEdit(controller, dataType),
                ViewReviewAction.BannerButtonAction.newDisplayButton(controller, dataType, details),
                gia,
                newMenuAction(controller, dataType),
                true);

        view.setViewModifier(new ViewReviewBuildModifier(controller));

        return view;
    }

    private static ViewReview newShareScreen(FragmentViewReview parent) {
        Activity activity = parent.getActivity();
        Intent i = activity.getIntent();
        Administrator admin = Administrator.get(activity);
        ControllerReview controller = admin.unpack(i);
        GvDataList data = admin.getSocialPlatformList();

        String social = parent.getResources().getString(R.string.button_social);
        GvDataList.GvType dataType = GvDataList.GvType.SOCIAL;
        ViewReview view = new ViewReview(parent, data,
                newCoverManager(controller),
                newSubjectViewAction(controller, dataType),
                newRatingBarAction(controller, dataType),
                ViewReviewAction.BannerButtonAction.newDisplayButton(controller, dataType, social),
                newGridItemAction(controller, dataType),
                newMenuAction(controller, dataType),
                false);

        view.setViewModifier(new ViewReviewShareModifier());

        return view;
    }

    private static ViewReview newFeedScreen(FragmentViewReview parent) {
        Activity activity = parent.getActivity();
        Intent i = activity.getIntent();
        Administrator admin = Administrator.get(activity);
        ControllerReview controller = admin.unpack(i);
        GvDataList data = admin.getPublishedReviews().toGridViewable();

        CoverManager nullManager = new CoverManager() {
            @Override
            public void updateCover(FragmentViewReview fragment) {

            }

            @Override
            public void proposeCover(GvImageList.GvImage image) {

            }
        };

        String social = parent.getResources().getString(R.string.button_social);
        GvDataList.GvType dataType = GvDataList.GvType.REVIEWS;
        return new ViewReview(parent, data, nullManager,
                newSubjectViewAction(controller, dataType),
                newRatingBarAction(controller, dataType),
                ViewReviewAction.BannerButtonAction.newDisplayButton(controller, dataType, social),
                newGridItemAction(controller, dataType),
                newMenuAction(controller, dataType),
                false);
    }

    private static ViewReview newEditScreen(FragmentViewReview parent,
            GvDataList.GvType dataType) {
        Activity activity = parent.getActivity();
        Intent i = activity.getIntent();
        Administrator admin = Administrator.get(activity);
        ControllerReviewEditable controller = (ControllerReviewEditable) admin.unpack(i);

        GvDataList data = controller.getData(dataType);
        CoverManager coverManager = dataType == GvDataList.GvType.IMAGES ?
                newCoverManager((GvImageList) data) : newCoverManager(controller);

        return new ViewReview(parent, data, coverManager,
                newSubjectEdit(controller, dataType),
                newRatingBarEdit(controller, dataType),
                newBannerButtonAdd(controller, dataType),
                newGridItemEdit(controller, dataType),
                newMenuEdit(controller, dataType), true);
    }

    private static ViewReviewAction.MenuAction newMenuAction(ControllerReview controller,
            GvDataList.GvType dataType) {
        if (dataType == GvDataList.GvType.REVIEWS) {
            return new MenuFeed(controller);
        } else if (dataType == GvDataList.GvType.BUILD_UI) {
            return new MenuBuildUi(controller);
        } else {
            return new ViewReviewAction.MenuAction(controller, dataType, true);
        }
    }

    private static ViewReviewAction.MenuAction newMenuEdit(ControllerReviewEditable controller,
            GvDataList.GvType dataType) {
        if (dataType == GvDataList.GvType.COMMENTS) {
            return new MenuEditComments(controller);
        } else if (dataType == GvDataList.GvType.CHILDREN) {
            return new MenuEditChildren(controller);
        } else {
            return new MenuDeleteDone(controller, dataType);
        }
    }

    private static ViewReviewAction.GridItemAction newGridItemAction(ControllerReview controller,
            GvDataList.GvType dataType) {
        if (dataType == GvDataList.GvType.SOCIAL) {
            return new GridItemSocial(controller);
        } else {
            return new ViewReviewAction.GridItemAction(controller, dataType);
        }
    }

    private static ViewReviewAction.GridItemAction newGridItemEdit(ControllerReviewEditable
            controller,
            GvDataList.GvType dataType) {
        if (dataType == GvDataList.GvType.COMMENTS) {
            return new GridItemEditComment(controller);
        } else if (dataType == GvDataList.GvType.IMAGES) {
            return new GridItemEditImage(controller);
        } else if (dataType == GvDataList.GvType.BUILD_UI) {
            return new GridItemBuildUi(controller);
        } else {
            return new GridItemEdit(controller, dataType);
        }
    }

    private static ViewReviewAction.BannerButtonAction newBannerButtonAdd(ControllerReviewEditable
            controller,
            GvDataList.GvType dataType) {
        if (dataType == GvDataList.GvType.IMAGES) {
            return new BannerButtonAddImage(controller);
        } else {
            return new BannerButtonAdd(controller, dataType);
        }
    }

    private static ViewReviewAction.RatingBarAction newRatingBarAction(ControllerReview controller,
            GvDataList.GvType dataType) {
        return new ViewReviewAction.RatingBarAction(controller, dataType);
    }

    private static ViewReviewAction.RatingBarAction newRatingBarEdit(ControllerReviewEditable
            controller,
            GvDataList.GvType dataType) {
        if (dataType == GvDataList.GvType.CHILDREN) {
            return new RatingEditChildren(controller);
        } else {
            return new RatingEdit(controller, dataType);
        }
    }

    private static ViewReviewAction.SubjectViewAction newSubjectViewAction(ControllerReview
            controller,
            GvDataList.GvType dataType) {
        return new ViewReviewAction.SubjectViewAction(controller, dataType);
    }

    private static ViewReviewAction.SubjectViewAction newSubjectEdit(ControllerReviewEditable
            controller,
            GvDataList.GvType dataType) {
        return new SubjectEdit(controller, dataType);
    }

    private static CoverManager newCoverManager(ControllerReview controller) {
        return new CoverManagerController(controller);
    }

    private static CoverManager newCoverManager(GvImageList images) {
        return new CoverManagerImageList(images);
    }

    private static void setParams(GvDataList.GvType dataType, ViewReview view) {
        ViewReview.ViewReviewParams params = view.getParams();
        if (dataType == GvDataList.GvType.IMAGES) {
            params.cellHeight = ViewReview.CellDimension.HALF;
        } else if (dataType == GvDataList.GvType.REVIEWS) {
            params.cellHeight = ViewReview.CellDimension.FULL;
            params.cellWidth = ViewReview.CellDimension.FULL;
            params.subjectIsVisibile = false;
            params.ratingIsVisibile = false;
            params.bannerButtonIsVisibile = false;
            params.gridAlpha = ViewReview.GridViewImageAlpha.TRANSPARENT;
        }
        if (dataType == GvDataList.GvType.BUILD_UI || dataType == GvDataList.GvType.SOCIAL) {
            params.gridAlpha = ViewReview.GridViewImageAlpha.TRANSPARENT;
        }
    }
}
