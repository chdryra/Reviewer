/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import com.chdryra.android.mygenerallibrary.GVData;
import com.chdryra.android.mygenerallibrary.GVList;

public abstract class GVReviewDataList<T extends GVData> extends GVList<T> {

    private final GVType mDataType;

    GVReviewDataList(GVType dataType) {
        mDataType = dataType;
    }

    public GVType getDataType() {
        return mDataType;
    }

    public enum GVType {
        COMMENTS("comment"),
        CHILDREN("criterion", "criteria"),
        IMAGES("image"),
        FACTS("fact"),
        REVIEW("review"),
        URLS("link"),
        LOCATIONS("location"),
        TAGS("tag"),
        SOCIAL("social");

        private final String mDatumString;
        private final String mDataString;

        GVType(String datum) {
            mDatumString = datum;
            mDataString = datum + "s";
        }

        GVType(String datum, String data) {
            mDatumString = datum;
            mDataString = data;
        }

        public String getDatumString() {
            return mDatumString;
        }

        public String getDataString() {
            return mDataString;
        }
    }
}
