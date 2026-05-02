package com.sym.system.service.impl;

import com.sym.framework.security.utils.JwtUtil;
import com.sym.framework.security.utils.WechatUtil;
import com.sym.system.domain.BaseUser;
import com.sym.system.domain.MiniappUser;
import com.sym.system.domain.vo.MiniappLoginVO;
import com.sym.system.service.IBaseUserService;
import com.sym.system.service.IMiniappLoginService;
import com.sym.system.service.IMiniappUserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import sym.com.common.utils.SymStringUtil;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class MiniappLoginServieImpl implements IMiniappLoginService {

    // 用于获取微信用户的openid和session_key等信息
    @Resource
    private WechatUtil wechatUtil;

    // 用于获取创建更新基础用户信息
    @Resource
    private IBaseUserService baseUserService;

    // 用于获取创建更新小程序用户信息
    @Resource
    private IMiniappUserService miniappUserService;

    /**
     * 小程序静默登录
     * @param code
     * @return 登录token
     */
    @Override
    public MiniappLoginVO login(String code) {
        Map<String, String> sessionData =  wechatUtil.code2Session(code);
        String openid = sessionData.get("openid");
        String unionid = sessionData.get("unionid");
        String sessionKey = sessionData.get("session_key");

        MiniappUser miniappUser = miniappUserService.getByOpenid(openid);
        BaseUser baseUser;
        if (SymStringUtil.isNull(miniappUser)) {
            baseUser = createNewUser(openid, unionid, sessionKey);
            miniappUser = createMiniappUser(baseUser.getUserId(), openid, unionid, sessionKey);
        } else {
            baseUser = baseUserService.getById(miniappUser.getUserId());
            updateMiniappUser(miniappUser, sessionKey);
        }
        String token = JwtUtil.generateToken(baseUser.getUserId(), baseUser.getUsername());
        return MiniappLoginVO.builder()
                .token(token)
                .build();
    }

    /**
     * 保存用户基本信息基本表base_user
     * @param openid
     * @param unionid
     * @param sessionKey
     * @return
     */
    private BaseUser createNewUser(String openid, String unionid, String sessionKey) {
        BaseUser baseUser = new BaseUser();
        baseUser.setUsername(generateUsername(openid));
        baseUser.setNickname("微信用户");
        baseUser.setAvatar("");
        baseUser.setGender(0);
        baseUser.setStatus("0");
        baseUser.setDelFlag("0");
        baseUser.setCreateTime(LocalDateTime.now());
        baseUser.setUpdateTime(LocalDateTime.now());

        baseUserService.save(baseUser);
        return baseUser;
    }

    /**
     * 保存小程序用户信息到miniappUser表
     * @param userId
     * @param openid
     * @param unionid
     * @param sessionKey
     * @return
     */
    private MiniappUser createMiniappUser(Long userId, String openid, String unionid, String sessionKey) {
        MiniappUser miniappUser = new MiniappUser();
        miniappUser.setUserId(userId);
        miniappUser.setOpenid(openid);
        miniappUser.setUnionid(unionid != null ? unionid : "");
        miniappUser.setSessionKey(sessionKey);
        miniappUser.setLastLoginTime(LocalDateTime.now());

        miniappUserService.save(miniappUser);
        return miniappUser;
    }

    /**
     * 更新小程序用户登陆信息
     * @param miniappUser
     * @param sessionKey
     */
    private void updateMiniappUser(MiniappUser miniappUser, String sessionKey) {
        miniappUser.setSessionKey(sessionKey);
        miniappUser.setLastLoginTime(LocalDateTime.now());
        miniappUserService.updateById(miniappUser);
    }

    /**
     * 生成小程序用户名
     * @param openid
     * @return
     */
    private String generateUsername(String openid) {
        return "mp_" + openid.substring(Math.max(0, openid.length() - 8));
    }
}
