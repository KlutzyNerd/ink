package xyz.itao.ink.repository.impl;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.log.LogDelegateFactory;
import org.springframework.stereotype.Repository;
import xyz.itao.ink.dao.LogMapper;
import xyz.itao.ink.domain.BaseDomain;
import xyz.itao.ink.domain.LogDomain;
import xyz.itao.ink.domain.entity.Log;
import xyz.itao.ink.exception.ExceptionEnum;
import xyz.itao.ink.exception.InnerException;
import xyz.itao.ink.repository.AbstractBaseRepository;
import xyz.itao.ink.repository.LogRepository;

import java.util.List;

/**
 * @author hetao
 * @date 2018-12-05
 * @description
 */
@Repository("logRepository")
public class LogRepositoryImpl extends AbstractBaseRepository<LogDomain, Log> implements LogRepository {
    @Autowired
    LogMapper logMapper;

    @Override
    protected boolean doSave(Log entity) {
        return logMapper.insert(entity);
    }

    @Override
    protected Log doLoadByNoNullProperties(Log entity) {
        return null;
    }

    @Override
    protected boolean doUpdate(Log entity) {
        throw new InnerException(ExceptionEnum.ILLEGAL_OPERATION);
    }

    @Override
    protected LogDomain doAssemble(Log entity) {
        return LogDomain
                .builder()
                .id(entity.getId())
                .deleted(entity.getDeleted())
                .createTime(entity.getCreateTime())
                .createBy(entity.getCreateBy())
                .updateTime(entity.getUpdateTime())
                .updateBy(entity.getUpdateBy())
                .active(entity.getActive())
                .ip(entity.getIp())
                .agent(entity.getAgent())
                .data(entity.getData())
                .action(entity.getAction())
                .userId(entity.getUserId())
                .build();
    }

    @Override
    protected Log doExtract(LogDomain domain) {
        return  Log
                .builder()
                .id(domain.getId())
                .deleted(domain.getDeleted())
                .createTime(domain.getCreateTime())
                .createBy(domain.getCreateBy())
                .updateTime(domain.getUpdateTime())
                .updateBy(domain.getUpdateBy())
                .active(domain.getActive())
                .ip(domain.getIp())
                .agent(domain.getAgent())
                .data(domain.getData())
                .action(domain.getAction())
                .userId(domain.getUserId())
                .build();

    }

    @Override
    public List<LogDomain> loadAllLogs() {
        List<Log> logs = logMapper.selectAllLogs();
        List<LogDomain> logDomains = Lists.newArrayList();
        for(Log log : logs){
            logDomains.add(assemble(log));
        }
        return logDomains;
    }
}