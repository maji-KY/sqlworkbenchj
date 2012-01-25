/*
 * Db2SearchPath.java
 *
 * This file is part of SQL Workbench/J, http://www.sql-workbench.net
 *
 * Copyright 2002-2011, Thomas Kellerer
 * No part of this code may be reused without the permission of the author
 *
 * To contact the author please send an email to: support@sql-workbench.net
 *
 */
package workbench.db.ibm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import workbench.db.DbSearchPath;
import workbench.db.WbConnection;
import workbench.log.LogMgr;
import workbench.util.SqlUtil;
import workbench.util.StringUtil;

/**
 *
 * @author Thomas Kellerer
 */
public class Db2SearchPath
	implements DbSearchPath
{
	/**
	 * Returns the current search path defined in the session (or the user).
	 * <br/>
	 * @param con the connection for which the search path should be retrieved
	 * @return the list of schemas (libraries) in the search path.
	 */
	@Override
	public List<String> getSearchPath(WbConnection con, String defaultSchema)
	{
		if (con == null) return Collections.emptyList();
		List<String> result = new ArrayList<String>();

		ResultSet rs = null;
		Statement stmt = null;
		String sql = "values(current_path)";
		try
		{
			stmt = con.createStatementForQuery();
			LogMgr.logDebug("Db2SearchPath.getSearchPath()", "Running: " + sql);
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				String row = rs.getString(1);
				result.add(row);
			}
		}
		catch (SQLException ex)
		{
			LogMgr.logError("Db2SearchPath.getSearchPath()", "Could not read search path", ex);
		}
		finally
		{
			SqlUtil.closeAll(rs, stmt);
		}

		LogMgr.logDebug("Db2SearchPath.getSearchPath()", "Received: " + result.toString());

		List<String> searchPath = new ArrayList<String>();
		for (String line : result)
		{
			searchPath.addAll(StringUtil.stringToList(line, ",", true, true, false, false));
		}
		if (defaultSchema != null && searchPath.isEmpty())
		{
			searchPath.add(defaultSchema);
		}
		LogMgr.logDebug("Db2SearchPath.getSearchPath()", "Using path: " + result.toString());
		return searchPath;
	}
}
