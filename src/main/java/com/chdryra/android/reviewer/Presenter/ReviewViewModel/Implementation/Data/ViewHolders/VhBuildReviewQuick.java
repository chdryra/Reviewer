/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;

/**
 * Created by: Rizwan Choudrey
 * On: 26/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class VhBuildReviewQuick extends VhBuildReviewFull {
    @Override
    String getLowerString(int number, GvDataType dataType) {
        return dataType.equals(GvTag.TYPE) ? dataType.getDataName() : dataType.getDatumName();
    }
}
