/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces;

import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ActivityResultListener;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;

/**
 * Created by: Rizwan Choudrey
 * On: 07/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewEditor<GC extends GvDataList<? extends GvDataParcelable>>
        extends ReviewView<GC>, ActivityResultListener {
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

    enum EditMode {
        FULL(Strings.Buttons.FULL_REVIEW),
        QUICK(Strings.Buttons.QUICK_REVIEW);

        private String mLabel;

        EditMode(String label) {
            mLabel = label;
        }

        public String getLabel() {
            return mLabel;
        }
    }

    interface ModeListener {
        void onEditMode(EditMode mode);
    }

    void setSubject();

    void setRatingIsAverage(boolean isAverage);

    void setRating(float rating, boolean fromUser);

    GvImage getCover();

    void setCover(GvImage image);

    ImageChooser newImageChooser();

    <T extends GvDataParcelable> ReviewDataEditor<T> newDataEditor(GvDataType<T> dataType);

    ReadyToBuildResult isReviewBuildable();

    Review buildReview();

    EditMode getEditMode();

    void registerListener(ModeListener listener);

    void unregisterListener(ModeListener listener);

    ReviewNode buildPreview();
}
