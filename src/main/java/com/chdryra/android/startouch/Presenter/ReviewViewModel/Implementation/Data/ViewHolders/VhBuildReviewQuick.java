/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import android.support.annotation.Nullable;

import com.chdryra.android.corelibrary.Viewholder.ViewHolder;
import com.chdryra.android.startouch.Application.Interfaces.EditorSuite;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;

/**
 * Created by: Rizwan Choudrey
 * On: 26/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class VhBuildReviewQuick extends VhBuildReviewFull {
    public VhBuildReviewQuick(@Nullable ViewHolder datumView) {
        super(datumView);
    }

    @Override
    String getLowerString(int number, GvDataType dataType) {
        return isQuickType(dataType)? dataType.getDatumName() : dataType.getDataName();
    }

    private boolean isQuickType(GvDataType<?> dataType) {
        for(GvDataType<?> non : EditorSuite.NON_QUICK) {
            if(dataType.equals(non)) return false;
        }
        return true;
    }
}
