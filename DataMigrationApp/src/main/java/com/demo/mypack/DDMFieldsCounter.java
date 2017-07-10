package com.demo.mypack;

import java.util.HashMap;

/**
 * @author Eduardo Lundgren
 * @author Marcellus Tavares
 */
public class DDMFieldsCounter extends HashMap<Object, Integer> {

	@Override
	public Integer get(Object key) {
		if (!containsKey(key)) {
			put(key, 0);
		}

		return super.get(key);
	}

	public int incrementKey(Object key) {
		int value = get(key);

		put(key, ++value);

		return value;
	}

}