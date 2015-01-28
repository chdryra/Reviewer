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
    public static FactoryViewReview sFactory;

    private FactoryViewReview() {
    }

    private static FactoryViewReview get() {
        if (sFactory == null) sFactory = new FactoryViewReview();
        return sFactory;
    }

    public static ViewReview newViewReview(FragmentViewReview parent, GvDataList.GvType dataType,
            boolean isEdit) {
        if (dataType == GvDataList.GvType.SOCIAL) {
            return newViewReviewShare(parent);
        } else {
            return get().newViewReviewEdit(parent, dataType);
        }
    }

    public static ViewReview newViewReviewShare(FragmentViewReview parent) {
        return get().newShareView(parent);
    }

    private ViewReview newShareView(FragmentViewReview parent) {
        Activity activity = parent.getActivity();
        Intent i = activity.getIntent();
        Administrator admin = Administrator.get(activity);
        ControllerReview controller = admin.unpack(i);
        GvDataList data = admin.getSocialPlatformList();
        GvImageList.GvImage cover = controller.getRandomCover();

        final String social = parent.getResources().getString(R.string.button_social);
        ViewReviewAction.BannerButtonAction bba = new ViewReviewAction.BannerButtonAction
                (controller, GvDataList.GvType.SOCIAL) {
            @Override
            public String getButtonTitle() {
                return social;
            }
        };

        GvDataList.GvType dataType = GvDataList.GvType.SOCIAL;
        ViewReview view = new ViewReview(parent, data, cover,
                newSubjectViewAction(controller, dataType),
                newRatingBarAction(controller, dataType),
                bba,
                newGridItemAction(controller, dataType),
                newMenuAction(controller, dataType),
                false);

        view.setViewModifier(new ViewReviewShareModifier());

        return view;
    }

    private ViewReview newViewReviewEdit(FragmentViewReview parent, GvDataList.GvType dataType) {
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

    private ViewReviewAction.MenuAction newMenuAction(ControllerReview controller,
            GvDataList.GvType dataType) {
        return new ViewReviewAction.MenuAction(controller, dataType, true);
    }

    private ViewReviewAction.MenuAction newMenuEdit(ControllerReviewEditable controller,
            GvDataList.GvType dataType) {
        if (dataType == GvDataList.GvType.COMMENTS) {
            return new MenuEditComments(controller);
        } else if (dataType == GvDataList.GvType.CHILDREN) {
            return new MenuEditChildren(controller);
        } else {
            return new MenuDeleteDone(controller, dataType);
        }
    }

    private ViewReviewAction.GridItemAction newGridItemAction(ControllerReview controller,
            GvDataList.GvType dataType) {
        if (dataType == GvDataList.GvType.SOCIAL) {
            return new GridItemSocial(controller);
        } else {
            return new ViewReviewAction.GridItemAction(controller, dataType);
        }
    }

    private ViewReviewAction.GridItemAction newGridItemEdit(ControllerReviewEditable
            controller,
            GvDataList.GvType dataType) {
        if (dataType == GvDataList.GvType.COMMENTS) {
            return new GridItemEditComment(controller);
        } else if (dataType == GvDataList.GvType.IMAGES) {
            return new GridItemEditImage(controller);
        } else {
            return new GridItemEdit(controller, dataType);
        }
    }

    private ViewReviewAction.BannerButtonAction newBannerButtonAdd(ControllerReviewEditable
            controller,
            GvDataList.GvType dataType) {
        if (dataType == GvDataList.GvType.IMAGES) {
            return new BannerButtonAddImage(controller);
        } else {
            return new BannerButtonAdd(controller, dataType);
        }
    }

    private ViewReviewAction.RatingBarAction newRatingBarAction(ControllerReview controller,
            GvDataList.GvType dataType) {
        return new ViewReviewAction.RatingBarAction(controller, dataType);
    }

    private ViewReviewAction.RatingBarAction newRatingBarEdit(ControllerReviewEditable controller,
            GvDataList.GvType dataType) {
        if (dataType == GvDataList.GvType.CHILDREN) {
            return new RatingEditChildren(controller);
        } else {
            return new RatingEdit(controller, dataType);
        }
    }

    private ViewReviewAction.SubjectViewAction newSubjectViewAction(ControllerReview controller,
            GvDataList.GvType dataType) {
        return new ViewReviewAction.SubjectViewAction(controller, dataType);
    }

    private ViewReviewAction.SubjectViewAction newSubjectEdit(ControllerReviewEditable controller,
            GvDataList.GvType dataType) {
        return new SubjectEdit(controller, dataType);
    }
}
