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
public class FactoryReviewView {
    public static FactoryReviewView sFactory;

    private FactoryReviewView() {
    }

    private static FactoryReviewView get() {
        if (sFactory == null) sFactory = new FactoryReviewView();
        return sFactory;
    }

    public static ReviewView newReviewView(FragmentReviewView parent, GvDataList.GvType dataType,
            boolean isEdit) {
        return get().getReviewViewEdit(parent, dataType);
    }

    private ReviewView getReviewViewEdit(FragmentReviewView parent, GvDataList.GvType dataType) {
        Activity activity = parent.getActivity();
        Intent i = activity.getIntent();
        Administrator admin = Administrator.get(activity);
        ControllerReviewEditable controller = (ControllerReviewEditable) admin.unpack(i);

        GvDataList data = controller.getData(dataType);
        GvImageList.GvImage cover = controller.getRandomCover();

        return new ReviewView(parent, data, cover,
                newSubjectViewAction(controller, dataType),
                newRatingBarAction(controller, dataType),
                newBannerButtonAction(controller, dataType),
                newGridItemActionEdit(controller, dataType),
                newMenuActionEdit(controller, dataType), true);
    }

    private ReviewViewAction.MenuAction newMenuActionEdit(ControllerReviewEditable controller,
            GvDataList.GvType dataType) {
        if (dataType == GvDataList.GvType.COMMENTS) {
            return new ActionMenuCommentsEdit(controller);
        } else if (dataType == GvDataList.GvType.CHILDREN) {
            return new ActionMenuChildrenEdit(controller);
        } else {
            return new ActionMenuDeleteDoneGrid(controller, dataType);
        }
    }

    private ReviewViewAction.GridItemAction newGridItemActionEdit(ControllerReviewEditable
            controller,
            GvDataList.GvType dataType) {
        if (dataType == GvDataList.GvType.COMMENTS) {
            return new ActionGridItemEditComment(controller);
        } else if (dataType == GvDataList.GvType.IMAGES) {
            return new ActionGridItemEditImage(controller);
        } else {
            return new ActionGridItemEdit(controller, dataType);
        }
    }

    private ReviewViewAction.BannerButtonAction newBannerButtonAction(ControllerReviewEditable
            controller,
            GvDataList.GvType dataType) {
        if (dataType == GvDataList.GvType.IMAGES) {
            return new ActionBannerButtonAddImage(controller);
        } else {
            return new ActionBannerButtonAdd(controller, dataType);
        }
    }

    private ReviewViewAction.RatingBarAction newRatingBarAction(ControllerReviewEditable controller,
            GvDataList.GvType dataType) {
        if (dataType == GvDataList.GvType.CHILDREN) {
            return new ActionRatingEditChildren(controller);
        } else {
            return new ReviewViewAction.RatingBarAction(controller, dataType);
        }
    }

    private ReviewViewAction.SubjectViewAction newSubjectViewAction(ControllerReviewEditable
            controller,
            GvDataList.GvType dataType) {
        return new ReviewViewAction.SubjectViewAction(controller, dataType);
    }
}
