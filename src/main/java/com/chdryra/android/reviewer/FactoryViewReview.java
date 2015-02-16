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
        Intent i = activity.getIntent();
        Administrator admin = Administrator.get(activity);

        ReviewBuilder controller = admin.getReviewBuilder();

        GvDataList data = GvBuildReviewList.newInstance(activity, controller);
        ViewReview view = new ViewReview(parent, data, true, new ViewReviewBuildModifier
                (controller));

        GvDataList.GvType dataType = GvDataList.GvType.BUILD_REVIEW;

        view.setAction(newSubjectEdit(controller));
        view.setAction(newRatingBarEdit(controller, dataType));
        view.setAction(ViewReviewAction.BannerButtonAction.newDisplayButton(controller, dataType,
                parent.getResources().getString(R.string.button_add_review_data)));
        view.setAction(newGridItemEdit(controller, dataType));
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

        GvAdapter adapter = admin.unpack(i);
        GvDataList.GvType dataType = GvDataList.GvType.SOCIAL;

        view.setAction(newSubjectViewAction(adapter));
        view.setAction(newRatingBarAction(adapter));
        view.setAction(ViewReviewAction.BannerButtonAction.newDisplayButton(adapter, dataType,
                parent.getResources().getString(R.string.button_social)));
        view.setAction(newGridItemAction(adapter, dataType));
        view.setAction(newMenuAction(adapter, dataType));

        view.setCoverManager(newCoverManager(adapter));

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

        ReviewBuilder controller = admin.getReviewBuilder();
        GvDataList data = controller.getData(dataType);

        ViewReview view = new ViewReview(parent, data, true);

        view.setAction(newSubjectViewAction(controller));
        view.setAction(newRatingBarEdit(controller, dataType));
        view.setAction(newBannerButtonAdd(controller, dataType));
        view.setAction(newGridItemEdit(controller, dataType));
        view.setAction(newMenuEdit(controller, dataType));

        CoverManager coverManager = dataType == GvDataList.GvType.IMAGES ?
                newCoverManager((GvImageList) data) : newCoverManager(controller);
        view.setCoverManager(coverManager);

        return view;
    }

    private static ViewReviewAction.MenuAction newMenuAction(GvAdapter adapter,
            GvDataList.GvType dataType) {
        if (dataType == GvDataList.GvType.BUILD_REVIEW) {
            return new MenuBuildReview(adapter);
        } else {
            return new ViewReviewAction.MenuAction(adapter, dataType, true);
        }
    }

    private static ViewReviewAction.MenuAction newMenuEdit(ReviewBuilder controller,
            GvDataList.GvType dataType) {
        if (dataType == GvDataList.GvType.COMMENTS) {
            return new MenuEditComments(controller);
        } else if (dataType == GvDataList.GvType.CHILDREN) {
            return new MenuEditChildren(controller);
        } else {
            return new MenuDeleteDone(controller, dataType);
        }
    }

    private static ViewReviewAction.GridItemAction newGridItemAction(GvAdapter adapter,
            GvDataList.GvType dataType) {
        if (dataType == GvDataList.GvType.SOCIAL) {
            return new GridItemSocial(adapter);
        } else {
            return new ViewReviewAction.GridItemAction(adapter, dataType);
        }
    }

    private static ViewReviewAction.GridItemAction newGridItemEdit(ReviewBuilder
            controller,
            GvDataList.GvType dataType) {
        if (dataType == GvDataList.GvType.COMMENTS) {
            return new GridItemEditComment(controller);
        } else if (dataType == GvDataList.GvType.IMAGES) {
            return new GridItemEditImage(controller);
        } else if (dataType == GvDataList.GvType.BUILD_REVIEW) {
            return new GridItemBuildReview(controller);
        } else {
            return new GridItemEdit(controller, dataType);
        }
    }

    private static ViewReviewAction.BannerButtonAction newBannerButtonAdd(ReviewBuilder
            controller,
            GvDataList.GvType dataType) {
        if (dataType == GvDataList.GvType.IMAGES) {
            return new BannerButtonAddImage(controller);
        } else {
            return new BannerButtonAdd(controller, dataType);
        }
    }

    private static ViewReviewAction.RatingBarAction newRatingBarAction(GvAdapter adapter) {
        return new ViewReviewAction.RatingBarAction(adapter);
    }

    private static ViewReviewAction.RatingBarAction newRatingBarEdit(ReviewBuilder
            controller,
            GvDataList.GvType dataType) {
        if (dataType == GvDataList.GvType.BUILD_REVIEW) {
            return new RatingEditBuildReview(controller);
        } else {
            return new RatingEdit(controller);
        }
    }

    private static ViewReviewAction.SubjectViewAction newSubjectViewAction(GvAdapter adapter) {
        return new ViewReviewAction.SubjectViewAction(adapter);
    }

    private static ViewReviewAction.SubjectViewAction newSubjectEdit(ReviewBuilder
            controller) {
        return new SubjectEdit(controller);
    }

    private static CoverManager newCoverManager(GvAdapter adapter) {
        return new CoverManagerAdapter(adapter);
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
        if (dataType == GvDataList.GvType.BUILD_REVIEW || dataType == GvDataList.GvType.SOCIAL) {
            params.gridAlpha = ViewReview.GridViewImageAlpha.TRANSPARENT;
        }
    }
}
