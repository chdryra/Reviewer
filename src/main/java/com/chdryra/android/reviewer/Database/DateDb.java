package com.chdryra.android.reviewer.Database;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;
import com.chdryra.android.reviewer.Interfaces.Data.DataDate;

/**
 * Created by: Rizwan Choudrey
 * On: 14/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DateDb implements DataDate{
    private long mTime;

    public DateDb(long time) {
        mTime = time;
    }

    @Override
    public long getTime() {
        return mTime;
    }

    @Override
    public boolean hasData(DataValidator validator) {
        return validator.validate(this);
    }
}
