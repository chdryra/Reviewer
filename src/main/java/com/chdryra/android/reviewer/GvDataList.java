/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.os.Parcelable;

import com.chdryra.android.mygenerallibrary.ViewHolderData;
import com.chdryra.android.mygenerallibrary.ViewHolderDataList;

/**
 * The (Grid) View layer (V) equivalent of the Model layer (M) {@link MdDataList}. Implementation of
 * {@link ViewHolderDataList} tailored for Review data accessed via a {@link ControllerReview} (C)
 * that
 * translates between
 * them (MVC pattern).
 * <p/>
 * <p>
 * Access of Review data via a {@link ControllerReview} requires passing a {@link com.chdryra
 * .android.reviewer.GvDataList.GvType}
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
 * @param <T>: {@link GvDataList.GvData} type.
 */
public abstract class GvDataList<T extends GvDataList.GvData> extends ViewHolderDataList<T> {
    private GvType mDataType;

    /**
     * Enum that enumerates and labels the type of data that will be viewable on a GridView
     */
    public enum GvType {
        BUILD_UI("create", "create"),
        COMMENTS("comment"),
        CHILDREN("criterion", "criteria"),
        IMAGES("image"),
        FACTS("fact"),
        URLS("link"),
        LOCATIONS("location"),
        TAGS("tag"),
        FEED("feed", "feed"),
        SOCIAL("social");

        private final String mDatumString;
        private final String mDataString;

        GvType(String datum) {
            mDatumString = datum;
            mDataString = datum + "s";
        }

        GvType(String datum, String data) {
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

    /**
     * Parcelable version of {@link ViewHolderData}
     */
    public interface GvData extends ViewHolderData, Parcelable {
    }

    protected GvDataList(GvType dataType) {
        mDataType = dataType;
    }


    //Only used for GvCellManager
    protected GvDataList() {
        mDataType = null;
    }

    public GvType getGvType() {
        return mDataType;
    }
}
