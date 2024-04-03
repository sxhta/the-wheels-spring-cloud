package com.sxhta.cloud.content.service;

import com.sxhta.cloud.common.service.ICommonService;
import com.sxhta.cloud.content.request.ArticleRequest;
import com.sxhta.cloud.content.request.ArticleSearchRequest;
import com.sxhta.cloud.content.response.ArticleResponse;

/**
 * 平台文章服务
 */
public interface ArticleService extends ICommonService<ArticleSearchRequest, ArticleRequest, ArticleResponse> {
}
