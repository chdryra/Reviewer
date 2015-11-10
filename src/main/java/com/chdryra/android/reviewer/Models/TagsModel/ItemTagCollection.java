package com.chdryra.android.reviewer.Models.TagsModel;

/**
 * Iterable collection of ReviewTags.
 */
public interface ItemTagCollection<T extends ItemTag> extends Iterable<T> {

    int size();
    T getItemTag(int position);
}
