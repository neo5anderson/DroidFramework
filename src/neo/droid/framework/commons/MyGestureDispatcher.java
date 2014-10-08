package neo.droid.framework.commons;

import android.view.MotionEvent;

/**
 * Gesture dispatcher
 * 
 * @author neo
 */
public class MyGestureDispatcher {

	/** default is null */
	public static final int GESTURE_TO_NULL = 0x00;

	/** left */
	public static final int GESTURE_TO_LEFT = 0x01;
	/** up */
	public static final int GESTURE_TO_UP = 0x02;
	/** right */
	public static final int GESTURE_TO_RIGHT = 0x03;
	/** down */
	public static final int GESTURE_TO_DOWN = 0x04;

	private static final int DEFAULT_BLIND_AERA_R_SQUARE = 60;

	private int currentDirection;

	private float lastDownX;
	private float lastDownY;

	private int blindAreaRSquare;

	private boolean isReported;
	private boolean ignoreReport;

	private OnGestureListener listener;

	/**
	 * construction, with a listener
	 * 
	 * @param listener
	 */
	public MyGestureDispatcher(OnGestureListener listener) {
		isReported = false;
		ignoreReport = false;

		currentDirection = GESTURE_TO_NULL;
		blindAreaRSquare = DEFAULT_BLIND_AERA_R_SQUARE;

		this.listener = listener;
	}

	public boolean isReported() {
		return isReported;
	}

	public void setReported(boolean isReported) {
		this.isReported = isReported;
	}

	public boolean isIgnoreReport() {
		return ignoreReport;
	}

	public void setIgnoreReport(boolean ignoreReport) {
		this.ignoreReport = ignoreReport;
	}

	public int getBlindAreaRSquare() {
		return blindAreaRSquare;
	}

	public void setBlindAreaRSquare(int blindAreaRSquare) {
		this.blindAreaRSquare = blindAreaRSquare;
	}

	/**
	 * call me when every onTouch event
	 * 
	 * @param event
	 * @return
	 */
	public boolean motion(MotionEvent event) {
		boolean result = false;

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			if (false == ignoreReport) {
				lastDownX = event.getX();
				lastDownY = event.getY();
				isReported = false;
				currentDirection = GESTURE_TO_NULL;
			}
			break;
		}

		case MotionEvent.ACTION_UP:
			isReported = true;
			break;

		case MotionEvent.ACTION_MOVE: {
			if (isReported || ignoreReport) {
				break;
			}

			float upOffset = lastDownY - event.getY();
			float rightOffset = event.getX() - lastDownX;

			// [Neo] blind area check
			if ((upOffset * upOffset + rightOffset * rightOffset) < blindAreaRSquare) {
				break;
			}

			// [Neo] dot with plane
			if (upOffset + rightOffset > 0 && upOffset - rightOffset < 0) {
				currentDirection = GESTURE_TO_RIGHT;
			} else if (upOffset + rightOffset > 0 && upOffset - rightOffset > 0) {
				currentDirection = GESTURE_TO_UP;
			} else if (upOffset + rightOffset < 0 && upOffset - rightOffset > 0) {
				currentDirection = GESTURE_TO_LEFT;
			} else if (upOffset + rightOffset < 0 && upOffset - rightOffset < 0) {
				currentDirection = GESTURE_TO_DOWN;
			}

			if (null != listener) {
				result = true;
				isReported = true;
				listener.onGesture(currentDirection);
			}

			break;
		}

		default:
			break;
		}

		return result;
	}

	public interface OnGestureListener {

		/**
		 * direction event
		 * 
		 * @param direction
		 *            ref {@link MyGestureDispatcher#GESTURE_TO_LEFT}<br>
		 *            ref {@link MyGestureDispatcher#GESTURE_TO_UP}<br>
		 *            ref {@link MyGestureDispatcher#GESTURE_TO_RIGHT}<br>
		 *            ref {@link MyGestureDispatcher#GESTURE_TO_DOWN}
		 */
		public void onGesture(int direction);
	}

}
