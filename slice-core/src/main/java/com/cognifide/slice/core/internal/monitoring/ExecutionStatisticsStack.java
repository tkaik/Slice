/*-
 * #%L
 * Slice - Core
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
package com.cognifide.slice.core.internal.monitoring;

import java.util.ArrayDeque;
import java.util.Deque;

import com.google.inject.Inject;
import com.google.inject.Key;

public class ExecutionStatisticsStack {

	private final boolean monitoringEnabled;

	private InjectorStatisticsRepository injectorStatistics;

	private Deque<InjectionMonitoringContext> monitoringContexts = new ArrayDeque<InjectionMonitoringContext>();

	private Deque<ModelUsageData> modelUsageDataStack = new ArrayDeque<ModelUsageData>();

	@Inject
	public ExecutionStatisticsStack(InjectorStatisticsRepository injectorStatistics) {
		this.injectorStatistics = injectorStatistics;
		this.modelUsageDataStack.push(this.injectorStatistics.getModelUsageDataRoot());
		this.monitoringEnabled = injectorStatistics.isEnabled();
	}

	public <T> void startMeasurement(Key<T> key) {
		if (monitoringEnabled) {
			ModelUsageData currentModelUsageData = modelUsageDataStack.peek().getChildForKey(key);

			this.monitoringContexts.push(new InjectionMonitoringContext());
			this.modelUsageDataStack.push(currentModelUsageData);
		}
	}

	public void endAndStoreMeasurement() {
		if (monitoringEnabled) {
			InjectionMonitoringContext injectionMonitoringContext = this.monitoringContexts.pop();
			this.modelUsageDataStack.pop().addTimeMeasurement(injectionMonitoringContext.getElapsedTime());
		}
	}

}