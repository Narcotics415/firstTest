package com.fan.realm;

import com.fan.pojo.Waiter;
import com.fan.service.WaiterService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class WaiterRealm extends AuthorizingRealm {
    @Autowired
    WaiterService waiterService;

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
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;

        Waiter waiter = waiterService.queryWaiterByName(userToken.getUsername());

        if(waiter == null){
            return null;//跑出异常
        }

        //密码认证shiro自己做
        return new SimpleAuthenticationInfo("",waiter.getPassword(),"");
    }
}
