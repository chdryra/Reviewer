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

import com.chdryra.android.reviewer.ApplicationSingletons.Administrator;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.ReviewsProviderModel.ReviewsRepository;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.Screens.ReviewListScreen;
import com.chdryra.android.reviewer.View.Utils.RequestCodeGenerator;

/**
 * Created by: Rizwan Choudrey
 * On: 18/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewLauncher {
    //Static methods
    public static void launchReview(Context context, Fragment commissioner, GvData datum) {
        ReviewsRepository repo = Administrator.get(context).getReviewsRepository();
        launchReview(context, commissioner, repo.getReview(datum), repo);
    }

    private static void launchReview(Context context, Fragment commissioner, Review review,
                                     ReviewsRepository repository) {
        LaunchableUi ui = ReviewListScreen.newScreen(context, review, repository);
        String tag = review.getSubject().get();
        int requestCode = RequestCodeGenerator.getCode(tag);
        LauncherUi.launch(ui, commissioner, requestCode, tag, new Bundle());
    }
}
