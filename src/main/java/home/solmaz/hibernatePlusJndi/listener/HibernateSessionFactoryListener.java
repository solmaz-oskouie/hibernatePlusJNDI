package home.solmaz.hibernatePlusJndi.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.jboss.logging.Logger;

@WebListener
public class HibernateSessionFactoryListener implements ServletContextListener {

    public final Logger logger = Logger.getLogger(HibernateSessionFactoryListener.class);
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        SessionFactory sessionFactoryObj = (SessionFactory) servletContextEvent.getServletContext().getAttribute("SessionFactory");
        if(sessionFactoryObj != null && !sessionFactoryObj.isClosed()) {
            logger.info("Closing Session Factory Object!");
            sessionFactoryObj.close();
        }
        logger.info("Hibernate Session Factory Object Released!");
    }

    public void contextInitialized(ServletContextEvent servletContextEvent) {
//        // Creating Configuration Instance & Passing Hibernate Configuration File
//        Configuration configObj = new Configuration();
//        configObj.configure("hibernate.cfg.xml");
//
//        // Since Hibernate Version 4.x, Service Registry Is Being Used
//        ServiceRegistry serviceRegistryObj = new StandardServiceRegistryBuilder().applySettings(configObj.getProperties()).build();
//
//        // Creating Hibernate Session Factory Instance
//        SessionFactory factoryObj = configObj.buildSessionFactory(serviceRegistryObj);
//
//        // Saving Hibernate Session Factory Object In The Request Scope
//


        // Create registry
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        // Create MetadataSources
        MetadataSources sources = new MetadataSources(registry);
        // Create Metadata
        Metadata metadata = sources.getMetadataBuilder().build();
        // Create SessionFactory
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
        servletContextEvent.getServletContext().setAttribute("SessionFactory", sessionFactory);
        logger.info("Hibernate Session Factory Configured Successfully!");
    }
}
