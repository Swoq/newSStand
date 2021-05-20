package com.swoqe.newSStand.util;

public enum OrderBy {
	PRICE,
	NAME,
	PUBLICATION_DATE;

	public static OrderBy safeValueOf(final String value) {
		try {
			return OrderBy.valueOf(value.toUpperCase());
		} catch (IllegalArgumentException | NullPointerException e) {
			return NAME;
		}
	}
}