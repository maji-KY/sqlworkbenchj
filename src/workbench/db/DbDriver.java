/*
 * DbDriver.java
 *
 * Created on January 25, 2002, 11:41 PM
 */

package workbench.db;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;
import workbench.exception.WbException;

/**
 *	Represents a JDBC Driver definition.
 *	The definition includes a (logical) name, a driver class
 *	and (optional) a library from which the driver is to 
 *	be loaded.
 *	@author  thomas
 */
public class DbDriver
{
	private Driver driverClassInstance;
	private URLClassLoader classLoader;
	
	/** Holds value of property name. */
	private String name;
	
	/** Holds value of property driverClass. */
	private String driverClass;
	
	/** Holds value of property library. */
	private String library;
	
	public DbDriver()
	{
	}
	
	public DbDriver(Driver aDriverClass)
	{
		this.driverClassInstance = aDriverClass;
		this.driverClass = aDriverClass.getClass().getName();
		this.name = this.driverClass;
	}
	
	/** Creates a new instance of DbDriver */
	public DbDriver(String aName, String aClass, String aLibrary)
	{
		this.setName(aName);
		this.setDriverClass(aClass);
		this.setLibrary(aLibrary);
	}
	
	public String getName() { return this.name; }
	public void setName(String name) { 	this.name = name; }
	
	public String getDriverClass() {  return this.driverClass; }
	public void setDriverClass(String driverClass) { this.driverClass = driverClass;	}
	
	public String getLibrary() { return this.library; }
	public void setLibrary(String library) { this.library = library; }
	
	public String toString() { return this.getDriverClass(); }

	private void loadDriverClass()
		throws WbException
	{
		if (this.driverClassInstance != null) return;
		try
		{
			if (this.classLoader == null)
			{
				URL[] url = new URL[1];
				url[0] = new File(this.library).toURL();
				this.classLoader = new URLClassLoader(url);
			}
			
			Class drvClass = this.classLoader.loadClass(this.driverClass);
			this.driverClassInstance = (Driver)drvClass.newInstance();
		}
		catch (Exception e)
		{
			throw new WbException("Could not load driver class " + this.driverClass);
		}
	}
	
	public Connection getConnection(String url, String user, String password)
		throws WbException, SQLException
	{
		Connection c = null;
		try
		{
			this.loadDriverClass();
			Properties props = new Properties();
			props.put("user", user);
			props.put("password", password);
			c = this.driverClassInstance.connect(url, props);
		}
		catch (WbException e)
		{
			throw e;
		}
		catch (SQLException e)
		{
			throw e;
		}
		return c;
	}
	
}
