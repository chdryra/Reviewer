/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.TagsModel.Interfaces;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Iterable collection of ReviewTags.
 */
public interface ItemTagCollection extends Collection<ItemTag> {
    ItemTag getItemTag(int position);

    ArrayList<String> toStringArray();
}
