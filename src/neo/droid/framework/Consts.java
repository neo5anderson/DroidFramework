package neo.droid.framework;

import java.io.File;

import android.os.Environment;

/**
 * Full constant variables
 * 
 * @author neo
 */
public class Consts {

	public static final String APP_NAME = "DF";

	public static final String SDCARD_PATH = Environment
			.getExternalStorageDirectory().getPath();

	public static final String LOG_PATH = SDCARD_PATH + File.separator
			+ APP_NAME;

	public static final int SRC_FRAGMENT = 0x00;
	public static final int SRC_ACTIVITY = 0x01;
	public static final int SRC_SERVICE = 0x02;
	public static final int SRC_BROADCAST = 0x03;
	public static final int SRC_JNI = 0x04;
	// [Neo] TODO new add src type in here, below 0x0F
	public static final int SRC_OTHERS = 0x0F;

	public static final String TAG_UI = APP_NAME + "_UI";
	public static final String TAG_LOGICAL = APP_NAME + "_LOGICAL";
	public static final String TAG_BUG = APP_NAME + "_BUG";

	public static final String KEY_WIDTH = "width";
	public static final String KEY_HEIGHT = "height";
	public static final String KEY_DENSITY = "density";
	public static final String KEY_SCALE = "scale";
	
	public static final String KEY_AUTO_START = "auto_start";
	public static final String KEY_AUTO_UPDATE = "auto_update";

	public static final String FORMAT_IMPL_EXCEPTION = "%s must be an %s impl!";

}
