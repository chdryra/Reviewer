/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Application.Implementation;

/**
 * Created by: Rizwan Choudrey
 * On: 27/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class Strings {
    public static String APP_NAME = "StarTouch";
    public static String APP_SITE = "http:www.startouchapp.com";
    public static String APP_SITE_SHORT = "http://bit.ly/2D0CjE6";
    public static String REVIEW = "rating";
    public static String REVIEWS = REVIEW + "s";
    public static String REVIEWS_CAP = "Ratings";
    public static String FOLLOWING = "Following";
    public static String FOLLOWERS = "Followers";

    public static class Alerts {
        public static final String ADD_ON_BROWSER = "Add link using browser?";
        public static final String SET_IMAGE_AS_BACKGROUND = "Set image as cover?";
        public static final String SET_COMMENT_AS_HEADLINE = "Set comment as headline?";
        public static final String EDIT_ON_MAP = "Edit on map?";
        public static final String ADD_ON_MAP = "Add using map?";
        public static final String EDIT_ON_BROWSER = "Edit on browser?";
        public static final String DELETE_POST = "post";
        public static final String SIGN_UP = "Looks like you're new! Sign up?";
        public static final String SHOW_ON_MAP = "Show on map?";
        public static final String DISCARD_REVIEW = "Discard " + REVIEW + " ?";
        public static final String DISCARD_EDITS = "Discard edits?";
    }

    public static class Dialogs {
        public static final String ADD = "Add";
        public static final String EDIT = "Edit";
    }

    public static class Callbacks {
        public static final String PROFILE_CREATED = "Profile created";
        public static final String PROFILE_UPDATED = "Profile updated";
        public static final String NAME_TAKEN = " is already taken";
    }

    public static class Toasts {
        public static final String ENTER_SUBJECT = "Please enter a subject";
        public static final String HAS_DATA = "You\'ve already added this";
        public static final String SPLIT_COMMENT = "Showing sentences";
        public static final String UNSPLIT_COMMENT = "Showing headlines";
        public static final String BAD_URL = "Link does not make sense";
        public static final String MAP_SEARCH_FAILED = "Couldn\'t find location";
        public static final String ENTER_TAG = "Please enter at least one tag";
        public static final String ENTER_LOCATION = "Please enter a location name";
        public static final String PUBLISHING = "Publishing...";
        public static final String PROBLEM_PUBLISHING = "Problem publishing";
        public static final String PROBLEM_UPLOADING_EDITS = "Problem uploading edits";
        public static final String REVIEW_NOT_FOUND = REVIEW + " not found";
        public static final String COMPLETING_SIGNUP = "Completing sign up...";
        public static final String DELETING = "Deleting please wait...";
        public static final String LOGIN_UNSUCCESSFUL = "Login unsuccessful";
        public static final String COPYING = "Copying...";
        public static final String LAUNCHING_EDITOR = "Launching editor...";
        public static final String FOLLOWING = "Following...";
        public static final String UNFOLLOWING = "Unfollowing...";
        public static final String DONE = "Done";
        public static final String LOGGING_OUT = "Logging out...";
        public static final String PROBLEM_LOGGING_OUT = "Problem logging out...";
        public static final String NO_INTERNET = "No internet available...";
        public static final String UPLOADING_EDITS = "Uploading edits...";
        public static final String CONFIRM_PASSWORD = "Please confirm password then try again";
        public static final String CREATING_ACCOUNT = "Creating account...";
        public static final String UPDATING_PROFILE = "Updating profile...";
    }
    
    public static class Screens {
        public static final String LOCATION = "location";
        public static final String URL = "link";
        public static final String CREATE = "create";
        public static final String SHARE = "share";
        public static final String FILTER = "filter";
        public static final String PREVIEW = "preview";
        public static final String SEARCH = "search";
        public static final String EDIT = "edit";
    }

    public static class Buttons {
        public static final String SHARE = "Share";
        public static final String PUBLISH = "Publish";
        public static final String ADD = "add";
        public static final String SORTING = "sorting...";
        public static final String AUTHORS = "authors";
        public static final String FULL_REVIEW = "Full " + REVIEW;
        public static final String QUICK_REVIEW = "Quick " + REVIEW;
        public static final String MAP = "Map";
        public static final String FINISH_EDITING = "Finish Editing";
        public static final String DONE = "Done";
        public static final String FOLLOW = "Follow";
        public static final String UNFOLLOW = "Unfollow";
        public static final String EDIT_PROFILE = "Edit profile";

        public static class CommentEdit {
            public static final String SEPARATOR = "=";
            public static final String STAR = "*";
            public static final String HASHTAG = "#";
        }
    }

    public static class EditTexts {
        public static final String NO_SUGGESTIONS = "no suggestions found";
        public static final String SEARCHING_NEAR_HERE = "searching near here...";
        public static final String SEARCHING_NEAR_PHOTO = "searching near photo...";
        public static final String FETCHING = "fetching...";

        public static class Hints {
            public static final String ADD_LOCATION = "name current or select another";
            public static final String NAME_IMAGE_LOCATION = "name image location";
            public static final String AUTHOR_NAME = "author name";
            public static final String SUBJECT = "+subject";
            public static final String CONFIRM_PASSWORD = "Confirm password then press login";
        }
    }

    public static class ProgressBar {
        public static final String LOGGING_IN = "Logging in";
    }

    public static class Menu {
        public static final String SHOW_SENTENCES = "show sentences";
        public static final String SHOW_HEADLINES = "show headlines";
        public static final String REVIEWS = Strings.REVIEWS;
    }

    public static class Mapping {
        public static final String LOCATION = "location";
        public static final String LOCATIONS = "locations";
        public static final String REVIEWS = Strings.REVIEWS;
        public static final String REVIEW = Strings.REVIEW;
    }
    
    public static class Placeholders {
        public static final String NO_SUBJECT = "(no subject)";
    }

    public static class Progress {
        public static final String LOADING = "loading...";
        public static final String FETCHING = "fetching...";
        public static final String SEARCHING = "searching...";
    }

    public static class Web {
        public static final String LINK = "Link";
    }

    public static class Commands {
        public static final String SHARE = "Share";
        public static final String TEMPLATE = "Use as template";
        public static final String EDIT = "Edit";
        public static final String DELETE = "Delete";
        public static final String OPTIONS = "Options";
        public static final String REVIEW_OPTIONS = "options";
        public static final String PAGED = "Pages";
        public static final String MAPPED = "Map";
        public static final String BOOKMARK = "Bookmark";
        public static final String UNBOOKMARK = "Unbookmark";
        public static final String BOOKMARKS_OFFLINE = "(Bookmarks offline)";
        public static final String OFFLINE = "offline";
        public static final String DASHES = "- - -";
        public static final String FILTER = "Filter";
        public static final String BUCKETS = "Buckets";
        public static final String LIST = "List";
    }
    
    public static class Comparators {
        public static final String A_TO_Z = "A-Z";
        public static final String Z_TO_A = "Z-A";
        public static final String HIGH_TO_LOW = "High-Low";
        public static final String LOW_TO_HIGH = "Low-High";
        public static final String NEWEST = "New-Old";
        public static final String OLDEST = "Old-New";
        public static final String TRUE = "True";
        public static final String FALSE = "False";
        public static final String SEPARATOR = ":";
    }

    public static class ReviewsList {
        public static final String FEED = "feed";
        public static final String REVIEWS = Strings.REVIEWS;
    }

    public static class Formatted {
        public static final String DASHES = "- - -";
        public static final String AUTHOR = "author";
        public static final String DATE = "date";
        public static final String COMMENT = "comment";
        public static final String TAGS = "tags";
        public static final String NONE = "none";
        public static final String LOADING = "loading";
        public static final String CRITERIA = "Criteria";
        public static final String FACTS = "facts";
        public static final String NO_TAGS = "no tags";
        public static final String NO_SUBJECT = "no subject";
        public static final String LOCATIONS = "locations";
        public static final String IMAGES = "images";
        public static final String NO_COMMENT = "no comment";
    }

    public static class Publish {
        public static final String CONNECTED = "connected";
        public static final String NOT_CONNECTED = "not " + CONNECTED;
        public static final String SHARE = "share";
    }

    public static class Playlists {
        public static final String BOOKMARKS = "Bookmarks";
    }
}
