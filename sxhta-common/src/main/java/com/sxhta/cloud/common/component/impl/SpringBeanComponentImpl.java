package com.sxhta.cloud.common.component.impl;

import com.sxhta.cloud.common.component.SpringBeanComponent;
import lombok.NonNull;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;

/**
 * spring工具类 方便在非spring管理环境中获取bean
 */
@Component
public final class SpringBeanComponentImpl implements SpringBeanComponent, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Spring应用上下文环境
     */
    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void postProcessBeanFactory(@NonNull ConfigurableListableBeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    /**
     * 获取对象
     *
     * @return Object 一个以所给名字注册的bean的实例
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T getBean(String name) throws BeansException {
        return (T) beanFactory.getBean(name);
    }

    /**
     * 获取类型为requiredType的对象
     */
    @Override
    public <T> T getBean(Class<T> clz) throws BeansException {
        return beanFactory.getBean(clz);
    }

    /**
     * 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true
     *
     * @return Boolean
     */
    @Override
    public Boolean containsBean(String name) {
        return beanFactory.containsBean(name);
    }

    /**
     * 判断以给定名字注册的bean定义是一个singleton还是一个prototype。 如果与给定名字相应的bean定义没有被找到，将会抛出一个异常（NoSuchBeanDefinitionException）
     *
     * @return Boolean
     */
    @Override
    public Boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return beanFactory.isSingleton(name);
    }

    /**
     * @return Class 注册对象的类型
     */
    @Override
    public Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        return beanFactory.getType(name);
    }

    /**
     * 如果给定的bean名字在bean定义中有别名，则返回这些别名
     */
    @Override
    public String[] getAliases(String name) throws NoSuchBeanDefinitionException {
        return beanFactory.getAliases(name);
    }

    /**
     * 获取aop代理对象
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T getAopProxy(T invoker) {
        return (T) AopContext.currentProxy();
    }
}
