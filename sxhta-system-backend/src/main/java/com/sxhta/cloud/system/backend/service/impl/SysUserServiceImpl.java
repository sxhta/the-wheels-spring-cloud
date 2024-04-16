package com.sxhta.cloud.system.backend.service.impl;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.sxhta.cloud.common.component.BeanValidatorComponent;
import com.sxhta.cloud.common.constant.UserConstants;
import com.sxhta.cloud.common.exception.ServiceException;
import com.sxhta.cloud.common.utils.CommonStringUtil;
import com.sxhta.cloud.remote.domain.SysRole;
import com.sxhta.cloud.remote.domain.SysUser;
import com.sxhta.cloud.remote.util.AdminChecker;
import com.sxhta.cloud.security.datascop.annotation.DataScope;
import com.sxhta.cloud.security.service.SecurityService;
import com.sxhta.cloud.system.backend.domain.SysPost;
import com.sxhta.cloud.system.backend.domain.SysUserPost;
import com.sxhta.cloud.system.backend.domain.SysUserRole;
import com.sxhta.cloud.system.backend.mapper.*;
import com.sxhta.cloud.system.backend.service.ISysUserService;
import com.sxhta.cloud.system.backend.service.SysConfigService;
import jakarta.inject.Inject;
import jakarta.validation.Validator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户 业务层处理
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper,SysUser> implements ISysUserService {

    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Inject
    protected Validator validator;

    @Inject
    private SysUserMapper userMapper;

    @Inject
    private SysRoleMapper roleMapper;

    @Inject
    private SysPostMapper postMapper;

    @Inject
    private SysUserRoleMapper userRoleMapper;

    @Inject
    private SysUserPostMapper userPostMapper;

    @Inject
    private SysConfigService configService;

    @Inject
    private SecurityService securityService;

    @Inject
    private BeanValidatorComponent beanValidatorComponent;

    /**
     * 根据条件分页查询用户列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectUserList(SysUser user) {
        return userMapper.selectUserList(user);
    }

    /**
     * 根据条件分页查询已分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectAllocatedList(SysUser user) {
        return userMapper.selectAllocatedList(user);
    }

    /**
     * 根据条件分页查询未分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectUnallocatedList(SysUser user) {
        return userMapper.selectUnallocatedList(user);
    }

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserByUserName(String userName) {
        return userMapper.selectUserByUserName(userName);
    }

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserById(Long userId) {
        return userMapper.selectUserById(userId);
    }

    /**
     * 查询用户所属角色组
     *
     * @param userName 用户名
     * @return 结果
     */
    @Override
    public String selectUserRoleGroup(String userName) {
        final var list = roleMapper.selectRolesByUserName(userName);
        if (CollectionUtils.isEmpty(list)) {
            return StringUtils.EMPTY;
        }
        return list.stream().map(SysRole::getRoleName).collect(Collectors.joining(","));
    }

    /**
     * 查询用户所属岗位组
     *
     * @param userName 用户名
     * @return 结果
     */
    @Override
    public String selectUserPostGroup(String userName) {
        final var list = postMapper.selectPostsByUserName(userName);
        if (CollectionUtils.isEmpty(list)) {
            return StringUtils.EMPTY;
        }
        return list.stream().map(SysPost::getPostName).collect(Collectors.joining(","));
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public Boolean checkUserNameUnique(SysUser user) {
        final var userId = ObjectUtil.isNull(user.getUserId()) ? -1L : user.getUserId();
        final var info = userMapper.checkUserNameUnique(user.getUserName());
        if (ObjectUtil.isNotNull(info) && !info.getUserId().equals(userId)) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验手机号码是否唯一
     *
     * @param user 用户信息
     */
    @Override
    public Boolean checkPhoneUnique(SysUser user) {
        final var userId = ObjectUtil.isNull(user.getUserId()) ? -1L : user.getUserId();
        final var info = userMapper.checkPhoneUnique(user.getPhonenumber());
        if (ObjectUtil.isNotNull(info) && !info.getUserId().equals(userId)) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     */
    @Override
    public Boolean checkEmailUnique(SysUser user) {
        final var userId = ObjectUtil.isNull(user.getUserId()) ? -1L : user.getUserId();
        final var info = userMapper.checkEmailUnique(user.getEmail());
        if (ObjectUtil.isNotNull(info) && !info.getUserId().equals(userId)) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验用户是否允许操作
     *
     * @param user 用户信息
     */
    @Override
    public void checkUserAllowed(SysUser user) {
        if (ObjectUtil.isNotNull(user.getUserId()) && AdminChecker.isAdminUser(user.getUserId())) {
            throw new ServiceException("不允许操作超级管理员用户");
        }
    }

    /**
     * 校验用户是否有数据权限
     *
     * @param userId 用户id
     */
    @Override
    public void checkUserDataScope(Long userId) {
        if (!AdminChecker.isAdminUser(userId)) {
            final var user = new SysUser();
            user.setUserId(userId);
            final var users = selectUserList(user);
            if (CommonStringUtil.isEmpty(users)) {
                throw new ServiceException("没有权限访问用户数据！");
            }
        }
    }

    /**
     * 新增保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean insertUser(SysUser user) {
        // 新增用户信息
        final var rows = userMapper.insertUser(user);
        // 新增用户岗位关联
        insertUserPost(user);
        // 新增用户与角色管理
        insertUserRole(user);
        return SqlHelper.retBool(rows);
    }

    /**
     * 注册用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public Boolean registerUser(SysUser user) {
        return userMapper.insertUser(user) > 0;
    }

    /**
     * 修改保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateUser(SysUser user) {
        final var userId = user.getUserId();
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 新增用户与角色管理
        insertUserRole(user);
        // 删除用户与岗位关联
        userPostMapper.deleteUserPostByUserId(userId);
        // 新增用户与岗位管理
        insertUserPost(user);
        return SqlHelper.retBool(userMapper.updateUser(user));
    }

    /**
     * 用户授权角色
     *
     * @param userId  用户ID
     * @param roleIds 角色组
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertUserAuth(Long userId, Long[] roleIds) {
        userRoleMapper.deleteUserRoleByUserId(userId);
        insertUserRole(userId, roleIds);
    }

    /**
     * 修改用户状态
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public Boolean updateUserStatus(SysUser user) {
        return SqlHelper.retBool(userMapper.updateUser(user));
    }

    /**
     * 修改用户基本信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public Boolean updateUserProfile(SysUser user) {
        return SqlHelper.retBool(userMapper.updateUser(user));
    }

    /**
     * 修改用户头像
     *
     * @param userName 用户名
     * @param avatar   头像地址
     * @return 结果
     */
    @Override
    public Boolean updateUserAvatar(String userName, String avatar) {
        return userMapper.updateUserAvatar(userName, avatar) > 0;
    }

    /**
     * 重置用户密码
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public Boolean resetPwd(SysUser user) {
        return SqlHelper.retBool(userMapper.updateUser(user));
    }

    /**
     * 重置用户密码
     *
     * @param userName 用户名
     * @param password 密码
     * @return 结果
     */
    @Override
    public Boolean resetUserPwd(String userName, String password) {
        return SqlHelper.retBool(userMapper.resetUserPwd(userName, password));
    }

    /**
     * 新增用户角色信息
     *
     * @param user 用户对象
     */
    public void insertUserRole(SysUser user) {
        this.insertUserRole(user.getUserId(), user.getRoleIds());
    }

    /**
     * 新增用户岗位信息
     *
     * @param user 用户对象
     */
    public void insertUserPost(SysUser user) {
        final var posts = user.getPostIds();
        if (ObjectUtil.isNotEmpty(posts)) {
            // 新增用户与岗位管理
            final var list = new ArrayList<SysUserPost>();
            for (final var postId : posts) {
                final var up = new SysUserPost();
                up.setUserId(user.getUserId());
                up.setPostId(postId);
                list.add(up);
            }
            userPostMapper.batchUserPost(list);
        }
    }

    /**
     * 新增用户角色信息
     *
     * @param userId  用户ID
     * @param roleIds 角色组
     */
    public void insertUserRole(Long userId, Long[] roleIds) {
        if (ObjectUtil.isNotEmpty(roleIds)) {
            // 新增用户与角色管理
            final var list = new ArrayList<SysUserRole>();
            for (final var roleId : roleIds) {
                final var ur = new SysUserRole();
                ur.setUserId(userId);
                ur.setRoleId(roleId);
                list.add(ur);
            }
            userRoleMapper.batchUserRole(list);
        }
    }

    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteUserById(Long userId) {
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 删除用户与岗位表
        userPostMapper.deleteUserPostByUserId(userId);
        return userMapper.deleteUserById(userId);
    }

    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteUserByIds(Long[] userIds) {
        for (final var userId : userIds) {
            final var systemUser = new SysUser();
            systemUser.setUserId(userId);
            checkUserAllowed(systemUser);
            checkUserDataScope(userId);
        }
        // 删除用户与角色关联
        userRoleMapper.deleteUserRole(userIds);
        // 删除用户与岗位关联
        userPostMapper.deleteUserPost(userIds);
        return SqlHelper.retBool(userMapper.deleteUserByIds(userIds));
    }

    /**
     * 导入用户数据
     *
     * @param userList        用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName        操作用户
     * @return 结果
     */
    @Override
    public String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName) {
        if (ObjectUtil.isNull(userList) || userList.isEmpty()) {
            throw new ServiceException("导入用户数据不能为空！");
        }
        Integer successNum = 0;
        Integer failureNum = 0;
        final var successMsg = new StringBuilder();
        final var failureMsg = new StringBuilder();
        final var password = configService.selectConfigByKey("sys.user.initPassword");
        for (final var user : userList) {
            try {
                // 验证是否存在这个用户
                final var u = userMapper.selectUserByUserName(user.getUserName());
                if (ObjectUtil.isNull(u)) {
                    beanValidatorComponent.validateWithException(validator, user);
                    user.setPassword(securityService.encryptPassword(password));
                    user.setCreateBy(operName);
                    userMapper.insertUser(user);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、账号 ").append(user.getUserName()).append(" 导入成功");
                } else if (isUpdateSupport) {
                    beanValidatorComponent.validateWithException(validator, user);
                    checkUserAllowed(u);
                    checkUserDataScope(u.getUserId());
                    user.setUserId(u.getUserId());
                    user.setUpdateBy(operName);
                    userMapper.updateUser(user);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、账号 ").append(user.getUserName()).append(" 更新成功");
                } else {
                    failureNum++;
                    failureMsg.append("<br/>").append(failureNum).append("、账号 ").append(user.getUserName()).append(" 已存在");
                }
            } catch (Exception e) {
                failureNum++;
                final var msg = "<br/>" + failureNum + "、账号 " + user.getUserName() + " 导入失败：";
                failureMsg.append(msg).append(e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

}
