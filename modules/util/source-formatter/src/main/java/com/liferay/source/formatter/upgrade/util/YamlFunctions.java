/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.source.formatter.upgrade.util;

import com.liferay.source.formatter.upgrade.LugbotConfig;

import java.io.InputStream;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.CustomClassLoaderConstructor;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.introspector.PropertyUtils;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

/**
 * @author Gregory Amerson
 */
public class YamlFunctions {

	public static final CustomClassLoaderConstructor constructor =
		new CustomClassLoaderConstructor(
			LugbotConfig.class, LugbotConfig.class.getClassLoader());

	public static LugbotConfig load(InputStream inputStream) {
		Yaml yaml = new Yaml(constructor);

		return yaml.load(inputStream);
	}

	public static LugbotConfig load(String content) {
		Yaml yaml = new Yaml(constructor);

		return yaml.load(content);
	}

	public static void write(
		LugbotConfig lugbotConfig, PrintWriter printWriter) {

		DumperOptions dumperOptions = new DumperOptions();

		dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
		dumperOptions.setIndent(2);
		dumperOptions.setPrettyFlow(true);

		Yaml yaml = new Yaml(
			constructor, new CustomRepresenter(dumperOptions), dumperOptions);

		yaml.dump(lugbotConfig, printWriter);
	}

	private static class CustomPropertiesUtils extends PropertyUtils {

		@Override
		public Set<Property> getProperties(Class<? extends Object> type) {
			Set<Property> properties = super.getProperties(type);

			if (LugbotConfig.Upgrade.class.equals(type)) {
				ArrayList<Property> list = new ArrayList<>(properties);

				Property first = list.remove(0);

				list.add(1, first);

				return new LinkedHashSet<>(list);
			}

			return properties;
		}

	}

	private static class CustomRepresenter extends Representer {

		public CustomRepresenter(DumperOptions dumperOptions) {
			super(dumperOptions);
		}

		protected NodeTuple representJavaBeanProperty(
			Object javaBean, Property property, Object propertyValue,
			Tag customTag) {

			if (propertyValue == null) {
				return null;
			}

			return super.representJavaBeanProperty(
				javaBean, property, propertyValue, customTag);
		}

	}

	static {
		constructor.setPropertyUtils(new CustomPropertiesUtils());
	}

}