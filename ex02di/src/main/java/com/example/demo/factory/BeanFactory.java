package com.factory;
/*
 * 디자인 패턴 - 펙토리
 */
public class BeanFactory {
	public Object getBean(String beanName){
        if(beanName.equals("samsung")){ 
            return new SamsungTV();
        } else if(beanName.equals("lg")){
            return new LgTV();
        }
        return null;
    }
}
