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

import com.chdryra.android.mygenerallibrary.TextUtils.TextUtils;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataConverter;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataUrl;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.VhFact;

import java.net.URL;

/**
 * {@link GvData} version of: {@link com.chdryra
 * .android.reviewer.MdUrlList.MdUrl}
 * <p>
 * Methods for getting full URL and shortened more readable version.
 * </p>
 */
public class GvUrl extends GvFact implements DataUrl {
    public static final GvDataType<GvUrl> TYPE = new GvDataType<>(GvUrl.class, "link");
    public static final Creator<GvUrl> CREATOR = new Creator<GvUrl>() {
        @Override
        public GvUrl createFromParcel(Parcel in) {
            return new GvUrl(in);
        }

        @Override
        public GvUrl[] newArray(int size) {
            return new GvUrl[size];
        }
    };

    private URL mUrl;

    public GvUrl() {
        super();
    }

    public GvUrl(String label, URL url) {
        super(label, TextUtils.toShortenedString(url));
        mUrl = url;
    }

    public GvUrl(@Nullable GvReviewId id, String label, URL url) {
        super(id, label, TextUtils.toShortenedString(url));
        mUrl = url;
    }

    private GvUrl(GvUrl url) {
        this(url.getGvReviewId(), url.getLabel(), url.getUrl());
    }

    private GvUrl(Parcel in) {
        super(in);
        mUrl = (URL) in.readSerializable();
    }

    @Override
    public GvDataType<GvUrl> getGvDataType() {
        return GvUrl.TYPE;
    }

    @Override
    public boolean isValidForDisplay() {
        return getLabel() != null && getLabel().length() > 0 && mUrl != null;
    }

    @Override
    public boolean hasData(DataValidator dataValidator) {
        return dataValidator.validate(this);
    }

    @Override
    public boolean isUrl() {
        return true;
    }

    @Override
    public URL getUrl() {
        return mUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeSerializable(mUrl);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GvUrl)) return false;
        if (!super.equals(o)) return false;

        GvUrl gvUrl = (GvUrl) o;

        return mUrl != null ? mUrl.equals(gvUrl.mUrl) : gvUrl.mUrl == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (mUrl != null ? mUrl.hashCode() : 0);
        return result;
    }

    public static class Reference extends GvDataRef<Reference, DataUrl, VhFact> {
        public static final GvDataType<GvUrl.Reference> TYPE
                = new GvDataType<>(GvUrl.Reference.class, GvUrl.TYPE);

        public Reference(ReviewItemReference<DataUrl> reference,
                         DataConverter<DataUrl, GvUrl, ?> converter) {
            super(TYPE, reference, converter, VhFact.class, new PlaceHolderFactory<DataUrl>() {
                @Override
                public DataUrl newPlaceHolder(String placeHolder) {
                    return new GvUrl();
                }
            });
        }
    }
}
