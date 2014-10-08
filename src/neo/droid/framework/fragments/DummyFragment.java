package neo.droid.framework.fragments;

import neo.droid.framework.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DummyFragment extends BaseFragment {

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// [Neo] TODO
		arguments.containsKey("todo_list");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.activity_main, container, false);
	}

	@Override
	public void onHandler(int what, int arg1, int arg2, Object obj) {
	}

	@Override
	public void onNotify(int src, int what, int requestOrResult, Object obj) {
	}

}
