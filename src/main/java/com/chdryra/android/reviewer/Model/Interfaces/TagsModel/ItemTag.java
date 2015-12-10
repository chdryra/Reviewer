package com.chdryra.android.reviewer.Model.Interfaces.TagsModel;

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
