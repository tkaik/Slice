/*-
 * #%L
 * Slice - Core API
 * %%
 * Copyright (C) 2012 Cognifide Limited
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

package com.cognifide.slice.api.provider;

import java.util.Map;

import aQute.bnd.annotation.ConsumerType;

/**
 * An optional service creating a component definition. If there is no OSGi service implementing this
 * interface, Slice will use an administrative resource resolver to create a component definition. This may be
 * used as a bridge between already existing component definition provider (eg. ComponentManager in AEM) and
 * Slice.
 * 
 * @author Tomasz Rękawek
 * @deprecated Please use {@link ComponentDefinitionProvider}
 */
@ConsumerType
@Deprecated
public interface ComponentDefinitionResolver {

	/**
	 * Returns a definition of a component defined by specified resourceType in form of its properties map.
	 * 
	 * @param resourceType resource type of a component
	 * @return Map of resource properties or null if there's no resource under path specified in resourceType
	 * parameter.
	 */
	Map<String, Object> getComponentDefinition(String resourceType);

}
