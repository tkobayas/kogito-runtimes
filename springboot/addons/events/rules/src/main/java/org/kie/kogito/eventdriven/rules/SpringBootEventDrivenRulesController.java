/*
 * Copyright 2021 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kie.kogito.eventdriven.rules;

import java.util.List;

import javax.annotation.PostConstruct;

import org.kie.kogito.conf.ConfigBean;
import org.kie.kogito.event.EventEmitter;
import org.kie.kogito.event.EventReceiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SpringBootEventDrivenRulesController extends EventDrivenRulesController {

    @Autowired
    public SpringBootEventDrivenRulesController(List<EventDrivenQueryExecutor> executors, ConfigBean config, EventEmitter eventEmitter, EventReceiver eventReceiver) {
        super(executors, config, eventEmitter, eventReceiver);
    }

    @PostConstruct
    private void onPostConstruct() {
        subscribe();
    }
}