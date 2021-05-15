/**
* Copyright (C) 2018-2020
* All rights reserved, Designed By www.yixiang.co
* 注意：
* 本软件为www.yixiang.co开发研制
*/
package co.yixiang.modules.security.service;

import co.yixiang.exception.BadRequestException;
import co.yixiang.modules.security.security.vo.JwtUser;
import co.yixiang.modules.system.service.RoleService;
import co.yixiang.modules.system.service.UserService;
import co.yixiang.modules.system.service.dto.DeptSmallDto;
import co.yixiang.modules.system.service.dto.JobSmallDto;
import co.yixiang.modules.system.service.dto.UserDto;
import co.yixiang.utils.EmailUtils;
import co.yixiang.utils.PhoneUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author hupeng
 * @date 2018-11-22
 */
@Slf4j
@Service("userDetailsService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    private final RoleService roleService;

    public UserDetailsServiceImpl(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public UserDetails loadUserByUsername(String user) {
        UserDto userDto;
//        log.info("接受数据：{}",user);
        if (EmailUtils.isEmail(user)) {
//        if (user.matches("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+")) {
//            log.info("邮箱登录");
            //邮箱登录
            userDto = userService.findByEmail(user);
        } else if (PhoneUtils.isPhoneLegal(user)) {
            //电话登录
//            log.info("电话登录");
            userDto = userService.findByPhone(user);
        } else {
            //正常登录
//            log.info("账号登录");
            userDto = userService.findByName(user);
        }
//        log.info("查询数据：{}",userDto);

        if (userDto == null) {
            throw new BadRequestException("账号不存在");
        } else {
            if (!userDto.getEnabled()) {
                throw new BadRequestException("账号未激活");
            }
            return createJwtUser(userDto);
        }
    }

    private UserDetails createJwtUser(UserDto user) {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getNickName(),
                user.getSex(),
                user.getPassword(),
                user.getAvatar(),
                user.getEmail(),
                user.getPhone(),
                Optional.ofNullable(user.getDept()).map(DeptSmallDto::getName).orElse(null),
                Optional.ofNullable(user.getJob()).map(JobSmallDto::getName).orElse(null),
                roleService.mapToGrantedAuthorities(user),
                user.getEnabled(),
                user.getCreateTime(),
                user.getLastPasswordResetTime()
        );
    }
}
