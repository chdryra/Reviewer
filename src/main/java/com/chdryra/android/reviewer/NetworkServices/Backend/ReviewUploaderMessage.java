/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.NetworkServices.Backend;

import android.os.Parcel;

import com.chdryra.android.reviewer.NetworkServices.ReviewServiceMessage;

/**
 * Created by: Rizwan Choudrey
 * On: 23/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewUploaderMessage extends ReviewServiceMessage {
    public static final Creator<ReviewUploaderMessage> CREATOR
            = new Creator<ReviewUploaderMessage>() {
        @Override
        public ReviewUploaderMessage createFromParcel(Parcel in) {
            return new ReviewUploaderMessage(in);
        }

        @Override
        public ReviewUploaderMessage[] newArray(int size) {
            return new ReviewUploaderMessage[size];
        }
    };

    public ReviewUploaderMessage(Parcel in) {
        super(in);
    }

    private ReviewUploaderMessage() {
        super();
    }

    public static ReviewUploaderMessage ok(String message) {

        return new ReviewUploaderMessage(message, false);
    }

    public static ReviewUploaderMessage error(String message) {
        return new ReviewUploaderMessage(message, true);
    }

    private ReviewUploaderMessage(String message, boolean isError) {
        super(message, isError);
    }
}
