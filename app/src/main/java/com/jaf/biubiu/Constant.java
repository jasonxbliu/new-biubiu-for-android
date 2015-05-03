package com.jaf.biubiu;

/**
 * Created by jarrah on 2015/4/14.
 */
public interface Constant {

    public static final String KEY_PUSH_EXTRA = "push_detail_extra";

    public interface BottomTabBar {
        public static final int TAB_COUNT = 4;
        public static final int FIRST_TAB = R.id.tab0;
        public static final int TAB_TITLES[] = new int[] {
                R.string.nearby, R.string.topic, R.string.message, R.string.me
        };
    }

    public static final String KEY_PUSH_DETAIL = "PUSH_START_DETAIL";

    public static final String API = "http://183.131.76.109/cgi/user_svc.php";
    public static final String VER = "2.1";
    public static final String MULTIPART = "http://183.131.76.109/biubiu_upload.php";

    public interface CMD {
        public static final int USER_REG = 4097;
        public static final int USER_INFO = 4100;
        public static final int USER_LOC = 0x1005;

        public static final int LIST_NEARBY = 0X2004;

        public static final int REPORT_ABUSE = 0X5001;

        public static final int LIST_UNION = 8450;
        public static final int LIST_QUESTION = 8197;

        public static final int PUBLISH_QUESTION = 8198;
        public static final int LIST_TOPIC_QUESTION = 8451;
        public static final int POST_ANSWER_QUESTION = 8199;
        public static final int POST_LIKE = 8200;
        public static final int POST_MSG = 0x4302;

        public static final int POST_MY_Q = 8193;
        public static final int POST_MY_A = 8194;
        public static final int POST_FEEDBACK = 0x3001;
        public static final int RANDOM_FEEDBACK = 0x3002;
        public static final int RANDOM_TOPIC = 0x2104;
        public static final int POST_CREATE_UNION = 0x2101;
        public static final int GET_SPECIAL_NAME = 0x1005;

        public static final int POST_GET_QUESTION = 0x2009;
        public static final int POST_DELETE_MSG = 0x4303;

        public static final int POST_PUSH_SWITCH = 0x1008;
    }
}
