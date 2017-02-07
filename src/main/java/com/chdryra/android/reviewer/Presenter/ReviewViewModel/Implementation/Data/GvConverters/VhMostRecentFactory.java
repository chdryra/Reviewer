/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters;

import android.content.res.Resources;
import android.graphics.BitmapFactory;

import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryCoverBinder;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .ReviewSelector;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .SelectorMostRecent;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .VhNode;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .VhReviewSelected;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .ViewHolderFactory;
import com.chdryra.android.reviewer.R;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class VhMostRecentFactory implements ViewHolderFactory<VhNode> {
    private static final int IMAGE_PLACEHOLDER = R.drawable.image_placeholder;

    private final AuthorsRepository mRepository;
    private final Resources mResources;
    private final FactoryCoverBinder mFactoryCoverBinder;

    public VhMostRecentFactory(AuthorsRepository repository, Resources resources) {
        mRepository = repository;
        mResources = resources;
        mFactoryCoverBinder = new FactoryCoverBinder();
    }

    @Override
    public VhNode newViewHolder() {
        return new VhReviewSelected(mRepository, new ReviewSelector(new SelectorMostRecent()),
                BitmapFactory.decodeResource(mResources, IMAGE_PLACEHOLDER), mFactoryCoverBinder);
    }
}
