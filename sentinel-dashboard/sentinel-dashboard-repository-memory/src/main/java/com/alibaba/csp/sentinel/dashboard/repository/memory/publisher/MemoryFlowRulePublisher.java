package com.alibaba.csp.sentinel.dashboard.repository.memory.publisher;

import com.alibaba.csp.sentinel.dashboard.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.repository.memory.MemoryConfig;
import com.alibaba.csp.sentinel.dashboard.repository.memory.MemoryRulePublisher;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

/**
 * @author cdfive
 */
@ConditionalOnBean(MemoryConfig.class)
@Component
public class MemoryFlowRulePublisher extends MemoryRulePublisher<FlowRuleEntity> {

}
