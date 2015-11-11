package com.chdryra.android.reviewer.Models.TagsModel;

import java.util.ArrayList;

/**
 * Wraps a string plus a collection of reviews tagged with that string. Comparable with
 * another ReviewTag alphabetically.
 */
public interface ItemTag extends Comparable<ItemTag> {
    //public methods
    ArrayList<String> getItemIds();
    String getTag();
    boolean tagsItem(String itemId);
}
