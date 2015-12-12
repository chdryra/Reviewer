package com.chdryra.android.reviewer.DataDefinitions.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.UserId;

/**
 * Created by: Rizwan Choudrey
 * On: 12/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class NullUserId implements UserId {
    public static final NullUserId ID = new NullUserId();

    private NullUserId() {

    }

    @Override
    public String getId() {
        return NULL_ID_STRING;
    }
}
