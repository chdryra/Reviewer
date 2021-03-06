/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 10/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class GvDataRefList<Reference extends GvDataRef> extends GvDataListImpl<Reference> {
    public GvDataRefList(GvDataType<Reference> dataType, GvReviewId reviewId) {
        super(dataType, reviewId);
    }

    public void unbind() {
        for (Reference reference : this) {
            reference.unbind();
        }
    }
}
