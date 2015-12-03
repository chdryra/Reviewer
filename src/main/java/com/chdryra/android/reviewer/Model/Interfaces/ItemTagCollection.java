package com.chdryra.android.reviewer.Model.Interfaces;

import java.util.Collection;

/**
 * Iterable collection of ReviewTags.
 */
public interface ItemTagCollection extends Collection<ItemTag> {
    ItemTag getItemTag(int position);
}
