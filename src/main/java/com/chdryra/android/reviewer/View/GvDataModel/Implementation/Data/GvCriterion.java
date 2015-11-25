package com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data;

import android.os.Parcel;

import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.Utils.RatingFormatter;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.ViewHolders.VhChild;
import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvData;

/**
 * {@link GvData} version of: no equivalent as used
 * for review children (sub-reviews).
 * {@link ViewHolder}: {@link VhChild}
 */
public class GvCriterion extends GvDataBasic<GvCriterion> {
    public static final GvDataType<GvCriterion> TYPE =
            new GvDataType<>(GvCriterion.class, "criterion", "criteria");
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
        this(null, 0f);
    }

    public GvCriterion(String subject, float rating) {
        this(null, subject, rating);
    }

    public GvCriterion(GvReviewId id, String subject, float rating) {
        super(GvCriterion.TYPE, id);
        mSubject = subject;
        mRating = rating;
    }

    public GvCriterion(GvCriterion child) {
        this(child.getGvReviewId(), child.getSubject(), child.getRating());
    }

    GvCriterion(Parcel in) {
        super(in);
        mSubject = in.readString();
        mRating = in.readFloat();
    }

    //public methods
    public String getSubject() {
        return mSubject;
    }

    public float getRating() {
        return mRating;
    }

    //Overridden
    @Override
    public ViewHolder getViewHolder() {
        return new VhChild();
    }

    @Override
    public boolean isValidForDisplay() {
        return mSubject != null && mSubject.length() > 0;
    }

    @Override
    public boolean hasData(DataValidator dataValidator) {
        return dataValidator.validateString(mSubject);
    }

    @Override
    public String getStringSummary() {
        return getSubject() + ": " + RatingFormatter.outOfFive(getRating());
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
}
