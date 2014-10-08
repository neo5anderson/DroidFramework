package neo.droid.framework.activities;

import neo.droid.framework.Consts;
import neo.droid.framework.MainApplication;
import neo.droid.framework.R;
import neo.droid.framework.commons.MyLog;
import android.app.AlertDialog;
import android.util.DisplayMetrics;
import android.widget.Toast;

public class MainActivity extends BaseActivity {

	private static final int WHAT_AUTO_SHOW_PROGRESS = WHAT_BASE + 0x01;
	private static final int WHAT_AUTO_SHOW_ALERT = WHAT_BASE + 0x02;
	private static final int WHAT_DISMISS_DIALOG = WHAT_BASE + 0x03;

	@Override
	protected void onInitSettings() {
		// Jni.setOnCallbackListener(getApplication());

		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		((MainApplication) getApplication()).putStatus(Consts.KEY_WIDTH,
				String.valueOf(metrics.widthPixels));
		((MainApplication) getApplication()).putStatus(Consts.KEY_HEIGHT,
				String.valueOf(metrics.heightPixels));
		((MainApplication) getApplication()).putStatus(Consts.KEY_DENSITY,
				String.valueOf(metrics.density));
		((MainApplication) getApplication()).putStatus(Consts.KEY_SCALE,
				String.valueOf(metrics.scaledDensity));

		MyLog.d(Consts.TAG_UI, "Main initSettings");
	}

	@Override
	protected void onInitUi() {
		setContentView(R.layout.activity_main);
		showToast(Toast.LENGTH_SHORT, "initUi");

		handler.sendEmptyMessageDelayed(WHAT_AUTO_SHOW_ALERT, 1000);
		handler.sendEmptyMessageDelayed(WHAT_AUTO_SHOW_PROGRESS, 3000);
		handler.sendEmptyMessageDelayed(WHAT_DISMISS_DIALOG, 5000);
	}

	@Override
	protected void onSaveSettings() {
		MyLog.d(Consts.TAG_UI, "Main saveSettings");
	}

	@Override
	protected void onCleanObjects() {
	}

	@Override
	public void onHandler(int what, int arg1, int arg2, Object obj) {
		switch (what) {
		case WHAT_AUTO_SHOW_ALERT:
			showAlertDialog(new AlertDialog.Builder(this).setTitle("Alert")
					.setMessage("msg here").create());
			break;

		case WHAT_AUTO_SHOW_PROGRESS:
			MyLog.d(Consts.TAG_LOGICAL, "handler callback at 3s");
			showProgressDialog("dummy ...");
			break;

		case WHAT_DISMISS_DIALOG:
			dismissDialog();
			break;

		default:
			break;
		}
	}

	@Override
	public void onNotify(int src, int what, int requestOrResult, Object obj) {
	}

}
