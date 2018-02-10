/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces;

import android.support.annotation.Nullable;

import com.chdryra.android.corelibrary.Viewholder.ViewHolder;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .VhDataCollection;

/**
 * Created by: Rizwan Choudrey
 * On: 09/10/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface FactoryVhDataCollection {
    VhDataCollection newViewHolder(@Nullable ViewHolder datumVh);
}
