package com.wheels.cloud.backend.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxhta.cloud.common.exception.ServiceException;
import com.wheels.cloud.backend.entity.ArticleType;
import com.wheels.cloud.backend.mapper.ArticleTypeMapper;
import com.wheels.cloud.backend.service.IArticleTypeService;
import com.wheels.cloud.backend.vo.ArticleTypeVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleTypeServiceImpl extends ServiceImpl<ArticleTypeMapper, ArticleType> implements IArticleTypeService {

    /**
     * 新增文章类型
     *
     * @param articleType 文章类型实体类
     * @return true新增成功 false新增失败
     */
    @Override
    public Boolean saveArticleType(ArticleType articleType) {
        checkTypeNameIsExist(articleType.getTypeName());
        return save(articleType);
    }

    /**
     * 删除文章类型
     *
     * @param articleTypeCode 文章类型编号
     * @return true删除成功 false删除失败
     */
    @Override
    public Boolean deleteArticleType(String articleTypeCode) {
        final var articleTypeLqw = new LambdaQueryWrapper<ArticleType>();
        articleTypeLqw.eq(ArticleType::getTypeCode, articleTypeCode);
        final var articleType = getOne(articleTypeLqw);
        if (ObjectUtil.isNull(articleType)) {
            throw new ServiceException("该类型数据异常，请联系管理员");
        }
        return removeById(articleType);
    }

    /**
     * 修改文章类型
     *
     * @param articleType 文章类型实体类
     * @return true修改成功 false修改失败
     */
    @Override
    public Boolean updateArticleType(ArticleType articleType) {
        checkTypeNameIsExist(articleType.getTypeName());
        return updateById(articleType);
    }

    @Override
    public List<ArticleTypeVo> selectArticleTypeAll() {
        return null;
    }

    @Override
    public ArticleTypeVo selectArticleTypeInfo(String articleTypeCode) {
        final var articleTypeVo = new ArticleTypeVo();
        final var articleTypeLqw = new LambdaQueryWrapper<ArticleType>();
        articleTypeLqw.eq(ArticleType::getTypeCode, articleTypeCode);
        final var articleType = getOne(articleTypeLqw);
        if (ObjectUtil.isNotNull(articleType)) {
            BeanUtils.copyProperties(articleType, articleTypeVo);
            //TODO:创建人信息
        }
        return articleTypeVo;
    }


    /**
     * 查验新增或修改文章类型名称是否存在
     *
     * @param typeName 类型名称
     */
    private void checkTypeNameIsExist(String typeName) {
        final var articleTypeLqw = new LambdaQueryWrapper<ArticleType>();
        articleTypeLqw.eq(ArticleType::getTypeName, typeName);
        final var articleTypeByName = getOne(articleTypeLqw);
        if (ObjectUtil.isNotNull(articleTypeByName)) {
            throw new ServiceException("该类型名称已存在");
        }
    }

}
