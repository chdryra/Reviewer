package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSubject;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSubjectList;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvConverterSubjects extends GvConverterDataReview<DataSubject, GvSubject, GvSubjectList>{
    public GvConverterSubjects() {
        super(GvSubjectList.class);
    }

    @Override
    public GvSubject convert(DataSubject datum) {
        return new GvSubject(newId(datum.getReviewId()), datum.getSubject());
    }
}
