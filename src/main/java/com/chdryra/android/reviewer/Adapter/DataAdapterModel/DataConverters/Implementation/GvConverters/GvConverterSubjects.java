package com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.GvConverters;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataSubject;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvSubject;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvSubjectList;

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
