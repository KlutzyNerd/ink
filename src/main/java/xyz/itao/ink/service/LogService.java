package xyz.itao.ink.service;

import com.github.pagehelper.PageInfo;
import xyz.itao.ink.domain.LogDomain;
import xyz.itao.ink.domain.params.PageParam;
import xyz.itao.ink.domain.vo.LogVo;

import java.util.List;

/**
 * @author hetao
 * @date 2018-12-05
 * @description
 */
public interface LogService {
    /**
     * 分页显示日志
     * @param pageParam 分页信息
     * @return 查找的log信息
     */
    PageInfo<LogVo> getLogs(PageParam pageParam);
}
