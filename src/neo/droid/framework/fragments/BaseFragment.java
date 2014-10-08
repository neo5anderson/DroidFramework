package neo.droid.framework.fragments;

import java.lang.reflect.Field;

import neo.droid.framework.MainInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

/**
 * base abstract fragment
 * 
 * @author neo
 */
public abstract class BaseFragment extends Fragment implements
		MainInterface.IHandler, MainInterface.INotify {

	protected MyHandler handler;

	protected FragmentActivity parent;
	protected Bundle arguments;
	protected View view;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		parent = getActivity();
		arguments = getArguments();
		view = getView();
	}

	@Override
	public void onDetach() {
		super.onDetach();
		try {
			Field childFragmentManager = Fragment.class
					.getDeclaredField("mChildFragmentManager");
			childFragmentManager.setAccessible(true);
			childFragmentManager.set(this, null);
		} catch (Exception e) {
			// [Neo] empty
		}
	}

	protected static class MyHandler extends Handler {
		private BaseFragment fragment;

		public MyHandler(BaseFragment fragment) {
			this.fragment = fragment;
		}

		@Override
		public void handleMessage(Message msg) {
			fragment.onHandler(msg.what, msg.arg1, msg.arg2, msg.obj);
		}

	}

}
