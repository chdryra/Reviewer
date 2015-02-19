/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 10 February, 2015
 */

package com.chdryra.android.reviewer.test.TestUtils;

import com.chdryra.android.reviewer.GvChildrenList;
import com.chdryra.android.reviewer.GvCommentList;
import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.GvFactList;
import com.chdryra.android.reviewer.GvImageList;
import com.chdryra.android.reviewer.GvLocationList;
import com.chdryra.android.reviewer.GvTagList;
import com.chdryra.android.reviewer.GvUrlList;
import com.robotium.solo.Solo;

/**
 * Created by: Rizwan Choudrey
 * On: 10/02/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class SoloDataEntry {
    public static void enter(Solo solo, GvDataList.GvData data) {
        if (data instanceof GvTagList.GvTag) enterTag(solo, (GvTagList.GvTag) data);
        if (data instanceof GvCommentList.GvComment) {
            enterComment(solo, (GvCommentList.GvComment) data);
        }
        if (data instanceof GvChildrenList.GvChildReview) {
            enterCriterion(solo,
                    (GvChildrenList.GvChildReview) data);
        }
        if (data instanceof GvFactList.GvFact) enterFact(solo, (GvFactList.GvFact) data);
        if (data instanceof GvLocationList.GvLocation) {
            enterLocation(solo, (GvLocationList.GvLocation) data);
        }
        if (data instanceof GvImageList.GvImage) enterImage(solo, (GvImageList.GvImage) data);
        if (data instanceof GvUrlList.GvUrl) enterUrl(solo, (GvUrlList.GvUrl) data);
    }

    public static void enterTag(Solo solo, GvTagList.GvTag data) {
        solo.clearEditText(solo.getEditText(0));
        solo.enterText(solo.getEditText(0), data.get());
    }

    public static void enterComment(Solo solo, GvCommentList.GvComment data) {
        solo.clearEditText(solo.getEditText(0));
        solo.enterText(solo.getEditText(0), data.getComment());
    }

    public static void enterCriterion(Solo solo, GvChildrenList.GvChildReview data) {
        solo.clearEditText(solo.getEditText(0));
        solo.enterText(solo.getEditText(0), data.getSubject());
        solo.setProgressBar(0, (int) (data.getRating() * 2f));
    }

    public static void enterFact(Solo solo, GvFactList.GvFact data) {
        solo.clearEditText(solo.getEditText(0));
        solo.clearEditText(solo.getEditText(1));
        solo.enterText(solo.getEditText(0), data.getLabel());
        solo.enterText(solo.getEditText(1), data.getValue());
    }

    public static void enterLocation(Solo solo, GvLocationList.GvLocation data) {
        solo.clearEditText(solo.getEditText(0));
        solo.enterText(solo.getEditText(0), data.getName());
    }

    public static void enterImage(Solo solo, GvImageList.GvImage data) {
        solo.clearEditText(solo.getEditText(0));
        solo.enterText(solo.getEditText(0), data.getCaption());
    }

    public static void enterUrl(Solo solo, GvUrlList.GvUrl data) {
        solo.clearEditText(solo.getEditText(0));
        solo.enterText(solo.getEditText(0), data.toString());
    }

    public static void enterRating(Solo solo, float rating) {
        rating = Math.round(rating * 2f) / 2f;
        solo.setProgressBar(0, (int) (rating * 2f));
    }
}
