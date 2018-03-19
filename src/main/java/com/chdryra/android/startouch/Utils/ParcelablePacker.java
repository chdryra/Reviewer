/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Utils;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;

/**
 * Created by: Rizwan Choudrey
 * On: 15/12/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Takes care of packing and unpacking data into Bundles/Intents between different fragments
 * and activities.
 */
public class ParcelablePacker<T extends Parcelable> {
    private static final String DATUM_CURRENT = "com.chdryra.android.reviewer.data_current";
    private static final String DATUM_NEW = "com.chdryra.android.reviewer.data_new";

    public enum CurrentNewDatum {
        CURRENT(DATUM_CURRENT),
        NEW(DATUM_NEW);

        private final String mTag;

        CurrentNewDatum(String tag) {
            mTag = tag;
        }

        private String getPackingTag() {
            return mTag;
        }
    }

    public void packItem(CurrentNewDatum currentNew, T item, Bundle args) {
        args.putParcelable(currentNew.getPackingTag(), item);
    }

    public void packItem(CurrentNewDatum currentNew, T item, Intent data) {
        data.putExtra(currentNew.getPackingTag(), item);
    }

    @Nullable
    public T unpack(CurrentNewDatum currentNew, Bundle args) {
        if (args == null) return null;
        return args.getParcelable(currentNew.getPackingTag());
    }

    @Nullable
    public T unpack(CurrentNewDatum currentNew, Intent data) {
        if (data == null) return null;
        return data.getParcelableExtra(currentNew.getPackingTag());
    }
}
