package com.marshal.epoch.mybatis.domain;

import lombok.Data;
import tk.mybatis.mapper.annotation.Version;

import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 * 审计对象
 * </pre>
 *
 * @author Marshal
 * @date 2019/11/1
 */
@Data
public class AuditDomain implements Serializable {

    public static String FILED_CREATED_BY = "createdBy";
    public static String FILED_CREATION_DATE = "creationDate";
    public static String FILED_LAST_UPDATED_BY = "lastUpdatedBy";
    public static String FILED_LAST_UPDATE_DATE = "lastUpdateDate";

    /**
     * 创建者
     */
    private Long createdBy;

    /**
     * 创建时间
     */
    private Date creationDate;

    /**
     * 最后一次更新者
     */
    private Long lastUpdatedBy;

    /**
     * 最后一次更新时间
     */
    private Date lastUpdateDate;

    /**
     * 乐观锁关键字
     */
    @Version
    private Long objectVersion;

}