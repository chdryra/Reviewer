/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Authentication;

import com.chdryra.android.reviewer.Authentication.Implementation.EmailPasswordValidation;
import com.chdryra.android.reviewer.Utils.EmailPassword;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 11/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class EmailPassswordValidationTest {
    @Test
    public void validNameIsValid() {
        String email = "first.last@test.com";
        String password = "12345678";

        EmailPasswordValidation validation = new EmailPasswordValidation(email, password);

        EmailPassword emailPassword = validation.getEmailPassword();
        assertThat(emailPassword, not(nullValue()));
        assertThat(emailPassword.getEmail(), not(nullValue()));
        assertThat(emailPassword.getEmail().toString(), is(email));
        assertThat(emailPassword.getPassword(), not(nullValue()));
        assertThat(emailPassword.getPassword().toString(), is(password));
        assertThat(validation.getError(), is(nullValue()));
    }

    @Test
    public void invalidLengthPasswordGivesInvalidLengthReason() {
        String email = "first.last@test.com";
        String password = "1234567";

        EmailPasswordValidation validation = new EmailPasswordValidation(email, password);

        EmailPassword emailPassword = validation.getEmailPassword();
        assertThat(emailPassword, is(nullValue()));
        assertThat(validation.getError().getReason(), is(EmailPasswordValidation.Reason.INVALID_PASSWORD));
    }

    @Test
    public void invalidEmailGivesInvalidEmailReason() {
        String email = "first.last";
        String password = "12345678";

        EmailPasswordValidation validation = new EmailPasswordValidation(email, password);

        EmailPassword emailPassword = validation.getEmailPassword();
        assertThat(emailPassword, is(nullValue()));
        assertThat(validation.getError().getReason(), is(EmailPasswordValidation.Reason.INVALID_EMAIL));
    }
}
