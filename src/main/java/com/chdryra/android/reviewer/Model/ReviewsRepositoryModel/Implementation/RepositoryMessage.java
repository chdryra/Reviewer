/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Implementation;

import android.os.Parcel;

import com.chdryra.android.reviewer.NetworkServices.ReviewServiceMessage;

/**
 * Created by: Rizwan Choudrey
 * On: 23/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class RepositoryMessage extends ReviewServiceMessage {
    public static final Creator<RepositoryMessage> CREATOR
            = new Creator<RepositoryMessage>() {
        @Override
        public RepositoryMessage createFromParcel(Parcel in) {
            return new RepositoryMessage(in);
        }

        @Override
        public RepositoryMessage[] newArray(int size) {
            return new RepositoryMessage[size];
        }
    };

    public RepositoryMessage(Parcel in) {
        super(in);
    }

    private RepositoryMessage() {
        super();
    }

    public static RepositoryMessage ok(String message) {

        return new RepositoryMessage(message, false);
    }

    public static RepositoryMessage error(String message) {
        return new RepositoryMessage(message, true);
    }

    private RepositoryMessage(String message, boolean isError) {
        super(message, isError);
    }
}
