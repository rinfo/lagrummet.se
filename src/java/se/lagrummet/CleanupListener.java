package se.lagrummet;

import java.beans.Introspector;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.LogFactory;

public class CleanupListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		LogFactory.release(Thread.currentThread().getContextClassLoader());
		org.apache.log4j.LogManager.shutdown();
		PropertyUtils.clearDescriptors();
		Introspector.flushCaches();
		for (Enumeration e = DriverManager.getDrivers(); e.hasMoreElements();) {
		Driver driver = (Driver) e.nextElement();
		if (driver.getClass().getClassLoader() == getClass().getClassLoader()) { try {
			DriverManager.deregisterDriver(driver);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} }
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

}
