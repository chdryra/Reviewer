/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData;

import android.os.Parcel;
import android.support.annotation.Nullable;
import android.webkit.URLUtil;

import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolder;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.StringParser;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataConverter;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.VhFact;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * {@link GvData} version of: {@link com.chdryra
 * .android.reviewer.MdFactList.MdFact}
 * {@link ViewHolder}: {@link VhFact}
 */

public class GvFact extends GvDualText implements DataFact {
    public static final GvDataType<GvFact> TYPE = new GvDataType<>(GvFact.class, TYPE_NAME);

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


    public GvFact() {
        super();
    }

    public GvFact(String label, String value) {
        super(label, value);
    }

    public GvFact(@Nullable GvReviewId id, String label, String value) {
        super(id, label, value);
    }

    public GvFact(GvFact fact) {
        this(fact.getGvReviewId(), fact.getLabel(), fact.getValue());
    }

    public static GvFact newFactOrUrl(String label, String urlCandidate) {
        String urlGuess = URLUtil.guessUrl(urlCandidate.toLowerCase());
        try {
            return new GvUrl(label, new URL(urlGuess));
        } catch (MalformedURLException e) {
            return new GvFact(label, urlCandidate);
        }
    }

    protected GvFact(Parcel in) {
        super(in);
    }

    @Override
    public GvDataType<? extends GvFact> getGvDataType() {
        return GvFact.TYPE;
    }

    @Override
    public String toString() {
        return StringParser.parse(this);
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

    public static class Reference extends GvDataRef<Reference, DataFact, VhFact> {
        public static final GvDataType<GvFact.Reference> TYPE
                = new GvDataType<>(GvFact.Reference.class, GvFact.TYPE);

        public Reference(ReviewItemReference<DataFact> reference,
                         DataConverter<DataFact, GvFact, ?> converter) {
            super(TYPE, reference, converter, VhFact.class, new PlaceHolderFactory<DataFact>() {
                @Override
                public DataFact newPlaceHolder(String placeHolder) {
                    return new GvFact(placeHolder, placeHolder);
                }
            });
        }
    }
}
