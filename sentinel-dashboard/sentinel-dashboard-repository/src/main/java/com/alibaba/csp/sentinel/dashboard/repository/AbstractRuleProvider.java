/*
 * Copyright 1999-2020 Alibaba Group Holding Ltd.
 *
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
 */
package com.alibaba.csp.sentinel.dashboard.repository;

import com.alibaba.csp.sentinel.dashboard.entity.discovery.AppManagement;
import com.alibaba.csp.sentinel.dashboard.entity.discovery.MachineInfo;
import com.alibaba.csp.sentinel.dashboard.entity.rule.RuleEntity;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cdfive
 */
public abstract class AbstractRuleProvider<T extends RuleEntity> implements DynamicRuleProvider<T> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AppManagement appManagement;

    @Autowired
    private Converter<String, List<T>> ruleDecoder;

    @Autowired
    private RuleKeyBuilder<T> ruleKeyBuilder;

    @Override
    public List<T> getRules(String app) throws Exception {
        if (StringUtil.isBlank(app)) {
            return new ArrayList<>();
        }

        List<MachineInfo> machineInfos = appManagement.getDetailApp(app).getMachines()
                .stream()
                .filter(MachineInfo::isHealthy)
                .sorted((e1, e2) -> Long.compare(e2.getLastHeartbeat(), e1.getLastHeartbeat())).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(machineInfos)) {
            return new ArrayList<>();
        } else {
            MachineInfo machine = machineInfos.get(0);
            return this.getRules(machine.getApp(), machine.getIp(), machine.getPort());
        }
    }

    @Override
    public List<T> getRules(String app, String ip, Integer port) throws Exception {
        String ruleStr = fetchRules(app, ip, port);
        if (StringUtil.isEmpty(ruleStr)) {
            return new ArrayList<>();
        }

        return ruleDecoder.convert(ruleStr);
    }

    protected String buildRuleKey(String app, String ip, Integer port) {
        return ruleKeyBuilder.buildRuleKey(app, ip, port);
    }

    protected abstract String fetchRules(String app, String ip, Integer port) throws Exception;
}
