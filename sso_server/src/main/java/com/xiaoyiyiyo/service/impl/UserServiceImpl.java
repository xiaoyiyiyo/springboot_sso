package com.xiaoyiyiyo.service.impl;

import com.xiaoyiyiyo.pojo.UserDo;
import com.xiaoyiyiyo.repository.IRedisClient;
import com.xiaoyiyiyo.repository.IUserRepository;
import com.xiaoyiyiyo.service.IUserService;
import org.hibernate.Session;
import org.hibernate.jpa.HibernateEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.UUID;

/**
 * Created by xiaoyiyiyo on 2018/4/24.
 */
@Service
public class UserServiceImpl implements IUserService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private IUserRepository userRepository;


    /**
     * 此处缓存key包含password,可能需要加密  TODO
     */
    @Cacheable
    @Override
    public UserDo getUser(String userName, String password) {

        //查询User
        UserDo user = userRepository.findByAccount(userName);
        Session session = (Session)entityManager.getDelegate();
        session.evict(user);

        user.setPassword(null);
        user.setSalt(null);

        return user;
    }
}
