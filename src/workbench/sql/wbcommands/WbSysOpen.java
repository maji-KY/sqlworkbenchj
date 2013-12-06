/*
 * WbSysOpen.java
 *
 * This file is part of SQL Workbench/J, http://www.sql-workbench.net
 *
 * Copyright 2002-2013, Thomas Kellerer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at.
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * To contact the author please send an email to: support@sql-workbench.net
 *
 */
package workbench.sql.wbcommands;

import java.awt.Desktop;
import java.io.File;
import java.sql.SQLException;

import workbench.log.LogMgr;
import workbench.sql.SqlCommand;
import workbench.sql.StatementRunnerResult;
import workbench.util.*;

/**
 * A workbench command to call an operating system program (or command)
 *
 * @author Thomas Kellerer
 */
public class WbSysOpen
	extends SqlCommand
{
	public static final String VERB = "WBSYSOPEN";

	public WbSysOpen()
	{
		super();
		cmdLine = new ArgumentParser();
	}

	@Override
	public StatementRunnerResult execute(String sql)
		throws SQLException
	{
		StatementRunnerResult result = new StatementRunnerResult(sql);
		String doc = getCommandLine(sql);
		if (StringUtil.isBlank(doc))
		{
			result.setFailure();
			result.addMessageByKey("ErrSysOpenNoParm");
			return result;
		}

		try
		{
			Desktop.getDesktop().open(new File(doc));
			result.setSuccess();
		}
		catch (Exception ex)
		{
			LogMgr.logError("WbSysOpen.execute()", "Could not open file " + doc, ex);
			result.setFailure();
			result.addMessage(ex.getLocalizedMessage());
		}
		return result;
	}

	@Override
	protected boolean isConnectionRequired()
	{
		return false;
	}

	@Override
	public String getVerb()
	{
		return VERB;
	}
}
