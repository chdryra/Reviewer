/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.TagsModel.Interfaces;

import java.util.ArrayList;

/**
 * Wraps a string plus a collection of reviews tagged with that string. Comparable with
 * another ReviewTag alphabetically.
 */
public interface ItemTag extends Comparable<ItemTag> {
    ArrayList<String> getItemIds();

    String getTag();

    boolean tagsItem(String itemId);

    boolean isTag(String tag);
}
