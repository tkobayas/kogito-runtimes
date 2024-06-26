/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jbpm.process;

import org.jbpm.ruleflow.core.RuleFlowProcess;
import org.jbpm.ruleflow.core.WorkflowElementIdentifierFactory;
import org.jbpm.test.util.AbstractBaseTest;
import org.jbpm.workflow.core.Node;
import org.jbpm.workflow.core.impl.ConnectionImpl;
import org.jbpm.workflow.core.node.EndNode;
import org.jbpm.workflow.core.node.StartNode;
import org.jbpm.workflow.core.node.SubProcessNode;
import org.junit.jupiter.api.Test;
import org.kie.api.definition.process.WorkflowElementIdentifier;
import org.kie.kogito.internal.process.runtime.KogitoProcessInstance;
import org.kie.kogito.internal.process.runtime.KogitoProcessRuntime;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class SubProcessTest extends AbstractBaseTest {

    private static WorkflowElementIdentifier one = WorkflowElementIdentifierFactory.fromExternalFormat("one");
    private static WorkflowElementIdentifier two = WorkflowElementIdentifierFactory.fromExternalFormat("two");
    private static WorkflowElementIdentifier three = WorkflowElementIdentifierFactory.fromExternalFormat("three");

    public void addLogger() {
        logger = LoggerFactory.getLogger(this.getClass());
    }

    @Test
    public void testNonExistentSubProcess() {
        String nonExistentSubProcessName = "nonexistent.process";
        RuleFlowProcess process = new RuleFlowProcess();
        process.setId("org.drools.core.process.process");
        process.setName("Process");
        StartNode startNode = new StartNode();
        startNode.setName("Start");
        startNode.setId(one);
        SubProcessNode subProcessNode = new SubProcessNode();
        subProcessNode.setName("SubProcessNode");
        subProcessNode.setId(two);
        subProcessNode.setProcessId(nonExistentSubProcessName);
        EndNode endNode = new EndNode();
        endNode.setName("End");
        endNode.setId(three);

        connect(startNode, subProcessNode);
        connect(subProcessNode, endNode);

        process.addNode(startNode);
        process.addNode(subProcessNode);
        process.addNode(endNode);

        KogitoProcessRuntime kruntime = createKogitoProcessRuntime(process);

        KogitoProcessInstance pi = kruntime.startProcess("org.drools.core.process.process");
        assertThat(pi.getState()).isEqualTo(KogitoProcessInstance.STATE_ERROR);
    }

    private void connect(Node sourceNode, Node targetNode) {
        new ConnectionImpl(sourceNode, Node.CONNECTION_DEFAULT_TYPE,
                targetNode, Node.CONNECTION_DEFAULT_TYPE);
    }

}
