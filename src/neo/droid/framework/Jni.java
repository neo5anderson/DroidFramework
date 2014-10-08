package neo.droid.framework;

/**
 * Full NDK interface statements
 * 
 * @author neo
 */
public class Jni {

	static {
		System.loadLibrary("jni");
	}

	public static native boolean setOnCallbackListener(Object listener);

}
