/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Factories;

import android.support.annotation.Nullable;

import com.chdryra.android.corelibrary.Viewholder.ViewHolder;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.FactoryVhDataCollection;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .VhBuildReviewFull;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .VhBuildReviewQuick;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .VhDataCollection;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
class FactoryVhBuildReview {
    static class Full implements FactoryVhDataCollection {
        @Override
        public VhDataCollection newViewHolder(@Nullable ViewHolder datumVh) {
            return new VhBuildReviewFull(datumVh);
        }
    }

    static class Quick implements FactoryVhDataCollection {
        @Override
        public VhDataCollection newViewHolder(@Nullable ViewHolder datumVh) {
            return new VhBuildReviewQuick(datumVh);
        }
    }


}
