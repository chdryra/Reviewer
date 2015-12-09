package com.chdryra.android.reviewer.Model.Implementation.TagsModel;

import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.ItemTag;

import org.apache.commons.lang3.text.WordUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ItemTagImpl implements ItemTag {
    private final ArrayList<String> mItemIds;
    private final String mTag;

    public ItemTagImpl(String tag, String id) {
        mTag = WordUtils.capitalize(tag);
        mItemIds = new ArrayList<>();
        mItemIds.add(id);
    }

    @Override
    public ArrayList<String> getItemIds() {
        return new ArrayList<>(mItemIds);
    }

    @Override
    public String getTag() {
        return mTag;
    }

    @Override
    public boolean tagsItem(String id) {
        return mItemIds.contains(id);
    }

    public void addItem(String id) {
        if (!mItemIds.contains(id)) mItemIds.add(id);
    }

    public void removeItem(String id) {
        mItemIds.remove(id);
    }

    @Override
    public int compareTo(@NotNull ItemTag another) {
        return mTag.compareToIgnoreCase(another.getTag());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemTag)) return false;

        ItemTag itemTag = (ItemTag) o;

        if (!mItemIds.equals(itemTag.getItemIds())) return false;
        return mTag.equals(itemTag.getTag());

    }

    @Override
    public int hashCode() {
        int result = mItemIds.hashCode();
        result = 31 * result + mTag.hashCode();
        return result;
    }
}
