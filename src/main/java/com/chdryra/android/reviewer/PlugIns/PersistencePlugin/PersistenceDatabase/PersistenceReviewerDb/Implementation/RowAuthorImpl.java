package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumUserId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.UserId;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Interfaces.RowEntry;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Interfaces.RowValues;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Interfaces.RowAuthor;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RowAuthorImpl extends RowTableBasic implements RowAuthor {
    private String mUserId;
    private String mName;

    public RowAuthorImpl(DataAuthor author) {
        mUserId = author.getUserId().toString();
        mName = author.getName();
    }

    //Via reflection
    public RowAuthorImpl() {
    }

    public RowAuthorImpl(RowValues values) {
        mUserId = values.getValue(USER_ID.getName(), USER_ID.getType());
        mName = values.getValue(AUTHOR_NAME.getName(), AUTHOR_NAME.getType());
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public UserId getUserId() {
        return new DatumUserId(mUserId);
    }

    @Override
    public String getRowId() {
        return mUserId;
    }

    @Override
    public String getRowIdColumnName() {
        return USER_ID.getName();
    }

    @Override
    public boolean hasData(DataValidator validator) {
        return validator.validate(this);
    }

    @Override
    protected int size() {
        return 2;
    }

    @Override
    protected RowEntry<?> getEntry(int position) {
        if(position == 0) {
            return new RowEntryImpl<>(USER_ID, mUserId);
        } else if(position == 1){
            return new RowEntryImpl<>(AUTHOR_NAME, mName);
        } else {
            throw noElement();
        }
    }
}
