package com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters;

import com.chdryra.android.reviewer.Interfaces.Data.DataSubject;
import com.chdryra.android.reviewer.View.GvDataModel.GvSubjectList;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvConverterSubjects extends GvConverterDataReview<DataSubject, GvSubjectList.GvSubject, GvSubjectList>{
    public GvConverterSubjects() {
        super(GvSubjectList.class);
    }

    @Override
    public GvSubjectList.GvSubject convert(DataSubject datum) {
        return new GvSubjectList.GvSubject(newId(datum.getReviewId()), datum.getSubject());
    }
}
