package com.chdryra.android.reviewer.View.GvDataModel.Implementation;

import android.os.Parcel;

import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataFact;
import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvData;

/**
 * {@link GvData} version of: {@link com.chdryra
 * .android.reviewer.MdFactList.MdFact}
 * {@link ViewHolder}: {@link VhFact}
 */

public class GvFact extends GvDualText implements DataFact {
    public static final Creator<GvFact> CREATOR = new Creator<GvFact>() {
        @Override
        public GvFact createFromParcel(Parcel in) {
            return new GvFact(in);
        }

        @Override
        public GvFact[] newArray(int size) {
            return new GvFact[size];
        }
    };
    public static GvDataType<GvFact> TYPE = new GvDataType<>(GvFact.class, "fact");

    //Constructors
    public GvFact() {
        super();
    }

    public GvFact(String label, String value) {
        super(label, value);
    }

    public GvFact(GvReviewId id, String label, String value) {
        super(id, label, value);
    }

    public GvFact(GvFact fact) {
        this(fact.getGvReviewId(), fact.getLabel(), fact.getValue());
    }

    protected GvFact(Parcel in) {
        super(in);
    }

    //Overridden
    @Override
    public GvDataType<? extends GvFact> getGvDataType() {
        return GvFact.TYPE;
    }

    @Override
    public String getStringSummary() {
        return getLabel() + ": " + getValue();
    }

    @Override
    public ViewHolder getViewHolder() {
        return new VhFact();
    }

    @Override
    public boolean hasData(DataValidator dataValidator) {
        return dataValidator.validate(this);
    }

    @Override
    public String getLabel() {
        return getUpper();
    }

    @Override
    public String getValue() {
        return getLower();
    }

    @Override
    public boolean isUrl() {
        return false;
    }
}
