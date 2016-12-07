package com.pjm.familyAccountWx.common.util;

import java.util.*;

/**
 * 集合类Util
 * 
 * @author PanJM
 *
 * @date 2016.11.19
 *
 */
public final class CollectionsUtil {

	/**
	 * 返回不同对象的HashMap
	 * 
	 * @return
	 */
	public static <K, V> HashMap<K, V> newHashMap() {
		return new HashMap<K, V>();
	}

	/**
	 * 返回不同对象的ArrayList
	 * 
	 * @return
	 */
	public static <T> ArrayList<T> newArrayList() {
		return new ArrayList<T>();
	}

	/**
	 * 返回不同对象的HashSet
	 * 
	 * @return
	 */
	public static <T> Set<T> newHashSet() {
		return new HashSet<T>();
	}

	/**
	 * 集合是否空判定
	 * 
	 * @param list
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(List list) {
		if (list != null && list.size() > 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 集合是否空判定
	 * 
	 * @param set
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Set set) {
		if (set != null && set.size() > 0) {
			return false;
		} else {
			return true;
		}
	}

}
