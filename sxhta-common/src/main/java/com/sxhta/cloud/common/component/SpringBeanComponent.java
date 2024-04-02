package com.sxhta.cloud.common.component;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;

/**
 * spring工具类 方便在非spring管理环境中获取bean
 */
public interface SpringBeanComponent extends BeanFactoryPostProcessor {

    /**
     * 获取对象
     *
     * @return Object 一个以所给名字注册的bean的实例
     */
    <T> T getBean(String name);

    /**
     * 获取类型为requiredType的对象
     */
    <T> T getBean(Class<T> clz);

    /**
     * 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true
     *
     * @return Boolean
     */
    Boolean containsBean(String name);

    /**
     * 判断以给定名字注册的bean定义是一个singleton还是一个prototype。 如果与给定名字相应的bean定义没有被找到，将会抛出一个异常（NoSuchBeanDefinitionException）
     *
     * @return Boolean
     */
    Boolean isSingleton(String name) throws NoSuchBeanDefinitionException;

    /**
     * @return Class 注册对象的类型
     */
    Class<?> getType(String name) throws NoSuchBeanDefinitionException;

    /**
     * 如果给定的bean名字在bean定义中有别名，则返回这些别名
     */
    String[] getAliases(String name) throws NoSuchBeanDefinitionException;

    /**
     * 获取aop代理对象
     */
    <T> T getAopProxy(T invoker);
}
