package com.chdryra.android.reviewer.Models.TagsModel;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TagsManagerImpl implements TagsManager{
    private final ItemTagList<ItemTagImpl> mTags;

    //Constructors
    public TagsManagerImpl() {
        mTags = new ItemTagList<>();
    }

    //public methods
    public ItemTagCollection getTags() {
        return new ItemTagList<>(mTags);
    }

    public ItemTagCollection getTags(String id) {
        ItemTagList<ItemTagImpl> tags = new ItemTagList<>();
        for (ItemTag tag : mTags) {
            if (tag.tagsItem(id)) tags.add((ItemTagImpl) tag);
        }

        return tags;
    }

    @Override
    public ArrayList<String> getTagsArray(String id) {
        return mTags.toStringArray();
    }

    @Override
    public void tagItem(String id, String tag) {
        ItemTagImpl itemTag = mTags.get(tag);
        if (itemTag == null) {
            mTags.add(new ItemTagImpl(tag, id));
        } else {
            itemTag.addItem(id);
        }
    }

    @Override
    public void tagItem(String id, ArrayList<String> tags) {
        for(String tag : tags) {
            tagItem(id, tag);
        }
    }

    @Override
    public boolean untagItem(String id, ItemTag tag) {
        ItemTagImpl tagImpl = (ItemTagImpl) tag;
        if (tagImpl.tagsItem(id)) {
            tagImpl.removeItem(id);
            if (tag.getItemIds().size() == 0) {
                mTags.remove(tagImpl);
                return true;
            }
        }

        return false;
    }
}
