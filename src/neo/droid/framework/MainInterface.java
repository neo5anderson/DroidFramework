package neo.droid.framework;

/**
 * common interfaces
 * 
 * @author neo
 */
public class MainInterface {

	public interface IHandler {
		/**
		 * work for handler in activities
		 * 
		 * @param what
		 * @param arg1
		 * @param arg2
		 * @param obj
		 */
		public void onHandler(int what, int arg1, int arg2, Object obj);
	}

	public interface INotify {
		/**
		 * work for callbacks, interactions
		 * 
		 * @param src
		 * @param what
		 * @param requestOrResult
		 * @param obj
		 */
		public void onNotify(int src, int what, int requestOrResult, Object obj);
	}

}
