package com.shawn.jooo.framework.base;


import com.shawn.jooo.framework.mybatis.condition.Example;
import com.shawn.jooo.framework.mybatis.reflect.BeanReflections;
import com.shawn.jooo.framework.mybatis.reflect.ClassTypeAdapt;
import com.shawn.jooo.framework.core.page.Page;
import com.shawn.jooo.framework.core.page.PageImpl;
import com.shawn.jooo.framework.core.page.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.Id;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * 基础service实现，参考JPA实现
 *
 * @author shawn
 */
public abstract class BaseServiceImpl<T, ID extends Serializable> extends ClassTypeAdapt<T> implements BaseService<T, ID>, ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(BaseService.class);

    private Class<T> type;

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;

    }

    public BaseServiceImpl() {
        type = getClassType();
        if (type == null) {
            throw new RuntimeException("继承类没有加泛型!");
        }
    }

    public BaseMapper<T, ID> getMapper() {
        BaseMapper<T, ID> mapper = null;
        String entityName = StringUtils.uncapitalize(type.getSimpleName());
        if (applicationContext.containsBean(entityName + "Mapper")) {
            Object bean = applicationContext.getBean(entityName + "Mapper");
            if (bean != null) {
                mapper = (BaseMapper<T, ID>) bean;
            } else {
                throw new RuntimeException("bean not exist by name:" + entityName + "Mapper");
            }
        } else if (applicationContext.containsBean(entityName + "Dao")) {
            Object bean = applicationContext.getBean(entityName + "Dao");
            if (bean != null) {
                mapper = (BaseMapper<T, ID>) bean;
            } else {
                throw new RuntimeException("bean not exist by name:" + entityName + "Dao");
            }
        } else {
            throw new RuntimeException(entityName + "Mapper or " + entityName + "Dao bean not exist ");
        }
        return mapper;
    }


    /**
     * 查询结果数量
     */
    @Override
    public long count() {
        return this.getMapper().countByExample(null);
    }

    /**
     * 根据条件，返回数据数量
     *
     * @param example
     * @return
     */
    @Override
    public long count(Example example) {
        return getMapper().countByExample(example);
    }

    /**
     * 根据条件，判断是否存在
     *
     * @param example
     * @return
     */
    @Override
    public boolean exists(Example example) {
        return getMapper().countByExample(example) > 0;
    }

    /**
     * 根据id判断是否存在
     *
     * @param id
     * @return
     */
    @Override
    public boolean existsById(ID id) {
        return getMapper().selectByPrimaryKey(id) != null;
    }

    /**
     * 根据条件，查询一条记录
     *
     * @param example
     * @return
     */
    @Override
    public Optional<T> findOne(Example example) {
        List<T> list = getMapper().selectByExample(example);
        if (!CollectionUtils.isEmpty(list)) {
            if (list.size() == 1) {
                return Optional.ofNullable(list.get(0));
            } else if (list.size() > 1) {
                logger.warn("has duplicate items result more than one: {}", list.size());
                return Optional.ofNullable(list.get(0));
            }
        }
        return Optional.empty();
    }


    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @Override
    public Optional<T> findById(ID id) {
        T data = getMapper().selectByPrimaryKey(id);
        return Optional.ofNullable(data);
    }

    @Override
    public T getOne(ID id) {
        return findById(id).get();
    }


    /**
     * 根据id列表查询
     *
     * @param ids
     * @return
     */
    @Override
    public List<T> findAllById(List<ID> ids) {
        Stream<ID> stream = StreamSupport.stream(ids.spliterator(), false);
        List<T> list = stream.map(id -> getMapper().selectByPrimaryKey(id)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(list)) {
            list = Collections.emptyList();
        }
        return list;
    }


    /**
     * 查询所有
     *
     * @return
     */
    @Override
    public List<T> findAll() {
        List<T> list = getMapper().selectByExample(null);
        if (CollectionUtils.isEmpty(list)) {
            list = Collections.emptyList();
        }
        return list;
    }

    /**
     * 查询所有，分页
     *
     * @param pageable
     * @return
     */
    @Override
    public Page<T> findAll(Pageable pageable) {
        List<T> list = getMapper().selectPageByExample(null, pageable);
        if (CollectionUtils.isEmpty(list)) {
            list = Collections.emptyList();
        }
        return new PageImpl(list, pageable);
    }


    /**
     * 根据条件，查询记录
     *
     * @param example
     * @return
     */
    @Override
    public List<T> findAll(Example example) {
        List<T> list = getMapper().selectByExample(example);
        if (CollectionUtils.isEmpty(list)) {
            list = Collections.emptyList();
        }
        return list;
    }

    /**
     * 根据条件，查询记录，并分页
     *
     * @param example
     * @param pageable
     * @return
     */
    @Override
    public Page<T> findAll(Example example, Pageable pageable) {
        List<T> list = getMapper().selectPageByExample(example, pageable);
        if (CollectionUtils.isEmpty(list)) {
            list = Collections.emptyList();
        }
        return new PageImpl<T>(list, pageable);
    }

    /**
     * 插入一条记录
     *
     * @param entity
     */
    @Override
    public int insert(T entity) {
        return getMapper().insert(entity);
    }

    /**
     * 更新一条记录
     *
     * @param entity
     */
    @Override
    public int update(T entity) {
        return getMapper().updateByPrimaryKeySelective(entity);
    }

    /**
     * 根据查询条件，更新一条记录
     *
     * @param entity
     * @param example
     */
    @Override
    public int update(T entity, Example example) {
        return getMapper().updateByExampleSelective(entity, example);
    }

    /**
     * 存在则修改记录，不存在则新增记录
     *
     * @param entity
     */
    @Override
    @Transactional
    public T save(T entity) {
        ID id = getPrimaryKey(entity);
        if (id == null) {
            insert(entity);
            return entity;
        } else {
            T update = getMapper().selectByPrimaryKey(id);
            if (update == null) { //新增
                insert(entity);
                return entity;
            } else {    //修改
                BeanReflections.copyPropertiesIgnoreNull(entity, update);
                update(update);
                return update;
            }
        }

    }

    /**
     * 批量插入
     *
     * @param entities
     */
    @Override
    @Transactional
    public void saveAllInBatch(List<T> entities) {
        final int batch = 5000;
        if (!CollectionUtils.isEmpty(entities)) {
            while (entities.size() > batch) {
                List inserts = entities.subList(0, batch);
                entities = entities.subList(batch, entities.size());
                getMapper().insertInBatch(inserts);
            }
            if (entities.size() > 0) {
                getMapper().insertInBatch(entities);
            }
        }
    }

    /**
     * 批量插入
     *
     * @param entities
     */
    @Override
    @Transactional
    public void saveAllInBatch(List<T> entities, int batchSize) {
        if (!CollectionUtils.isEmpty(entities)) {
            while (entities.size() > batchSize) {
                List inserts = entities.subList(0, batchSize);
                entities = entities.subList(batchSize, entities.size());
                getMapper().insertInBatch(inserts);
            }
            if (entities.size() > 0) {
                getMapper().insertInBatch(entities);
            }
        }
    }


    /**
     * 根据ID删除
     *
     * @param id
     */
    @Override
    public void deleteById(ID id) {
        getMapper().deleteByPrimaryKey(id);
    }


    /**
     * 根据条件，删除一条记录
     *
     * @param example
     */
    @Override
    public void delete(Example example) {
        getMapper().deleteByExample(example);
    }

    /**
     * 删除所有
     */
    @Override
    public void deleteAllInBatch() {
        getMapper().deleteByExample(null);
    }

    /**
     * 删除列表数据
     *
     * @param ids
     */
    @Override
    @Transactional
    public void deleteInBatch(List<ID> ids) {
        ids.forEach(id -> getMapper().deleteByPrimaryKey(id));
    }

    /**
     * getPrimaryKey
     *
     * @param entity
     * @return
     */
    private ID getPrimaryKey(T entity) {
        Class<T> clazz = getClassType();
        List<Field> fields = BeanReflections.getFields(clazz);
        Optional<Field> optional = fields.stream().filter(field -> field.isAnnotationPresent(Id.class)).findFirst();
        Field field = optional.orElseThrow(() -> new RuntimeException(clazz.getName() + "必须存在@Id注解字段"));
        ID id = (ID) BeanReflections.readField(field, entity);
        return id;
    }

    private void setPrimaryKey(T entity, int lastId) {
        Class<T> clazz = getClassType();
        List<Field> fields = BeanReflections.getFields(clazz);
        Optional<Field> optional = fields.stream().filter(field -> field.isAnnotationPresent(Id.class)).findFirst();
        Field field = optional.orElseThrow(() -> new RuntimeException(clazz.getName() + "必须存在@Id注解字段"));
        ID id = (ID) BeanReflections.readField(field, entity);
        if (id instanceof Integer || id instanceof Long) {
            BeanReflections.writeField(field, lastId, entity);
        }
        if(id instanceof Short) {
            BeanReflections.writeField(field, (short)lastId, entity);
        }
    }

}
