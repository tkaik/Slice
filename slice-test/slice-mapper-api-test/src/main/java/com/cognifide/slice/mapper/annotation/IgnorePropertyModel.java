package com.cognifide.slice.mapper.annotation;

/**
 * @author Mariusz Kubiś Date: 14.04.15
 */
@SliceResource(MappingStrategy.ALL)
public class IgnorePropertyModel {

	@JcrProperty
	private String text;

	@JcrProperty
	@IgnoreProperty
	private String style;

	public String getText() {
		return text;
	}

	public String getStyle() {
		return style;
	}
}
