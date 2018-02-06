/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Authentication;

import com.chdryra.android.startouch.Authentication.Implementation.AuthorNameValidation;
import com.chdryra.android.testutils.RandomString;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
/**
 * Created by: Rizwan Choudrey
 * On: 11/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class AuthorNameValidationTest {
    @Test
    public void validNameIsValid() {
        String name = RandomString.nextWord();
        AuthorNameValidation validation = new AuthorNameValidation(name);
        assertThat(validation.getName(), is(name));
        assertThat(validation.getReason(), is(AuthorNameValidation.Reason.OK));
    }

    @Test
    public void invalidLengthGivesInvalidLengthReason() {
        AuthorNameValidation validation = new AuthorNameValidation("ab");
        assertThat(validation.getName(), is(nullValue()));
        assertThat(validation.getReason(), is(AuthorNameValidation.Reason.INVALID_LENGTH));
    }

    @Test
    public void validLengthGivesIsValid() {
        String name = "abc";
        AuthorNameValidation validation = new AuthorNameValidation(name);
        assertThat(validation.getName(), is(name));
        assertThat(validation.getReason(), is(AuthorNameValidation.Reason.OK));
    }

    @Test
    public void invalidCharactersGivesInvalidCharactersReason() {
        String illegalString = "`¬¦!\"£$%^&*()-+={}[]:;@\'~#<,>.?/|\\";
        char[] illegalChars = illegalString.toCharArray();
        for(char illegal : illegalChars) {
            AuthorNameValidation validation = new AuthorNameValidation("abc" + illegal);
            assertThat(validation.getName(), is(nullValue()));
            assertThat(validation.getReason(), is(AuthorNameValidation.Reason.INVALID_CHARACTERS));
        }
    }

    @Test
    public void underscoreIsOk() {
        String name = "abc_";
        AuthorNameValidation validation = new AuthorNameValidation(name);
        assertThat(validation.getName(), is(name));
        assertThat(validation.getReason(), is(AuthorNameValidation.Reason.OK));
    }
}
