package pe.a3ya.mscustomers.infrastructure.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContext implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        context = applicationContext;
    }

    public static <T extends Object> T getBean(Class<T> beanClass) {
        return context.getBean(beanClass);
    }
}
