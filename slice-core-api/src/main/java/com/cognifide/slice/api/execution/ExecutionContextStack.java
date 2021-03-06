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

package com.cognifide.slice.api.execution;

import aQute.bnd.annotation.ProviderType;

import com.cognifide.slice.api.provider.ModelProvider;

/**
 * Execution context stack of Slice Injector. New ExecutionContext is put on this stack each time a new call
 * to ModelProvider is made. After call is finished the ExecutionContext is taken from this stack. This allows
 * Slice to build recursively complex models that need to have other models injected or changing a scope of
 * current resource by using ModelProvider in client code.
 * 
 * @deprecated This interface will be hidden from public use in next major version. Use {@link ModelProvider}
 * instead.
 */
@ProviderType
@Deprecated
public interface ExecutionContextStack {

	/**
	 * @return current ExecutionContext without removing it from a stack.
	 */
	ExecutionContext peek();

	/**
	 * Push new ExecutionContext on top of a stack.
	 * @param executionContext context to be pushed
	 */
	void push(ExecutionContext executionContext);

	/**
	 * @return ExecutionContext from top of a stack.
	 */
	ExecutionContext pop();

	/**
	 * @return current size of a stack.
	 */
	int size();

	/**
	 * @return absolute path from given path. If path is already absolute then it will be returned unchanged.
	 * If it is relative (starting with ./) then it will be appended (after removing ./) to path of current
	 * top of this stack.
	 * 
	 * InvalidExecutionContextException with be thrown in case when stack is empty and path is relative.
	 * @param path path to be transformed
	 */
	String getAbsolutePath(String path);

}
