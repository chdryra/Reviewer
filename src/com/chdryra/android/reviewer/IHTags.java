/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 14 October, 2014
 */

package com.chdryra.android.reviewer;

import android.content.Intent;
import android.os.Bundle;

import com.chdryra.android.reviewer.GVTagList.GVTag;

/**
 * Created by: Rizwan Choudrey
 * On: 14/10/2014
 * Email: rizwan.choudrey@gmail.com
 */
class IHTags extends InputHandlerReviewData<GVTag> {
    IHTags() {
        super(GVReviewDataList.GVType.TAGS);
    }

    IHTags(GVTagList data) {
        super(data);
    }

    @Override
    void pack(CurrentNewDatum currentNew, GVTag tag, Bundle args) {
        args.putString(getPackingTag(currentNew), tag.get());
    }

    @Override
    void pack(CurrentNewDatum currentNew, GVTag tag, Intent data) {
        data.putExtra(getPackingTag(currentNew), tag.get());
    }

    @Override
    GVTag unpack(CurrentNewDatum currentNew, Bundle args) {
        return new GVTag(args.getString(getPackingTag(currentNew)));
    }

    @Override
    GVTag unpack(CurrentNewDatum currentNew, Intent data) {
        return new GVTag((String) data.getSerializableExtra(getPackingTag(currentNew)));
    }
}
