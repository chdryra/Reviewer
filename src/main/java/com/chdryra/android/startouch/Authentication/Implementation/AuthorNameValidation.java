/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Authentication.Implementation;

import android.support.annotation.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by: Rizwan Choudrey
 * On: 10/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class AuthorNameValidation {
    private static final int MIN_LETTERS = 3;
    private String mIfValid;
    private Reason mReason = Reason.OK;

    public enum Reason {
        OK("Ok"),
        INVALID_LENGTH("Name should have at least " + MIN_LETTERS + " characters"),
        INVALID_CHARACTERS("Name should only contain letters, numbers, underscore");

        private final String mMessage;

        Reason(String message) {
            mMessage = message;
        }

        public String getMessage() {
            return mMessage;
        }
    }

    public AuthorNameValidation(String name) {
        if (name.length() < MIN_LETTERS) {
            mReason = Reason.INVALID_LENGTH;
        } else if(isNotValid(name)){
            mReason = Reason.INVALID_CHARACTERS;
        }

        if (mReason == Reason.OK) mIfValid = name;
    }

    public Reason getReason() {
        return mReason;
    }

    @Nullable
    public String getName() {
        return mIfValid;
    }

    private boolean isNotValid(String toExamine) {
        Pattern pattern = Pattern.compile("[^abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_]");
        Matcher matcher = pattern.matcher(toExamine);
        return matcher.find();
    }
}
