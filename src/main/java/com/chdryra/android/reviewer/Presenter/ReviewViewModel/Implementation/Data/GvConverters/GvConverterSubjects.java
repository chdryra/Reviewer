/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSubject;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSubjectList;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvConverterSubjects extends GvConverterReviewData.RefDataList<DataSubject, GvSubject, GvSubjectList, GvSubject.Reference> {
    public GvConverterSubjects() {
        super(GvSubjectList.class, GvSubject.Reference.TYPE);
    }

    @Override
    public GvSubject convert(DataSubject datum, ReviewId reviewId) {
        return new GvSubject(getGvReviewId(datum, reviewId), datum.getSubject());
    }

    @Override
    protected GvSubject.Reference convertReference(ReviewItemReference<DataSubject> reference) {
        return new GvSubject.Reference(reference, this);
    }
}
