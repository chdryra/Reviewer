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

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewAdapter;
import com.chdryra.android.reviewer.ApplicationSingletons.ReviewsManager;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewId;
import com.chdryra.android.reviewer.View.Screens.ReviewDataScreen;
import com.chdryra.android.reviewer.View.Utils.RequestCodeGenerator;

/**
 * Created by: Rizwan Choudrey
 * On: 18/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewLauncher {
    public static void launchReview(Context context, Fragment commissioner, GvReviewId id) {
        ReviewNode node = ReviewsManager.getReviewNode(context, id.getId());
        ReviewViewAdapter adapter = FactoryReviewViewAdapter.newTreeDataAdapter(node);
        LaunchableUi ui = ReviewDataScreen.newScreen(context, adapter);
        String tag = node.getSubject().get();
        int requestCode = RequestCodeGenerator.getCode(tag);
        LauncherUi.launch(ui, commissioner, requestCode, tag, new Bundle());
    }
}
