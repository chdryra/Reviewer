/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.ReviewStamp;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.GridDataViewer;

/**
 * Created by: Rizwan Choudrey
 * On: 05/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class UnstampedViewer<T extends GvData> implements GridDataViewer<T> {
    @Override
    public ReviewStamp getStamp() {
        return ReviewStamp.noStamp();
    }
}
