package com.xiaoyiyiyo.repository;

import com.xiaoyiyiyo.pojo.UserDo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by xiaoyiyiyo on 2018/4/24.
 */
public interface IUserRepository extends PagingAndSortingRepository<UserDo, Long>, JpaSpecificationExecutor<UserDo> {

    //通过账号查用户信息
    UserDo findByAccount(String account);

    @Query(value = "select count(u.id) from UserDo u where u.platform = :platform and u.lastLoginDate <= :lastLoginDate")
    long countActiveUser(@Param("platform") String platform, @Param("lastLoginDate") String lastLoginDate);

    @Query(value = "select u from UserDo u where u.email like concat('%',:email, '%') or u.iphone like concat('%',:phone, '%')")
    List<UserDo> findByEmailAndPhoneLike(@Param("email") String email, @Param("phone") String phone);

    @Modifying
    @Query("update UserDo u set u.email = :email where u.id = :id")
    void updateUserEmail(@Param("id") Long id, @Param("email") String email);
}
