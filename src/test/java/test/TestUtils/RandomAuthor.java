package test.TestUtils;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.Model.Implementation.UserModel.Author;
import com.chdryra.android.reviewer.Model.Implementation.UserModel.AuthorId;
import com.chdryra.android.testutils.RandomString;

/**
 * Created by: Rizwan Choudrey
 * On: 08/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RandomAuthor {
    public static DataAuthor nextAuthor() {
        return new Author(RandomString.nextWord(), AuthorId.generateId());
    }
}
