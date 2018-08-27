package com.tentative.core.service.common.impl;

import com.tentative.core.service.common.ElasticsearchService;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.indices.CreateIndex;
import org.elasticsearch.index.mapper.RootObjectMapper;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.io.IOException;

/**
 * @author Shinobu
 * @since 2018/8/27
 */
@Service
public class ElasticsearchServiceImpl implements ElasticsearchService, DisposableBean {

    @Resource
    private JestClient jestClient;

    /**
     * 新增索引
     *
     * @param index 索引名称
     */
    @Override
    public void createIndex(String index) {

        try {
            JestResult result = jestClient.execute(new CreateIndex.Builder("tentative").build());
            if (!result.isSucceeded()) {
                throw new RuntimeException("创建索引失败");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 删除索引
     *
     * @param index 索引名称
     */
    @Override
    public void deleteIndex(@NotNull String index) {
    }


    /**
     * bean销毁时关闭client
     */
    @Override
    public void destroy() throws Exception {
    }
}
