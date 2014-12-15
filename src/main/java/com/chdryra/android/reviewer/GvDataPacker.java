/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 15 December, 2014
 */

package com.chdryra.android.reviewer;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by: Rizwan Choudrey
 * On: 15/12/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Takes care of packing and unpacking data into Bundles/Intents between different fragments.
 *
 * @param <T>: {@link GvDataList.GvData} type.
 */
public class GvDataPacker<T extends GvDataList.GvData> {
    private static final String DATUM_CURRENT = "com.chdryra.android.reviewer.data_current";
    private static final String DATUM_NEW     = "com.chdryra.android.reviewer.data_new";

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

    public static void packItem(CurrentNewDatum currentNew, GvDataList.GvData item, Bundle args) {
        if (item != null && !item.isValidForDisplay()) item = null;
        args.putParcelable(currentNew.getPackingTag(), item);
    }

    public static void packItem(CurrentNewDatum currentNew, GvDataList.GvData item, Intent data) {
        if (item != null && !item.isValidForDisplay()) item = null;
        data.putExtra(currentNew.getPackingTag(), item);
    }

    public static GvDataList.GvData unpackItem(CurrentNewDatum currentNew, Bundle args) {
        return args.getParcelable(currentNew.getPackingTag());
    }

    public static GvDataList.GvData unpackItem(CurrentNewDatum currentNew, Intent data) {
        return data.getParcelableExtra(currentNew.getPackingTag());
    }

    public T unpack(CurrentNewDatum currentNew, Bundle args) {
        return args.getParcelable(currentNew.getPackingTag());
    }

    public T unpack(CurrentNewDatum currentNew, Intent data) {
        return data.getParcelableExtra(currentNew.getPackingTag());
    }
}
