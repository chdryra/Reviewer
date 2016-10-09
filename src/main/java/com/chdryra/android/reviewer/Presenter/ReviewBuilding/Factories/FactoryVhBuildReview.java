/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories;

import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.FactoryVhDataCollection;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .VhBuildReviewFull;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .VhBuildReviewQuick;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .VhDataCollection;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryVhBuildReview {
    public static class Full implements FactoryVhDataCollection {
        @Override
        public VhDataCollection newViewHolder() {
            return new VhBuildReviewFull();
        }
    }

    public static class Quick implements FactoryVhDataCollection {
        @Override
        public VhDataCollection newViewHolder() {
            return new VhBuildReviewQuick();
        }
    }


}
