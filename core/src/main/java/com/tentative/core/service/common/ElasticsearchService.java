package com.tentative.core.service.common;

import javax.validation.constraints.NotNull;

/**
 * es的相关封装
 *
 * @author Shinobu
 * @since 2018/8/27
 */
public interface ElasticsearchService {

    /**
     * 新增索引
     *
     * @param index 索引名称
     */
    void createIndex(@NotNull String index);

    /**
     * 删除索引
     *
     * @param index 索引名称
     */
    void deleteIndex(@NotNull String index);

}
