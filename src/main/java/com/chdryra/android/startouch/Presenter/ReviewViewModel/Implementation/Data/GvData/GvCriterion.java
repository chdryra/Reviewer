/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData;

import android.os.Parcel;
import android.support.annotation.Nullable;

import com.chdryra.android.corelibrary.Viewholder.ViewHolder;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.StringParser;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataConverter;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataCriterion;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.VhCriterion;

/**
 * {@link GvData} version of: no equivalent as used
 * for review children (sub-reviews).
 * {@link ViewHolder}: {@link VhCriterion}
 */
public class GvCriterion extends GvDataParcelableBasic<GvCriterion> implements DataCriterion {
    public static final GvDataType<GvCriterion> TYPE =
            new GvDataType<>(GvCriterion.class, TYPE_NAME, DATA_NAME);
    public static final Creator<GvCriterion> CREATOR = new Creator<GvCriterion>() {
        @Override
        public GvCriterion createFromParcel(Parcel in) {
            return new GvCriterion(in);
        }

        @Override
        public GvCriterion[] newArray(int size) {
            return new GvCriterion[size];
        }
    };

    private final String mSubject;
    private final float mRating;

    //Constructors
    public GvCriterion() {
        this("", 0f);
    }

    public GvCriterion(String subject, float rating) {
        this(null, subject, rating);
    }

    public GvCriterion(@Nullable GvReviewId id, String subject, float rating) {
        super(GvCriterion.TYPE, id);
        mSubject = subject;
        mRating = rating;
    }

    public GvCriterion(GvCriterion criterion) {
        this(criterion.getGvReviewId(), criterion.getSubject(), criterion.getRating());
    }

    public GvCriterion(Parcel in) {
        super(in);
        mSubject = in.readString();
        mRating = in.readFloat();
    }

    @Override
    public String getSubject() {
        return mSubject;
    }

    @Override
    public float getRating() {
        return mRating;
    }

    //Overridden
    @Override
    public ViewHolder getViewHolder() {
        return new VhCriterion();
    }

    @Override
    public boolean isValidForDisplay() {
        return mSubject != null && mSubject.length() > 0;
    }

    @Override
    public boolean hasData(DataValidator dataValidator) {
        return dataValidator.validate(this);
    }

    @Override
    public String toString() {
        return StringParser.parse(this);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(mSubject);
        parcel.writeFloat(mRating);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GvCriterion)) return false;
        if (!super.equals(o)) return false;

        GvCriterion that = (GvCriterion) o;

        if (Float.compare(that.mRating, mRating) != 0) return false;
        return !(mSubject != null ? !mSubject.equals(that.mSubject) : that.mSubject != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (mSubject != null ? mSubject.hashCode() : 0);
        result = 31 * result + (mRating != +0.0f ? Float.floatToIntBits(mRating) : 0);
        return result;
    }

    public static class Reference extends GvDataRef<Reference, DataCriterion, VhCriterion> {
        public static final GvDataType<GvCriterion.Reference> TYPE
                = new GvDataType<>(GvCriterion.Reference.class, GvCriterion.TYPE);

        public Reference(ReviewItemReference<DataCriterion> reference,
                         DataConverter<DataCriterion, GvCriterion, ?> converter) {
            super(TYPE, reference, converter, VhCriterion.class, new PlaceHolderFactory<DataCriterion>() {
                @Override
                public DataCriterion newPlaceHolder(String placeHolder) {
                    return new GvCriterion(placeHolder, 0f);
                }
            });
        }
    }
}
