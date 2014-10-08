package neo.droid.framework.commons;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.util.SparseArray;

/**
 * like a sparse array, could output a array list
 * 
 * @author neo
 * 
 * @param <E>
 */
public class SparseList<E> {

	private static final boolean IS_IN_DROID = true;
	private static final int DEFAULT_START_INDEX = 0;

	private SparseArray<E> array;
	private HashMap<Integer, E> map;

	private int startIndex = DEFAULT_START_INDEX;

	/**
	 * construction, base on 0
	 */
	public SparseList() {
		if (IS_IN_DROID) {
			array = new SparseArray<E>();
		} else {
			map = new HashMap<Integer, E>();
		}
	}

	/**
	 * construction, specify the start index
	 * 
	 * @param startIndex
	 */
	public SparseList(int startIndex) {
		this.startIndex = startIndex;
		if (IS_IN_DROID) {
			array = new SparseArray<E>();
		} else {
			map = new HashMap<Integer, E>();
		}
	}

	/**
	 * work for debug
	 */
	public void echo() {
		System.out.println(toString() + "-----");
	}

	/**
	 * swap two elements
	 * 
	 * @param org
	 *            original element index
	 * @param dst
	 *            destination index
	 * @return
	 */
	public boolean swap(int org, int dst) {
		boolean result = false;
		if (hasIndex(org) && hasIndex(dst)) {
			if (IS_IN_DROID) {
				E tmp = array.get(org);
				array.put(org, array.get(dst));
				array.put(dst, tmp);
				result = true;
			} else {
				Integer orgInteger = Integer.valueOf(org);
				Integer dstInteger = Integer.valueOf(dst);
				if (null != map.put(dstInteger,
						map.put(orgInteger, map.get(dstInteger)))) {
					result = true;
				}
			}
		}
		return result;
	}

	/**
	 * remove by index
	 * 
	 * @param index
	 * @return
	 */
	public boolean remove(int index) {
		boolean result = false;
		if (hasIndex(index)) {
			if (IS_IN_DROID) {
				array.remove(index);
				result = true;
			} else {
				if (null != map.remove(Integer.valueOf(index))) {
					result = true;
				}
			}
		}
		return result;
	}

	/**
	 * remove by element
	 * 
	 * @param e
	 * @return
	 */
	public boolean remove(E e) {
		boolean result = false;

		int index = -1;
		if (hasElement(e)) {
			if (IS_IN_DROID) {
				int size = array.size();
				for (int i = 0; i < size; i++) {
					if (array.valueAt(i).equals(e)) {
						index = array.keyAt(i);
						result = true;
						break;
					}
				}
			} else {
				Iterator<Map.Entry<Integer, E>> iterator = (Iterator<Map.Entry<Integer, E>>) map
						.entrySet().iterator();
				while (iterator.hasNext()) {
					Map.Entry<Integer, E> entry = (Map.Entry<Integer, E>) iterator
							.next();
					Integer key = (Integer) entry.getKey();
					E value = (E) entry.getValue();
					if (value.equals(e)) {
						index = key;
						result = true;
						break;
					}
				}
			}
		}

		if (result) {
			result = remove(index);
		}

		return result;
	}

	/**
	 * precheck the appropriate insert index
	 * 
	 * @return
	 */
	public int precheck() {
		int current = startIndex;

		if (IS_IN_DROID) {
			int size = array.size();
			for (int i = 0; i < size; i++) {
				if (array.keyAt(i) > current && false == hasIndex(current)) {
					break;
				}
				current++;
			}
		} else {
			Iterator<Map.Entry<Integer, E>> iterator = (Iterator<Map.Entry<Integer, E>>) map
					.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<Integer, E> entry = (Map.Entry<Integer, E>) iterator
						.next();
				Integer key = (Integer) entry.getKey();
				if (key.intValue() > current && false == hasIndex(current)) {
					break;
				}
				current++;
			}
		}

