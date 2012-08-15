package org.wangfy.dev.opensource.spring;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringframeworkTest {

    @Test
    public void testIOC() {
        String  contextXml = "applicationContext.xml";
        String configLocation = this.getClass().getResource(contextXml).toString();
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext();
//        context.setConfigLocation(configLocation);
        context.setConfigLocation("org/wangfy/dev/opensource/spring/"+contextXml);
        context.refresh();
        BeanA beanA = (BeanA) context.getBean("beanA");
        beanA.func();
    }

}

class BeanA {
    public void func() {
        System.out.println(BeanA.class.getName());
    }
}
