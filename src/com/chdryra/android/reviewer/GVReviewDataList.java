/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.os.Parcelable;

import com.chdryra.android.mygenerallibrary.VHDataList;
import com.chdryra.android.mygenerallibrary.ViewHolderData;

/**
 * The (Grid) View layer (V) equivalent of the Model layer (M) RDList. Implementation of VHDataList
 * tailored for Review data accessed via a ControllerReview (C) that translates between them (MVC
 * pattern).
 * <p/>
 * <p>
 * Access of Review data via a ControllerReview requires passing one of the enums of GVType
 * specified in this class. The same enum also provides a singular and plural readable text
 * label for that type of data.
 * </p>
 * <p/>
 * <p>
 * Of course having this enum violates the 100% decoupling between model and view layers as
 * it encodes types of review data that can be accessed. However the convenience it brings in
 * terms of gating access and labeling is worth the modest maintenance increase. It doesn't
 * need to know about the implementations of the model data, just that they exist.
 * </p>
 *
 * @param <T>: the GVData type in the list.
 * @see com.chdryra.android.reviewer.RDList
 * @see com.chdryra.android.reviewer.ControllerReview
 */
abstract class GVReviewDataList<T extends GVReviewDataList.GVReviewData> extends VHDataList<T> {

    protected GVType mDataType;

    /**
     * Enum that enumerates and labels the type of review data that will be viewable on a GridView
     */
    enum GVType {
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

        String getDatumString() {
            return mDatumString;
        }

        String getDataString() {
            return mDataString;
        }
    }

    GVReviewDataList(GVType dataType) {
        mDataType = dataType;
    }

    /**
     * Parcelable version of ViewHolderData
     */
    interface GVReviewData extends ViewHolderData, Parcelable {
    }

    GVType getGVType() {
        return mDataType;
    }
}
