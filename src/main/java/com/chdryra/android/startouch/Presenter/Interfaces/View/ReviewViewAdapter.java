/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.Interfaces.View;

/**
 * Created by: Rizwan Choudrey
 * On: 13/02/2015
 * Email: rizwan.choudrey@gmail.com
 */

import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;

/**
 * Adapter for {@link Review} model data to be presented in a {@link ReviewView} View layer using
 * {@link GvData}
 */
public interface ReviewViewAdapter<T extends GvData> extends GridDataViewer<T> {
    interface CoverCallback {
        void onAdapterCover(GvImage cover);
    }

    interface Filterable<T extends GvData> extends ReviewViewAdapter<T> {
        interface Callback {
            void onFiltered();
        }

        void filterGrid(String query, Callback callback);
    }

    void attachReviewView(ReviewView<T> view);

    void detachReviewView();

    ReviewView<T> getReviewView();

    void getCover(CoverCallback callback);

    String getSubject();

    float getRating();
}
