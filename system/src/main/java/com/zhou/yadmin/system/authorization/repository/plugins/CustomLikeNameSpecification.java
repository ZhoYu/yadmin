/**
 * <p>
 * 文件名称:    CustomLikeNameSpecification
 * </p>
 */
package com.zhou.yadmin.system.authorization.repository.plugins;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

import com.google.common.collect.Lists;
import com.zhou.yadmin.common.constants.CommonConstant;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/28 23:08
 */
public class CustomLikeNameSpecification<T> implements Specification<T>
{
    private static final long serialVersionUID = -2314833417563118597L;
    private static final String ATTRIBUTE_NAME = "name";

    private String name;

    public CustomLikeNameSpecification(String name)
    {
        this.name = name;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb)
    {
        List<Predicate> list = Lists.newArrayList();
        if (!ObjectUtils.isEmpty(name))
        {
            // 模糊查找
            list.add(cb.like(root.get(ATTRIBUTE_NAME).as(String.class),
              CommonConstant.PERCENT_SIGN_DELIMITER + name + CommonConstant.PERCENT_SIGN_DELIMITER));
        }
        Predicate[] p = new Predicate[list.size()];
        return cb.and(list.toArray(p));
    }
}
