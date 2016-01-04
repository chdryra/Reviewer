package com.chdryra.android.reviewer.DataDefinitions.DataConverters.GvConverters;


import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTagList;

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
    public GvTag convert(DataTag datum, ReviewId reviewId) {
        return new GvTag(newId(reviewId), datum.getTag());
    }
}
