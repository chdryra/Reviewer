/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Application.Implementation;

/**
 * Created by: Rizwan Choudrey
 * On: 27/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class Strings {
    public static String AppName = "StarTouch";

    public static class Alerts {
        public static final String ADD_ON_BROWSER = "Add link using browser?";
        public static final String SET_IMAGE_AS_BACKGROUND = "Set image as cover?";
        public static final String SET_COMMENT_AS_HEADLINE = "Set comment as headline?";
        public static final String EDIT_ON_MAP = "Edit on map?";
        public static final String ADD_ON_MAP = "Add using map?";
        public static final String EDIT_ON_BROWSER = "Edit on browser?";
        public static final String DELETE_REVIEW = "published review";
        public static final String NEW_USER = "Looks like you're a new user?";
        public static final String SHOW_ON_MAP = "Show on map?";
    }

    public static class Dialogs {
        public static final String ADD = "Add";
        public static final String EDIT = "Edit";
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
        public static final String REVIEW_NOT_FOUND = "Review not found";
        public static final String COMPLETING_SIGNUP = "Completing sign up...";
        public static final String DELETING = "Deleting please wait...";
        public static final String EMAIL_IS_INVALID = "Email is invalid";
        public static final String PASSWORD_IS_INCORRECT = "Password is incorrect";
        public static final String LOGIN_UNSUCCESSFUL = "Login unsuccessful";
        public static final String COPYING = "Copying...";
        public static final String FOLLOWING = "Following...";
        public static final String BOOKMARKING = "Bookmarking...";
        public static final String UNBOOKMARKING = "Unbookmarking...";
        public static final String BOOKMARKED = "Bookmarked";
        public static final String UNBOOKMARKED = "Unbookmarked";
        public static final String UNFOLLOWING = "Unfollowing...";
        public static final String DONE = "Done";
        public static final String LOGGING_OUT = "Logging out...";
        public static final String PROBLEM_LOGGING_OUT = "Problem logging out...";
        public static final String NO_INTERNET = "No internet available...";
    }
    
    public static class Screens {
        public static final String LOCATION = "location";
        public static final String URL = "link";
        public static final String BUILD = "create";
        public static final String SHARE = "share";
        public static final String SUMMARY = "summary";
        public static final String PREVIEW = "preview";
        public static final String SEARCH = "search";
    }

    public static class Buttons {
        public static final String SHARE = "Share";
        public static final String PUBLISH = "Publish";
        public static final String ADD = "add";
        public static final String SUMMARY = "summary";
        public static final String SORTING = "sorting...";
        public static final String AUTHORS = "authors";
        public static final String FULL_REVIEW = "Full review";
        public static final String QUICK_REVIEW = "Quick review";
        public static final String DISTRIBUTION = "Ratings distribution";
        public static final String MAP = "Map";

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

        public static class Hints {
            public static final String ADD_LOCATION = "name current or select another";
            public static final String NAME_IMAGE_LOCATION = "name image location";
            public static final String AUTHOR_NAME = "author name";
            public static final String SUBJECT = "+subject";
        }
    }

    public static class ProgressBar {
        public static final String NO_ONE_LOGGED_IN = "No one logged in";
        public static final String LOGGING_IN = "Logging in";
        public static final String PLEASE_WAIT = "Please wait...";
    }

    public static class Menu {
        public static final String SHOW_SENTENCES = "show sentences";
        public static final String SHOW_HEADLINES = "show headlines";
        public static final String REVIEWS = "reviews";
        public static final String PAGE = "page";
    }

    public static class Mapping {
        public static final String LOCATIONS = "locations";
        public static final String REVIEWS = "reviews";
        public static final String REVIEW = "review";
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
        public static final String COPY = "Use as template";
        public static final String DELETE = "Delete";
        public static final String OPTIONS = "Options";
        public static final String REVIEW_OPTIONS = "Review options";
        public static final String FORMATTED = "Formatted";
        public static final String MAPPED = "Mapped";
        public static final String DISTRIBUTION = "Distribution";
        public static final String BOOKMARK = "Bookmark";
        public static final String UNBOOKMARK = "Unbookmark";
        public static final String BOOKMARKS_OFFLINE = "(Bookmarks offline)";
        public static final String OFFLINE = "offline";
        public static final String DASHES = "- - -";
    }
    
    public static class Comparators {
        public static final String A_TO_Z = "A to Z";
        public static final String Z_TO_A = "Z to A";
        public static final String HIGH_TO_LOW = "High To Low";
        public static final String LOW_TO_HIGH = "Low To High";
        public static final String NEWEST = "Newest";
        public static final String OLDEST = "Oldest";
        public static final String TRUE = "True";
        public static final String FALSE = "False";
        public static final String SEPARATOR = ":";
    }

    public static class ReviewsList {
        public static final String FEED = "feed";
        public static final String REVIEWS = "reviews";
        public static final String BOOKMARKS = "bookmarks";
    }

    public static class Formatted {
        public static final String DASHES = "- - -";
        public static final String AUTHOR = "author";
        public static final String DATE = "date";
        public static final String COMMENT = "comment";
        public static final String TAGS = "tags";
        public static final String NONE = "none";
        public static final String CRITERIA = "Criteria";
        public static final String FACTS = "facts";
        public static final String NO_TAGS = "no tags";
        public static final String NO_SUBJECT = "no subject";
        public static final String LOCATIONS = "locations";
        public static final String IMAGES = "images";
        public static final String NO_COMMENT = "no comment";
    }

    public static class Playlists {
        public static final String BOOKMARKS = "Bookmarks";
    }
}
