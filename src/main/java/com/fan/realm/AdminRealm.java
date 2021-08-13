package com.fan.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class AdminRealm extends AuthorizingRealm {
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了=》授权doGetAuthorizationInfo");
        return null;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了=》认证doGetAuthenticationInfo");
        //用户名密码  数据库中获取
        String username = "root";
        String password ="123456";
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;

        if(!userToken.getUsername().equals(username)){
            return null;//跑出异常
        }

        //密码认证shiro自己做
        return new SimpleAuthenticationInfo("",password,"");
    }
}
