package com.chdryra.android.reviewer.Model.Interfaces.TagsModel;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Iterable collection of ReviewTags.
 */
public interface ItemTagCollection extends Collection<ItemTag> {
    ItemTag getItemTag(int position);

    ArrayList<String> toStringArray();
}
