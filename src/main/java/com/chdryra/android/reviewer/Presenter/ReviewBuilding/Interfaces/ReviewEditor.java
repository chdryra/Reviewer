/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces;

import com.chdryra.android.reviewer.Application.Strings;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;

/**
 * Created by: Rizwan Choudrey
 * On: 07/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewEditor<T extends GvData> extends ReviewView<T> {
    enum ReadyToBuildResult {
        YES("yes"),
        NoSubject(Strings.Toasts.ENTER_SUBJECT),
        NoTags(Strings.Toasts.ENTER_TAG);

        private final String mMessage;

        ReadyToBuildResult(String message) {
            mMessage = message;
        }

        public String getMessage() {
            return mMessage;
        }
    }

    void setSubject();

    void setRatingIsAverage(boolean isAverage);

    void setRating(float rating, boolean fromUser);

    GvImage getCover();

    void setCover(GvImage image);

    void notifyBuilder();

    ImageChooser getImageChooser();

    ReadyToBuildResult isReviewBuildable();
}
