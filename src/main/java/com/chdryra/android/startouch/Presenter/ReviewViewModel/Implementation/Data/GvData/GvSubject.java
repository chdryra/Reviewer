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

import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.StringParser;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataConverter;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataSubject;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.VhText;

/**
 * Created by: Rizwan Choudrey
 * On: 23/11/2015
 * Email: rizwan.choudrey@gmail.com
 */ //Classes
public class GvSubject extends GvText<GvSubject> implements DataSubject {
    public static final GvDataType<GvSubject> TYPE = new GvDataType<>(GvSubject.class, TYPE_NAME);
    public static final Creator<GvSubject> CREATOR = new Creator<GvSubject>() {
        @Override
        public GvSubject createFromParcel(Parcel in) {
            return new GvSubject(in);
        }

        @Override
        public GvSubject[] newArray(int size) {
            return new GvSubject[size];
        }
    };

    private GvSubject() {
        super(TYPE);
    }

    private GvSubject(String subject) {
        super(TYPE, subject);
    }

    public GvSubject(@Nullable GvReviewId id, String subject) {
        super(TYPE, id, subject);
    }

    private GvSubject(GvSubject subject) {
        this(new GvReviewId(subject.getReviewId()), subject.getString());
    }

    private GvSubject(Parcel in) {
        super(in);
    }

    @Override
    public String getSubject() {
        return getString();
    }

    @Override
    public String toString() {
        return StringParser.parse(this);
    }

    public static class Reference extends GvDataRef<Reference, DataSubject, VhText> {
        public static final GvDataType<GvSubject.Reference> TYPE
                = new GvDataType<>(GvSubject.Reference.class, GvSubject.TYPE);

        public Reference(ReviewItemReference<DataSubject> reference,
                         DataConverter<DataSubject, GvSubject, ?> converter) {
            super(TYPE, reference, converter, VhText.class, new PlaceHolderFactory<DataSubject>() {
                @Override
                public DataSubject newPlaceHolder(String placeHolder) {
                    return new GvSubject(placeHolder);
                }
            });
        }
    }
}
