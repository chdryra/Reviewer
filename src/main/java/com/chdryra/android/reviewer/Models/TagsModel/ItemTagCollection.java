package com.chdryra.android.reviewer.Models.TagsModel;

/**
 * Iterable collection of ReviewTags.
 */
public interface ItemTagCollection extends Iterable<ItemTag> {
    int size();
    ItemTag getItemTag(int position);
}
