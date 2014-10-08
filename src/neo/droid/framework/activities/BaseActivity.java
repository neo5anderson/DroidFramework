package neo.droid.framework.activities;

import neo.droid.framework.Consts;
import neo.droid.framework.MainApplication;
import neo.droid.framework.MainInterface;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

/**
 * base abstract fragment activity
 * 
 * @author neo
 */
public abstract class BaseActivity extends FragmentActivity implements
		MainInterface.IHandler, MainInterface.INotify {

	private static final int WHAT_SHOW_TOAST = Consts.SRC_OTHERS + 0x01;
	private static final int WHAT_SHOW_TOAST_INDEED = Consts.SRC_OTHERS + 0x02;

	private static Toast TOAST;

	public static final int WHAT_BASE = Consts.SRC_OTHERS + 0x11;

	private AlertDialog dialog;

	protected MyHandler handler;

	protected abstract void onInitSettings();

	protected abstract void onInitUi();

	protected abstract void onSaveSettings();

	protected abstract void onCleanObjects();

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		handler = new MyHandler(this);

		onInitSettings();
		onInitUi();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		onCleanObjects();
	}

	@Override
	protected void onResume() {
		super.onResume();
		((MainApplication) getApplication()).push(this);
		handler.setPaused(false);
	}

	@Override
	protected void onPause() {
		super.onPause();
		((MainApplication) getApplication()).pop();
		handler.setPaused(true);

		cancelToast();
		dismissDialog();
		onSaveSettings();
	}

	protected void showProgressDialog(String msg) {
		dismissDialog();

		dialog = new ProgressDialog(this);
		dialog.setMessage(msg);
		dialog.show();
	}

	protected void showAlertDialog(AlertDialog dialog) {
		dismissDialog();

		this.dialog = dialog;
		dialog.show();
	}

	protected void dismissDialog() {
		if (null != dialog) {
			dialog.dismiss();
			dialog = null;
		}
	}

	protected void showToast(int duration, String msg) {
		handler.sendMessage(handler.obtainMessage(WHAT_SHOW_TOAST, duration, 0,
				msg));
	}

	protected void cancelToast() {
		handler.sendEmptyMessage(WHAT_SHOW_TOAST);
	}

	protected static class MyHandler extends Handler {

		private boolean paused;
		private BaseActivity activity;

		public MyHandler(BaseActivity activity) {
			this.activity = activity;
			paused = false;
		}

		public void setPaused(boolean paused) {
			this.paused = paused;
		}

		@SuppressLint("ShowToast")
		@Override
		public void handleMessage(Message msg) {
			if (false == paused) {

				switch (msg.what) {
				case WHAT_SHOW_TOAST:
					if (null != TOAST) {
						TOAST.cancel();
						if (null == msg.obj) {
							break;
						}

						TOAST.setText(msg.obj.toString());
						TOAST.setDuration(msg.arg1);
					} else {
						TOAST = Toast.makeText(activity, msg.obj.toString(),
								msg.arg1);
					}

					activity.handler.sendEmptyMessageDelayed(
							WHAT_SHOW_TOAST_INDEED, 1);
					break;

				case WHAT_SHOW_TOAST_INDEED:
					if (null != TOAST) {
						TOAST.show();
					}
					break;

				default:
					activity.onHandler(msg.what, msg.arg1, msg.arg2, msg.obj);
					break;
				}

			}
		}
	}

}
