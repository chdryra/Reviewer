package com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.GvConverters;


import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataTag;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvTag;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvTagList;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvConverterDataTags extends GvConverterBasic<DataTag, GvTag, GvTagList>{
    public GvConverterDataTags() {
        super(GvTagList.class);
    }

    @Override
    public GvTag convert(DataTag datum, String reviewId) {
        return new GvTag(newId(reviewId), datum.getTag());
    }
}
