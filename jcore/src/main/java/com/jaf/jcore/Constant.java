package com.jaf.jcore;


public class Constant {
	
	public interface Debug {
		public static final String TAG = "wagongsi";
		public static final boolean DEBUG = true;
	}
	
	public interface Home {
		public static final String NEW_URL = "http://www.wagongsi.com/api/index.php/company/newlist";
		public static final String HOT_URL = "http://www.wagongsi.com/api/index.php/company/hotlist";
		public static final String NEW_MSG_URL = "http://www.wagongsi.com/api/index.php/company/user";
	}
	
	public interface Login {
		public static final String REQUEST_TAG = "login";
		public static final String URL = "http://www.wagongsi.com/api/index.php/company/login";
	}
	
	public interface Register {
		public static final String REQUEST_TAG = "reg";
		public static final String URL = "http://www.wagongsi.com/api/index.php/company/register";
	}
	
	public interface ValidCode {
		public static final String REQUEST_TAG = "valid";
		public static final String URL = "http://www.wagongsi.com/api/index.php/company/validcode";
	}
	
	public interface Detail {
		public static final String REQUEST_TAG = "detail";
	}
	
	public interface Publish {
		public static final String URL = "http://www.wagongsi.com/api/index.php/company/publish";
		public static final String REQUEST_TAG = "publish";
	}
	
	public interface NewMsg {
		public static final String URL = "http://www.wagongsi.com/api/index.php/company/msglist";
		public static final String REQUEST_TAG = "new msg";
	}
	
	public interface MeItem {
		public static final int REQUEST_CODE = 0xab;
	}
	
	public interface ForgetPass {
		public static final String URL_GET_VALID = "http://www.wagongsi.com/api/index.php/company/getvalidcode";
		public static final String REQUEST_TAG = "forget";
		public static final String URL = "http://www.wagongsi.com/api/index.php/company/resetpwd";
	}

	public static final long UPDATE_DELAY = 1000 * 60 * 60 * 24;
}
