/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 10 February, 2015
 */

package com.chdryra.android.reviewer.test.TestUtils;

import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCriterion;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.robotium.solo.Solo;

/**
 * Created by: Rizwan Choudrey
 * On: 10/02/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class SoloDataEntry {
    //Static methods
    public static void enter(Solo solo, GvData data) {
        if (data instanceof GvTag) enterTag(solo, (GvTag) data);
        if (data instanceof GvComment) {
            enterComment(solo, (GvComment) data);
        }
        if (data instanceof GvCriterion) {
            enterCriterion(solo,
                    (GvCriterion) data);
        }
        if (data instanceof GvFact) enterFact(solo, (GvFact) data);
        if (data instanceof GvLocation) {
            enterLocation(solo, (GvLocation) data);
        }
        if (data instanceof GvImage) enterImage(solo, (GvImage) data);
    }

    public static void enterTag(Solo solo, GvTag data) {
        solo.clearEditText(solo.getEditText(0));
        solo.enterText(solo.getEditText(0), data.getString());
    }

    public static void enterComment(Solo solo, GvComment data) {
        solo.clearEditText(solo.getEditText(0));
        solo.enterText(solo.getEditText(0), data.getComment());
    }

    public static void enterCriterion(Solo solo, GvCriterion data) {
        solo.clearEditText(solo.getEditText(0));
        solo.enterText(solo.getEditText(0), data.getSubject());
        solo.setProgressBar(0, (int) (data.getRating() * 2f));
    }

    public static void enterFact(Solo solo, GvFact data) {
        solo.clearEditText(solo.getEditText(0));
        solo.clearEditText(solo.getEditText(1));
        solo.enterText(solo.getEditText(0), data.getLabel());
        solo.enterText(solo.getEditText(1), data.getValue());
    }

    public static void enterLocation(Solo solo, GvLocation data) {
        solo.clearEditText(solo.getEditText(0));
        solo.enterText(solo.getEditText(0), data.getName());
    }

    public static void enterImage(Solo solo, GvImage data) {
        solo.clearEditText(solo.getEditText(0));
        solo.enterText(solo.getEditText(0), data.getCaption());
    }

    public static void enterRating(Solo solo, float rating) {
        rating = Math.round(rating * 2f) / 2f;
        solo.setProgressBar(0, (int) (rating * 2f));
    }

    public static void enterSubject(Solo solo, String subject) {
        solo.clearEditText(0);
        solo.enterText(solo.getEditText(0), subject);
    }
}