		return current;
	}

	/**
	 * add element, by auto precheck
	 * 
	 * @param e
	 * @return
	 */
	public boolean add(E e) {
		return add(precheck(), e);
	}

	/**
	 * add element by index
	 * 
	 * @param index
	 * @param e
	 * @return
	 */
	public boolean add(int index, E e) {
		boolean result = false;
		if (IS_IN_DROID) {
			array.put(index, e);
			result = true;
		} else {
			if (null != map.put(Integer.valueOf(index), e)) {
				result = true;
			}
		}
		return result;
	}

	public void clear() {
		if (IS_IN_DROID) {
			array.clear();
		} else {
			map.clear();
		}
	}

	/**
	 * has the specific index or not
	 * 
	 * @param index
	 * @return
	 */
	public boolean hasIndex(int index) {
		boolean result = false;
		if (IS_IN_DROID) {
			if (null != array.get(index)) {
				result = true;
			}
		} else {
			result = map.containsKey(Integer.valueOf(index));
		}
		return result;
	}

	/**
	 * has the specific element or not
	 * 
	 * @param e
	 * @return
	 */
	public boolean hasElement(E e) {
		boolean result = false;
		if (IS_IN_DROID) {
			int size = array.size();
			for (int i = 0; i < size; i++) {
				if (array.valueAt(i).equals(e)) {
					result = true;
					break;
				}
			}
		} else {
			result = map.containsValue(e);
		}
		return result;
	}

	public int indexOfKey(int key) {
		int result = -1;
		if (IS_IN_DROID) {
			result = array.indexOfKey(key);
		} else {
			result = toList().indexOf(get(key));
		}
		return result;
	}

	/**
	 * get element by index
	 * 
	 * @param index
	 * @return
	 */
	public E get(int index) {
		E e = null;
		if (IS_IN_DROID) {
			e = array.get(index);
		} else {
			e = map.get(Integer.valueOf(index));
		}
		return e;
	}

	/**
	 * check empty or not
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		boolean result = false;
		if (IS_IN_DROID) {
			result = (0 == array.size());
		} else {
			result = map.isEmpty();
		}
		return result;
	}

	public int size() {
		int result = -1;
		if (IS_IN_DROID) {
			result = array.size();
		} else {
			result = map.size();
		}
		return result;
	}

	@Override
	public String toString() {
		StringBuilder sBuilder = new StringBuilder();

		if (IS_IN_DROID) {
			int size = array.size();
			for (int i = 0; i < size; i++) {
				sBuilder.append(array.keyAt(i)).append(" => ")
						.append(array.valueAt(i)).append("\n");
			}
		} else {
			Iterator<Map.Entry<Integer, E>> iterator = (Iterator<Map.Entry<Integer, E>>) map
					.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<Integer, E> entry = (Map.Entry<Integer, E>) iterator
						.next();
				Integer key = (Integer) entry.getKey();
				E value = (E) entry.getValue();
				sBuilder.append(key).append(" => ").append(value).append("\n");
			}
		}
		return sBuilder.toString();
	}

	/**
	 * output the ordered element array list
	 * 
	 * @return
	 */
	public ArrayList<E> toList() {
		ArrayList<E> result = new ArrayList<E>();

		if (IS_IN_DROID) {
			int size = array.size();
			for (int i = 0; i < size; i++) {
				result.add(array.valueAt(i));
			}
		} else {
			ArrayList<Map.Entry<Integer, E>> list = new ArrayList<Map.Entry<Integer, E>>(
					map.entrySet());
			Collections.sort(list, new Comparator<Map.Entry<Integer, E>>() {

				@Override
				public int compare(Map.Entry<Integer, E> o1,
						Map.Entry<Integer, E> o2) {
					return o1.getKey().intValue() - o2.getKey().intValue();
				}
			});

			for (Map.Entry<Integer, E> entry : list) {
				result.add(entry.getValue());
			}
		}

		return result;
	}

}
