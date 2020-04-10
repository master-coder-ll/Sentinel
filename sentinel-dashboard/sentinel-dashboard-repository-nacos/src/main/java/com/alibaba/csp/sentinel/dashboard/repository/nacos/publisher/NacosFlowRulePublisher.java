package com.alibaba.csp.sentinel.dashboard.repository.nacos.publisher;

import com.alibaba.csp.sentinel.dashboard.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.repository.nacos.NacosConfig;
import com.alibaba.csp.sentinel.dashboard.repository.nacos.NacosRulePublisher;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

/**
 * @author cdfive
 */
@ConditionalOnBean(NacosConfig.class)
@Component
public class NacosFlowRulePublisher extends NacosRulePublisher<FlowRuleEntity> {

}
