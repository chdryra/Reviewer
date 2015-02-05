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
        Intent i = activity.getIntent();
        Administrator admin = Administrator.get(activity);

        ControllerReviewEditable controller = (ControllerReviewEditable) admin.unpack(i);
        GvDataList.GvType dataType = GvDataList.GvType.BUILD_UI;

        ViewReviewAction.GridItemAction gia = newGridItemEdit(controller, dataType);
        GvDataList data = GvBuildUiList.newInstance(gia);
        ViewReview view = new ViewReview(parent, data, true, new ViewReviewBuildModifier
                (controller));

        view.setAction(newSubjectEdit(controller));
        view.setAction(newRatingBarEdit(controller, dataType));
        view.setAction(ViewReviewAction.BannerButtonAction.newDisplayButton(controller, dataType,
                parent.getResources().getString(R.string.button_add_review_data)));
        view.setAction(gia);
        view.setAction(newMenuAction(controller, dataType));

        view.setCoverManager(newCoverManager(controller));

        return view;
    }

    private static ViewReview newShareScreen(FragmentViewReview parent) {
        Activity activity = parent.getActivity();
        Intent i = activity.getIntent();
        Administrator admin = Administrator.get(activity);

        GvDataList data = admin.getSocialPlatformList();

        ViewReview view = new ViewReview(parent, data, true, new ViewReviewShareModifier());

        ControllerReview controller = admin.unpack(i);
        GvDataList.GvType dataType = GvDataList.GvType.SOCIAL;

        view.setAction(newSubjectViewAction(controller));
        view.setAction(newRatingBarAction(controller));
        view.setAction(ViewReviewAction.BannerButtonAction.newDisplayButton(controller, dataType,
                parent.getResources().getString(R.string.button_social)));
        view.setAction(newGridItemAction(controller, dataType));
        view.setAction(newMenuAction(controller, dataType));

        view.setCoverManager(newCoverManager(controller));

        return view;
    }

    private static ViewReview newFeedScreen(FragmentViewReview parent) {
        Administrator admin = Administrator.get(parent.getActivity());
        GvDataList data = admin.getPublishedReviews().toGridViewable();

        ViewReview view = new ViewReview(parent, data, false);
        view.setAction(new MenuFeed());

        return view;
    }

    private static ViewReview newEditScreen(FragmentViewReview parent, GvDataList.GvType dataType) {
        Activity activity = parent.getActivity();
        Intent i = activity.getIntent();
        Administrator admin = Administrator.get(activity);

        ControllerReviewEditable controller = (ControllerReviewEditable) admin.unpack(i);
        GvDataList data = controller.getData(dataType);

        ViewReview view = new ViewReview(parent, data, true);

        view.setAction(newSubjectViewAction(controller));
        view.setAction(newRatingBarAction(controller));
        view.setAction(newBannerButtonAdd(controller, dataType));
        view.setAction(newGridItemEdit(controller, dataType));
        view.setAction(newMenuEdit(controller, dataType));

        CoverManager coverManager = dataType == GvDataList.GvType.IMAGES ?
                newCoverManager((GvImageList) data) : newCoverManager(controller);
        view.setCoverManager(coverManager);

        return view;
    }

    private static ViewReviewAction.MenuAction newMenuAction(ControllerReview controller,
            GvDataList.GvType dataType) {
        if (dataType == GvDataList.GvType.BUILD_UI) {
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

    private static ViewReviewAction.RatingBarAction newRatingBarAction(ControllerReview
            controller) {
        return new ViewReviewAction.RatingBarAction(controller);
    }

    private static ViewReviewAction.RatingBarAction newRatingBarEdit(ControllerReviewEditable
            controller,
            GvDataList.GvType dataType) {
        if (dataType == GvDataList.GvType.CHILDREN) {
            return new RatingEditChildren(controller);
        } else {
            return new RatingEdit(controller);
        }
    }

    private static ViewReviewAction.SubjectViewAction newSubjectViewAction(ControllerReview
            controller) {
        return new ViewReviewAction.SubjectViewAction(controller);
    }

    private static ViewReviewAction.SubjectViewAction newSubjectEdit(ControllerReviewEditable
            controller) {
        return new SubjectEdit(controller);
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
        } else if (dataType == GvDataList.GvType.FEED) {
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
