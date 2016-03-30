/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.NetworkServices.Social.Implementation;

import android.os.Parcel;

import com.chdryra.android.reviewer.NetworkServices.ReviewServiceMessage;

/**
 * Created by: Rizwan Choudrey
 * On: 23/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class SocialPublishingMessage extends ReviewServiceMessage {
    public static final Creator<SocialPublishingMessage> CREATOR
            = new Creator<SocialPublishingMessage>() {
        @Override
        public SocialPublishingMessage createFromParcel(Parcel in) {
            return new SocialPublishingMessage(in);
        }

        @Override
        public SocialPublishingMessage[] newArray(int size) {
            return new SocialPublishingMessage[size];
        }
    };

    public SocialPublishingMessage(Parcel in) {
        super(in);
    }

    private SocialPublishingMessage() {
        super();
    }

    public static SocialPublishingMessage ok(String message) {

        return new SocialPublishingMessage(message, false);
    }

    public static SocialPublishingMessage error(String message) {
        return new SocialPublishingMessage(message, true);
    }

    private SocialPublishingMessage(String message, boolean isError) {
        super(message, isError);
    }
}