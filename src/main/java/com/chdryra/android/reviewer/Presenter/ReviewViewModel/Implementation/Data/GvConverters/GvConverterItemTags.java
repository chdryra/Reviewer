/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.mygenerallibrary.TagsModel.Interfaces.ItemTag;
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
    public GvTag convert(ItemTag datum, @Nullable ReviewId reviewId) {
        if(reviewId == null || !datum.tagsItem(reviewId.toString())) {
            throw new IllegalArgumentException("ItemTag does not tag id: " + reviewId);
        }
        return new GvTag(newId(reviewId), datum.getTag());
    }
}
