package com.chdryra.android.reviewer.DataDefinitions.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.UserId;

/**
 * Created by: Rizwan Choudrey
 * On: 12/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class NullUserId extends DatumUserId {
    public static final UserId ID = new NullUserId();

    private NullUserId() {
        super(NULL_ID_STRING);
    }
}
