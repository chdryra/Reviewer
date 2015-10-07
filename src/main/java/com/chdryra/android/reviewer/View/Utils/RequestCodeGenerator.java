package com.chdryra.android.reviewer.View.Utils;

/**
 * Created by: Rizwan Choudrey
 * On: 30/08/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RequestCodeGenerator {
    //Static methods
    public static int getCode(String tag) {
        return tag != null ? tag.hashCode() : 0;
    }
}
