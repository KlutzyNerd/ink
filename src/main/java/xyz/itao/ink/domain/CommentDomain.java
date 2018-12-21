package xyz.itao.ink.domain;

import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import xyz.itao.ink.repository.UserRepository;

import java.util.Date;

/**
 * @author hetao
 * @date 2018-12-10
 * @description
 */
@Data
@Builder
@Accessors(chain = true)
public class CommentDomain extends BaseDomain {
    /**
     * 评论的id
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

    /**
     * UserRepository 对象
     */
    private UserRepository userRepository;

    /**
     * 作者姓名
     */
    private String author;

    /**
     * 作者邮箱
     */
    private String mail;

    /**
     * 作者主页
     */
    private String url;

    public String getAuthor() {
        if(author==null){
            refreshAuthor();
        }
        return author;
    }

    public String getMail() {
        if(mail==null){
            refreshAuthor();
        }
        return mail;
    }

    public String getUrl() {
        if(url==null){
            refreshAuthor();
        }
        return url;
    }

    private void refreshAuthor(){
        if(authorId==null || author!=null || mail!=null || url!=null){
            return ;
        }
        UserDomain userDomain = userRepository.loadActiveUserDomainById(authorId);
        author = userDomain.getDisplayName();
        url = userDomain.getHomeUrl();
        mail = userDomain.getEmail();
    }
}
