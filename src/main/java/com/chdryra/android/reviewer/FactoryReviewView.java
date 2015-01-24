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

        ReviewView.SubjectViewAction sva = new ActionSubjectEdit(controller, dataType);
        ReviewView.RatingBarAction rba = new ActionRatingEdit(controller, dataType);
        ReviewView.BannerButtonAction bba = new ActionBannerButtonAdd(controller, dataType);
        ReviewView.GridItemAction gia = new ActionGridItemEdit(controller, dataType);
        ReviewView.MenuAction ma = new ActionMenuDeleteDoneGrid(controller, dataType);

        return new ReviewView(parent, data, cover, sva, rba, bba, gia, ma, true);
    }
}
