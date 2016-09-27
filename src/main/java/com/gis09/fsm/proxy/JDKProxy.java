package com.gis09.fsm.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by huyoufu on 2016/9/27.
 */
public class JDKProxy {
    public static void main(String[] args){
        Class [] clazz=new Class[]{ServiceInterface.class} ;
       /* newProxyInstanceProxy.newProxyInstance(JDKProxy.class.getClassLoader(),clazz, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return null;
            }
        });*/
        ServiceInterface o = (ServiceInterface) Proxy.newProxyInstance(JDKProxy.class.getClassLoader(), clazz, (proxy, method, _args) -> {
            return _args[0];
        });
        String a = o.test("小明");
        System.out.println(a);
    }

}
