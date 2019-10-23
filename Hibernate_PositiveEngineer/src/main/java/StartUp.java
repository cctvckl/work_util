import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * desc:
 *
 * @author : caokunliang
 * creat_date: 2019/10/22 0022
 * creat_time: 9:12
 **/
public class StartUp {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("springcontext-config.xml");
        Object sessionFactory = context.getBean("sessionFactory");
        System.out.println(sessionFactory);
    }
}
