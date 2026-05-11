package com.sym.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sym.common.exception.BusinessException;
import com.sym.framework.security.utils.JwtUtil;
import com.sym.framework.security.utils.PasswordUtil;
import com.sym.system.domain.BaseUser;
import com.sym.system.domain.SysUser;
import com.sym.system.domain.dto.LoginRequest;
import com.sym.system.domain.vo.LoginVO;
import com.sym.system.mapper.BaseUserMapper;
import com.sym.system.service.IBaseUserService;
import com.sym.system.service.ICaptchaService;
import com.sym.system.service.ISysUserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class BaseUserServiceImpl extends ServiceImpl<BaseUserMapper, BaseUser> implements IBaseUserService {

    @Resource
    private ICaptchaService captchaService;

    @Resource
    private ISysUserService sysUserService;

    @Override
    public LoginVO login(LoginRequest request) {
        // 验证码校验
        captchaService.validateCaptcha(request.getCaptchaKey(), request.getCaptchaCode());
        // 1. 根据用户名查询用户
        BaseUser user = this.findByUsername(request.getUsername());

        // 2. 校验用户是否存在
        if (user == null) {
            throw new BusinessException("用户名或密码错误");
        }

        // 3. 校验账号状态
        if ("1".equals(user.getStatus())) {
            throw new BusinessException("账号已被停用，请联系管理员");
        }

        // 4. 校验删除标志
        if ("2".equals(user.getDelFlag())) {
            throw new BusinessException("账号不存在");
        }
        // 5. 密码校验
        if (!PasswordUtil.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }
        // 6. 查询后台用户扩展信息
        SysUser sysUser = sysUserService.lambdaQuery()
                .eq(SysUser::getUserId, user.getUserId())
                .one();
        // 7. token
        String token = JwtUtil.generateToken(user.getUserId(), user.getUsername());
        return LoginVO.builder()
                .token(token)
                .userId(user.getUserId())
                .username(user.getUsername())
                .nickname(sysUser.getRealName())
                .avatar(sysUser.getEmployeeNo())
                .build();
    }

    @Override
    public BaseUser findByUsername(String username) {
        LambdaQueryWrapper<BaseUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BaseUser::getUsername, username);
        // 使用继承的BaseMapper中的方法
        return this.getOne(wrapper);
    }
}
