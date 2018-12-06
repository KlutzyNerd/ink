package xyz.itao.ink.domain.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author hetao
 * @date 2018-12-05
 * @description
 */
@Data
@Builder
public class CommentVo {
    /**
     * 评论的id
     */
    private Long id;


    /**
     * 是否处于激活状态
     */
    private Boolean active;

    /**
     * 品论所属内容
     */
    private Long contentId;

    /**
     * 评论的作者id
     */
    private Long authorId;

    /**
     * 父级评论id
     */
    private Long parentId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论类型
     */
    private String type;

    /**
     * 评论状态
     */
    private String status;
}