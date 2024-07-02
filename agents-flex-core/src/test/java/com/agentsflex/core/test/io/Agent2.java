package com.agentsflex.core.test.io;

import com.agentsflex.core.agent.DefaultAgent;
import com.agentsflex.core.chain.Chain;

public class Agent2 extends DefaultAgent {

    public Agent2(Object id) {
        super(id);
    }

    @Override
    public Object execute(Object parameter, Chain chain) {
        return id + ":" + parameter;
    }
}
