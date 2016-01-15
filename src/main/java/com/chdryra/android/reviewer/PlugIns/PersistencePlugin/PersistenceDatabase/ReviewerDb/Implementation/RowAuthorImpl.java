package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumUserId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.UserId;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Implementation.RowEntryImpl;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.RowEntry;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.RowValues;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.RowAuthor;

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
        mUserId = values.getValue(COLUMN_USER_ID, COLUMN_USER_ID_TYPE);
        mName = values.getValue(COLUMN_AUTHOR_NAME, COLUMN_AUTHOR_NAME_TYPE);
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
        return COLUMN_USER_ID;
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
            return new RowEntryImpl<>(COLUMN_USER_ID, COLUMN_USER_ID_TYPE, mUserId);
        } else {
            return new RowEntryImpl<>(COLUMN_AUTHOR_NAME, COLUMN_AUTHOR_NAME_TYPE, mName);
        }
    }
}