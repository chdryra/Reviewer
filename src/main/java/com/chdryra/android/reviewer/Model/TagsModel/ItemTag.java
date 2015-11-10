package com.chdryra.android.reviewer.Model.TagsModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Wraps a string plus a collection of reviews tagged with that string. Comparable with
 * another ReviewTag alphabetically.
 */
public class ItemTag implements Comparable<ItemTag> {
    private final ArrayList<String> mItemIds;
    private final String mTag;

    ItemTag(String tag, String id) {
        //mTag = WordUtils.capitalize(tag);
        mTag = tag;
        mItemIds = new ArrayList<>();
        mItemIds.add(id);
    }

    //public methods
    public ArrayList<String> getItemIds() {
        return new ArrayList<>(mItemIds);
    }

    public String getTag() {
        return mTag;
    }

    public boolean tagsItem(String id) {
        return mItemIds.contains(id);
    }

    public boolean equals(String tag) {
        return mTag.equalsIgnoreCase(tag);
    }

    void addItem(String id) {
        if (!mItemIds.contains(id)) mItemIds.add(id);
    }

    void removeItem(String id) {
        mItemIds.remove(id);
    }

    //Overridden
    @Override
    public int compareTo(@NotNull ItemTag another) {
        return mTag.compareToIgnoreCase(another.mTag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemTag)) return false;

        ItemTag itemTag = (ItemTag) o;

        if (!mItemIds.equals(itemTag.mItemIds)) return false;
        return mTag.equals(itemTag.mTag);

    }


    @Override
    public int hashCode() {
        int result = mItemIds.hashCode();
        result = 31 * result + mTag.hashCode();
        return result;
    }


}
