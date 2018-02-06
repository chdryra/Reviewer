/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Utils;

/**
 * Created by: Rizwan Choudrey
 * On: 24/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class EmailAddressException extends Exception {

    public EmailAddressException(String email) {
        super("Illegal email address: " + email);
    }
}
