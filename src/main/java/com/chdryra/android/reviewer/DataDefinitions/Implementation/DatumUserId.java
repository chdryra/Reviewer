package com.chdryra.android.reviewer.DataDefinitions.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.UserId;

/**
 * Created by: Rizwan Choudrey
 * On: 12/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DatumUserId implements UserId {
    private String mId;

    public DatumUserId(String id) {
        mId = id;
    }

    @Override
    public String toString() {
        return mId;
    }
}
