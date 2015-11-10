package com.chdryra.android.reviewer.Adapter.DataConverters;

import com.chdryra.android.reviewer.Interfaces.Data.DataSubject;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewId;
import com.chdryra.android.reviewer.View.GvDataModel.GvSubjectList;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvConverterSubjects extends GvConverterBasic<DataSubject, GvSubjectList.GvSubject, GvSubjectList>{
    public GvConverterSubjects() {
        super(GvSubjectList.class);
    }

    @Override
    public GvSubjectList.GvSubject convert(DataSubject datum) {
        GvReviewId id = new GvReviewId(datum.getReviewId());
        return new GvSubjectList.GvSubject(id, datum.getSubject());
    }
}
