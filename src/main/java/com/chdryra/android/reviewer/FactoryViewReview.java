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
        if (dataType == GvDataList.GvType.SOCIAL) {
            return newShareScreen(parent);
        } else if (dataType == GvDataList.GvType.REVIEW) {
            return newBuildScreen(parent);
        } else {
            return newEditScreen(parent, dataType);
        }
    }

    public static ViewReview newBuildScreen(FragmentViewReview parent) {
        Activity activity = parent.getActivity();
        Intent i = activity.getIntent();
        Administrator admin = Administrator.get(activity);
        ControllerReviewEditable controller = (ControllerReviewEditable) admin.unpack(i);

        GvDataList.GvType dataType = GvDataList.GvType.REVIEW;
        ViewReviewAction.GridItemAction gia = newGridItemEdit(controller, dataType);
        GvDataList data = GvBuildReviewCellList.newInstance(gia);
        GvImageList.GvImage cover = controller.getRandomCover();

        String details = parent.getResources().getString(R.string.button_add_review_data);
        ViewReview view = new ViewReview(parent, data, cover,
                newSubjectEdit(controller, dataType),
                newRatingBarEdit(controller, dataType),
                ViewReviewAction.BannerButtonAction.newDisplayButton(controller, dataType, details),
                gia,
                newMenuAction(controller, dataType),
                true);

        view.setViewModifier(new ViewReviewBuildModifier(controller));
        view.setTransparentGridCellBackground();

        return view;
    }

    public static ViewReview newShareScreen(FragmentViewReview parent) {
        Activity activity = parent.getActivity();
        Intent i = activity.getIntent();
        Administrator admin = Administrator.get(activity);
        ControllerReview controller = admin.unpack(i);
        GvDataList data = admin.getSocialPlatformList();
        GvImageList.GvImage cover = controller.getRandomCover();

        String social = parent.getResources().getString(R.string.button_social);
        GvDataList.GvType dataType = GvDataList.GvType.SOCIAL;
        ViewReview view = new ViewReview(parent, data, cover,
                newSubjectViewAction(controller, dataType),
                newRatingBarAction(controller, dataType),
                ViewReviewAction.BannerButtonAction.newDisplayButton(controller, dataType, social),
                newGridItemAction(controller, dataType),
                newMenuAction(controller, dataType),
                false);

        view.setViewModifier(new ViewReviewShareModifier());
        view.setTransparentGridCellBackground();

        return view;
    }

    private static ViewReview newEditScreen(FragmentViewReview parent,
            GvDataList.GvType dataType) {
        Activity activity = parent.getActivity();
        Intent i = activity.getIntent();
        Administrator admin = Administrator.get(activity);
        ControllerReviewEditable controller = (ControllerReviewEditable) admin.unpack(i);

        GvDataList data = controller.getData(dataType);
        GvImageList.GvImage cover = controller.getRandomCover();

        return new ViewReview(parent, data, cover,
                newSubjectEdit(controller, dataType),
                newRatingBarEdit(controller, dataType),
                newBannerButtonAdd(controller, dataType),
                newGridItemEdit(controller, dataType),
                newMenuEdit(controller, dataType), true);
    }

    private static ViewReviewAction.MenuAction newMenuAction(ControllerReview controller,
            GvDataList.GvType dataType) {
        if (dataType == GvDataList.GvType.REVIEW) {
            return new MenuBuildScreen(controller);
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
        }
        if (dataType == GvDataList.GvType.REVIEW) {
            return new GridItemEditReview(controller);
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
}
