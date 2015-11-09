package com.chdryra.android.reviewer.View.GvDataModel;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataAuthor;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvAuthorConverter extends
        GvConverterBasic<DataAuthor, GvAuthorList.GvAuthor, GvAuthorList> {

    public GvAuthorConverter() {
        super(GvAuthorList.class);
    }

    @Override
    public GvAuthorList.GvAuthor convert(DataAuthor datum) {
        return new GvAuthorList.GvAuthor(datum.getName(), datum.getUserId());
    }
}
