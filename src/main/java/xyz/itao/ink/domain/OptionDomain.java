package xyz.itao.ink.domain;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author hetao
 * @date 2018-12-11
 * @description
 */
@Data
@Builder
@Accessors(chain = true)
public class OptionDomain extends BaseDomain{
    /**
     * 主键
     */
    private Long id;

    /**
     * 是否被删除
     */
    private Boolean deleted;

    /**
     * 是否处于激活状态
     */
    private Boolean active;

    /**
     * 配置名称
     */
    private String name;

    /**
     * 配置值
     */
    private String value;

    /**
     * 配置详情
     */
    private String detail;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 被谁创建
     */
    private Long createBy;

    /**
     * 被谁修改
     */
    private Long updateBy;
}
