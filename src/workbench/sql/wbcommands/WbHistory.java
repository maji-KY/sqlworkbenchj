/*
 * This file is part of SQL Workbench/J, http://www.sql-workbench.net
 *
 * Copyright 2002-2013 Thomas Kellerer.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * To contact the author please send an email to: support@sql-workbench.net
 */

package workbench.sql.wbcommands;

import java.sql.SQLException;
import java.sql.Types;
import java.util.Collections;
import java.util.List;

import workbench.interfaces.SqlHistoryProvider;

import workbench.storage.DataStore;

import workbench.sql.ScrollAnnotation;
import workbench.sql.SqlCommand;
import workbench.sql.StatementRunnerResult;

import workbench.util.SqlUtil;
import workbench.util.StringUtil;

/**
 *
 * @author Thomas Kellerer
 */
public class WbHistory
	extends SqlCommand
{
	public static final String VERB = "WBHISTORY";
	public static final String SHORT_VERB = "WBHIST";

	private int maxLength = -1;

	public WbHistory()
	{
	}

	@Override
	public String getVerb()
	{
		return VERB;
	}

	@Override
	protected boolean isConnectionRequired()
	{
		return false;
	}

	public void setMaxDisplayLength(int length)
	{
		maxLength = length;
	}

	@Override
	public StatementRunnerResult execute(String sql)
		throws SQLException
	{
		StatementRunnerResult result = new StatementRunnerResult();

		SqlHistoryProvider provider = this.runner.getHistoryProvider();
		List<String> history = Collections.emptyList();
		if (provider != null)
		{
			history = provider.getHistoryEntries();
		}

		String parameter = this.getCommandLine(sql);
		if (StringUtil.isNonBlank(parameter)) return result;

		DataStore ds = new DataStore(new String[] {"NR", "SQL"}, new int[] { Types.INTEGER, Types.VARCHAR} );
		int index = 1;
		for (String entry : history)
		{
			int row = ds.addRow();
			ds.setValue(row, 0, Integer.valueOf(index));
			ds.setValue(row, 1, getDisplayString(entry));
			index ++;
		}
		ds.resetStatus();
		ds.setGeneratingSql(ScrollAnnotation.getScrollToEndAnnotation() + "\nWbHistory");
		result.addDataStore(ds);
		return result;
	}

	private String getDisplayString(String sql)
	{
		String display = SqlUtil.makeCleanSql(sql, false, false, '\'');
		if (maxLength > -1)
		{
			display = StringUtil.getMaxSubstring(display, maxLength - 10);
		}
		return display;
	}
}