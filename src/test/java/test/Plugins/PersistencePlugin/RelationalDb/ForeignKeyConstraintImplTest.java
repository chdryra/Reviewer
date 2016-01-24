package test.Plugins.PersistencePlugin.RelationalDb;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Implementation.DbColumnNotNullable;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Implementation.DbEntryType;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Implementation.ForeignKeyConstraintImpl;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Interfaces.DbColumnDefinition;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Interfaces.DbTableRow;
import com.chdryra.android.testutils.RandomString;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 20/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ForeignKeyConstraintImplTest {
    @Rule
    public ExpectedException mExpectedException = ExpectedException.none();

    @Test
    public void constructorThrowsIllegalArgumentExceptionIfNumberColsDoesNotEqualNumberPrimaryKeys() {
        mExpectedException.expect(IllegalArgumentException.class);
        DbTableForTesting<DbTableRow> table
                = new DbTableForTesting<>(RandomString.nextWord(), DbTableRow.class);

        DbColumnDefinition col1 = new DbColumnNotNullable(RandomString.nextWord(), DbEntryType.TEXT);
        DbColumnDefinition col2 = new DbColumnNotNullable(RandomString.nextWord(), DbEntryType.TEXT);
        DbColumnDefinition col3 = new DbColumnNotNullable(RandomString.nextWord(), DbEntryType.TEXT);

        table.addPrimaryKeyColumn(col1);
        table.addPrimaryKeyColumn(col2);
        table.addPrimaryKeyColumn(col3);

        ArrayList<DbColumnDefinition> fkCols = new ArrayList<>();
        fkCols.add(col1);
        fkCols.add(col2);
        new ForeignKeyConstraintImpl<>(fkCols, table);
    }

    @Test
    public void constructorOkIfNumberColsEqualNumberPrimaryKeys() {
        DbTableForTesting<DbTableRow> table
                = new DbTableForTesting<>(RandomString.nextWord(), DbTableRow.class);

        DbColumnDefinition col1 = new DbColumnNotNullable(RandomString.nextWord(), DbEntryType.TEXT);
        DbColumnDefinition col2 = new DbColumnNotNullable(RandomString.nextWord(), DbEntryType.TEXT);
        DbColumnDefinition col3 = new DbColumnNotNullable(RandomString.nextWord(), DbEntryType.TEXT);

        table.addPrimaryKeyColumn(col1);
        table.addPrimaryKeyColumn(col2);
        table.addPrimaryKeyColumn(col3);

        ArrayList<DbColumnDefinition> fkCols = new ArrayList<>();
        fkCols.add(col1);
        fkCols.add(col2);
        fkCols.add(col3);
        assertThat(new ForeignKeyConstraintImpl<>(fkCols, table), not(nullValue()));
    }
}
