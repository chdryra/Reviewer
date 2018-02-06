/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData;

import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvDataParcelable;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class GvDataTypes {
    public static final ArrayList<GvDataType<? extends GvDataParcelable>> BUILD_TYPES = new ArrayList<>();
    static {
        BUILD_TYPES.add(GvComment.TYPE);
        BUILD_TYPES.add(GvFact.TYPE);
        BUILD_TYPES.add(GvLocation.TYPE);
        BUILD_TYPES.add(GvImage.TYPE);
        BUILD_TYPES.add(GvTag.TYPE);
        BUILD_TYPES.add(GvCriterion.TYPE);
    }
}
