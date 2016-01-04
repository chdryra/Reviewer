package com.chdryra.android.reviewer.DataDefinitions.DataConverters.GvConverters;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.ItemTag;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTagList;

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
    public GvTag convert(ItemTag datum, ReviewId reviewId) {
        if(!datum.tagsItem(reviewId.toString())) {
            throw new IllegalArgumentException("ItemTag does not tag id: " + reviewId);
        }
        return new GvTag(newId(reviewId), datum.getTag());
    }
}
