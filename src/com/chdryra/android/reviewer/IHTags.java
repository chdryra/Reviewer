/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 14 October, 2014
 */

package com.chdryra.android.reviewer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.chdryra.android.reviewer.GVTagList.GVTag;

/**
 * Created by: Rizwan Choudrey
 * On: 14/10/2014
 * Email: rizwan.choudrey@gmail.com
 */
class IHTags extends InputHandlerReviewData<GVTag> {
    private GVTagList mTags;

    IHTags() {
        super(GVReviewDataList.GVType.TAGS);
        mTags = (GVTagList) mData;
    }

    IHTags(GVTagList data) {
        super(data);
        mTags = (GVTagList) mData;
    }

    @Override
    void pack(CurrentNewDatum currentNew, GVTag tag, Bundle args) {
        args.putString(currentNew.getPackingTag(), tag.get());
    }

    @Override
    void pack(CurrentNewDatum currentNew, GVTag tag, Intent data) {
        data.putExtra(currentNew.getPackingTag(), tag.get());
    }

    @Override
    GVTag unpack(CurrentNewDatum currentNew, Bundle args) {
        return new GVTag(args.getString(currentNew.getPackingTag()));
    }

    @Override
    GVTag unpack(CurrentNewDatum currentNew, Intent data) {
        return new GVTag((String) data.getSerializableExtra(currentNew.getPackingTag()));
    }

    @Override
    public void add(Intent data, Context context) {
        addIfValid((String) data.getSerializableExtra(getNewDataTag()), context);
    }

    @Override
    public void replace(Intent data, Context context) {
        GVTag oldTag = unpack(CurrentNewDatum.CURRENT, data);
        GVTag newTag = unpack(CurrentNewDatum.NEW, data);
        if (isNewAndValid(oldTag.get(), newTag.get(), context)) {
            mTags.remove(oldTag);
            mTags.add(newTag);
        }
    }

    @Override
    public void delete(Intent data) {
        if (mTags != null) mTags.remove(unpack(CurrentNewDatum.CURRENT, data));
    }

    private boolean isNewAndValid(String oldTag, String newTag, Context context) {
        return !oldTag.equalsIgnoreCase(newTag) && isNewAndValid(newTag, context);
    }

    boolean isNewAndValid(String tag, Context context) {
        return tag != null && tag.length() > 0 && !contains(tag, context);
    }

    private boolean contains(String tag, Context context) {
        if (mTags != null && mTags.contains(tag)) {
            Toast.makeText(context, context.getResources().getString(R.string.toast_has_tag),
                    Toast.LENGTH_SHORT).show();

            return true;
        }

        return false;
    }

    boolean addIfValid(String tag, Context context) {
        if (isNewAndValid(tag, context)) {
            mTags.add(tag);
            return true;
        }

        return false;
    }
}
