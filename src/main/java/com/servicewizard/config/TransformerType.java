package com.servicewizard.config;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public enum TransformerType {
	CUSTOM, ANGULAR, DEFAULT_HTML, DEFAULT_MARKDOWN;

	public static String getNameString() {
		List<String> names = new LinkedList<>();
		for (TransformerType type : TransformerType.values())
			names.add(type.name());
		return names.stream().collect(Collectors.joining(", "));
	}
}