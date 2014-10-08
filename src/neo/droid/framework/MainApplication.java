package neo.droid.framework;

import java.util.HashMap;
import java.util.Stack;

import neo.droid.framework.MainInterface.INotify;
import neo.droid.framework.activities.BaseActivity;
import neo.droid.framework.commons.MyLog;
import android.app.Application;

/**
 * Main Application, receive & dispatch
 * 
 * @author neo
 */
public class MainApplication extends Application implements
		MainInterface.INotify {

	private INotify currentNotify;

	private Stack<BaseActivity> activityStack;
	private HashMap<String, String> statusMap;

	@Override
	public void onCreate() {
		super.onCreate();

		currentNotify = null;

		activityStack = new Stack<BaseActivity>();
		statusMap = new HashMap<String, String>();

		MyLog.init(Consts.LOG_PATH);
		MyLog.enableFile(false);
		MyLog.enableLogcat(true);

		MyLog.i(Consts.TAG_UI, "App created");
	}

	public boolean push(BaseActivity activity) {
		boolean result = false;

		if (null == activity) {
			throw new NullPointerException();
		} else if (activity instanceof INotify) {
			currentNotify = (INotify) activityStack.push(activity);
			if (null != currentNotify) {
				result = true;
			}
		} else {
			throw new ClassCastException(String.format(
					Consts.FORMAT_IMPL_EXCEPTION,
					BaseActivity.class.getSimpleName(),
					INotify.class.getSimpleName()));
		}

		return result;
	}

	public BaseActivity pop() {
		return activityStack.pop();
	}

	public String getStatus(String key) {
		return (statusMap.containsKey(key)) ? statusMap.get(key) : null;
	}

	public boolean putStatus(String key, String value) {
		return (null != statusMap.put(key, value));
	}

	public void finish(boolean isKillSelf) {
		while (activityStack.size() > 0) {
			pop().finish();
		}

		if (isKillSelf) {
			android.os.Process.killProcess(android.os.Process.myPid());
		}
	}

	@Override
	public void onNotify(int src, int what, int requestOrResult, Object obj) {
		if (null != currentNotify) {
			currentNotify.onNotify(src, what, requestOrResult, obj);
		}
	}

}
