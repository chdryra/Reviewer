/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.View.GvDataModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.reviewer.Interfaces.Data.DataFact;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;

public class GvFactList extends GvDataList<GvFactList.GvFact> {
    public static final Parcelable.Creator<GvFactList> CREATOR = new Parcelable
            .Creator<GvFactList>() {
        //Overridden
        public GvFactList createFromParcel(Parcel in) {
            return new GvFactList(in);
        }

        public GvFactList[] newArray(int size) {
            return new GvFactList[size];
        }
    };

    //Constructors
    public GvFactList() {
        super(GvFact.TYPE, null);
    }

    public GvFactList(GvReviewId id) {
        super(GvFact.TYPE, id);
    }

    public GvFactList(GvFactList data) {
        super(data);
    }

    public GvFactList(Parcel in) {
        super(in);
    }


    //Classes
    /**
     * {@link GvData} version of: {@link com.chdryra
     * .android.reviewer.MdFactList.MdFact}
     * {@link ViewHolder}: {@link VhFact}
     */

    public static class GvFact extends GvDualText implements DataFact {
        public static final Parcelable.Creator<GvFact> CREATOR = new Parcelable
                .Creator<GvFact>() {
            //Overridden
            public GvFact createFromParcel(Parcel in) {
                return new GvFact(in);
            }

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
}
