/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 18 June, 2015
 */

package com.chdryra.android.reviewer.View.Launcher;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;

import com.chdryra.android.reviewer.ApplicationSingletons.ReviewMaker;
import com.chdryra.android.reviewer.ApplicationSingletons.ReviewsManager;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewIdableList;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.Screens.ReviewListScreen;
import com.chdryra.android.reviewer.View.Utils.RequestCodeGenerator;

/**
 * Created by: Rizwan Choudrey
 * On: 18/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewLauncher {
    public static void launchReview(Context context, Fragment commissioner, GvData datum) {
        ReviewNode node = ReviewsManager.getReviewNode(context, datum);
        launchReview(context, commissioner, node);
    }

    public static void launchReview(Context context, Fragment commissioner, ReviewNode node) {
        ReviewNode meta;
        if (node.getChildren().size() == 0) {
            ReviewIdableList<ReviewNode> single = new ReviewIdableList<>();
            single.add(node.expand());
            meta = ReviewMaker.createMetaReview(context, single, node.getSubject().get());
        } else {
            meta = node;
        }

        LaunchableUi ui = ReviewListScreen.newScreen(context, meta);
        String tag = node.getSubject().get();
        int requestCode = RequestCodeGenerator.getCode(tag);
        LauncherUi.launch(ui, commissioner, requestCode, tag, new Bundle());
    }
}
