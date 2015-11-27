package com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.GvConverters;

import com.chdryra.android.reviewer.Model.Interfaces.ItemTag;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvTag;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvTagList;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvConverterItemTags extends GvConverterBasic<ItemTag, GvTag, GvTagList>{
    public GvConverterItemTags() {
        super(GvTagList.class);
    }

    @Override
    public GvTag convert(ItemTag datum, String reviewId) {
        if(!datum.tagsItem(reviewId)) {
            throw new IllegalArgumentException("ItemTag does not tage id: " + reviewId);
        }
        return new GvTag(newId(reviewId), datum.getTag());
    }
}
