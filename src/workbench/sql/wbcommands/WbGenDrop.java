/*
 * WbGenDrop.java
 *
 * This file is part of SQL Workbench/J, http://www.sql-workbench.net
 *
 * Copyright 2002-2012, Thomas Kellerer
 * No part of this code may be reused without the permission of the author
 *
 * To contact the author please send an email to: support@sql-workbench.net
 */
package workbench.sql.wbcommands;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import workbench.resource.ResourceMgr;

import workbench.db.DropScriptGenerator;
import workbench.db.TableIdentifier;
import workbench.db.WbConnection;

import workbench.sql.SqlCommand;
import workbench.sql.StatementRunnerResult;

import workbench.util.ArgumentParser;
import workbench.util.ArgumentType;
import workbench.util.ExceptionUtil;
import workbench.util.FileUtil;
import workbench.util.WbFile;

import static workbench.sql.wbcommands.WbGenDrop.PARAM_INCLUDE_CREATE;

/**
 * A SqlCommand to create a DROP script for one or more tables that will drop referencing foreign keys
 * before dropping the table(s).
 *
 * This can be used as an alternative to DROP ... CASCADE
 *
 * @author Thomas Kellerer
 */
public class WbGenDrop
	extends SqlCommand
{
	public static final String VERB = "WBGENERATEDROP";

	public static final String PARAM_TABLES = "tables";
	public static final String PARAM_FILE = "outputFile";
	public static final String PARAM_DIR = "outputDir";
	public static final String PARAM_INCLUDE_CREATE = "includeCreate";
	public static final String PARAM_DROP_FK_ONLY = "onlyForeignkeys";
	public static final String PARAM_SORT_BY_TYPE = "sortByType";
	public static final String PARAM_INCLUDE_COMMENTS = "includeMarkers";

	public WbGenDrop()
	{
		super();
		this.isUpdatingCommand = true;
		cmdLine = new ArgumentParser();
		cmdLine.addArgument(PARAM_DIR, ArgumentType.StringArgument);
		cmdLine.addArgument(PARAM_FILE, ArgumentType.StringArgument);
		cmdLine.addArgument(PARAM_TABLES, ArgumentType.TableArgument);
		cmdLine.addArgument(PARAM_INCLUDE_CREATE, ArgumentType.BoolArgument);
		cmdLine.addArgument(PARAM_DROP_FK_ONLY, ArgumentType.BoolArgument);
		cmdLine.addArgument(PARAM_SORT_BY_TYPE, ArgumentType.BoolArgument);
		cmdLine.addArgument(PARAM_INCLUDE_COMMENTS, ArgumentType.BoolArgument);
	}

	@Override
	public StatementRunnerResult execute(String sql)
		throws SQLException, Exception
	{
		StatementRunnerResult result = new StatementRunnerResult();
		String args = getCommandLine(sql);
		cmdLine.parse(args);

		if (cmdLine.hasUnknownArguments())
		{
			setUnknownMessage(result, cmdLine, ResourceMgr.getString("ErrGenDropWrongParam"));
			result.setFailure();
			return result;
		}
		if (!cmdLine.hasArguments())
		{
			result.addMessage(ResourceMgr.getString("ErrGenDropWrongParam"));
			result.setFailure();
			return result;
		}

		SourceTableArgument tableArg = new SourceTableArgument(cmdLine.getValue(PARAM_TABLES), currentConnection);
		List<TableIdentifier> tables = tableArg.getTables();

		if (tables.isEmpty())
		{
			result.addMessage(ResourceMgr.getFormattedString("ErrExportNoTablesFound", cmdLine.getValue(PARAM_TABLES)));
			result.setFailure();
			return result;
		}

		boolean includeCreate = cmdLine.getBoolean(PARAM_INCLUDE_CREATE, true);
		boolean onlyFk = cmdLine.getBoolean(PARAM_DROP_FK_ONLY, false);
		boolean sortByType = cmdLine.getBoolean(PARAM_SORT_BY_TYPE, false);
		boolean includeComments = cmdLine.getBoolean(PARAM_INCLUDE_COMMENTS, true);
		
		DropScriptGenerator gen = new DropScriptGenerator(currentConnection);
		gen.setIncludeRecreateStatements(includeCreate);
		gen.setIncludeComments(includeComments);

		if (onlyFk)
		{
			gen.setIncludeRecreateStatements(false);
			gen.setIncludeDropTable(false);
		}

		gen.setTables(tables);
		gen.setSortByType(sortByType);
		gen.setRowMonitor(this.rowMonitor);
		String dir = cmdLine.getValue(PARAM_DIR, null);
		String file = cmdLine.getValue(PARAM_FILE, null);

		gen.generateScript();

		List<TableIdentifier> processed = gen.getTables();

		if (dir != null)
		{
			WbFile dirFile = new WbFile(dir);
			if (!dirFile.isDirectory())
			{
				result.addMessage(ResourceMgr.getFormattedString("ErrExportOutputDirNotDir", dir));
				result.setFailure();
				return result;
			}

			int count = 0;
			for (TableIdentifier tbl : processed)
			{
				WbFile output = new WbFile(dirFile, "drop_" + tbl.getTableName().toLowerCase() + ".sql");
				try
				{
					writeFile(output, gen.getScriptFor(tbl));
					count ++;
				}
				catch (IOException io)
				{
					result.addMessageByKey("ErrExportFileCreate");
					result.addMessage(ExceptionUtil.getDisplay(io));
					result.setFailure();
					return result;
				}
			}
			result.setSuccess();
			result.addMessage(ResourceMgr.getFormattedString("MsgDropScriptsWritten", Integer.valueOf(count), dirFile.getFullPath()));
		}
		else if (file != null)
		{
			WbFile output = new WbFile(file);
			try
			{
				writeFile(output, gen.getScript());
				result.addMessage(ResourceMgr.getFormattedString("MsgScriptWritten", output.getFullPath()));
				result.setSuccess();
			}
			catch (IOException io)
			{
				result.addMessageByKey("ErrExportFileCreate");
				result.addMessage(ExceptionUtil.getDisplay(io));
				result.setFailure();
			}
		}
		else
		{
			result.setSuccess();
			result.addMessage(gen.getScript());
		}
		return result;
	}

	private void writeFile(File file, String script)
		throws IOException
	{
		PrintWriter writer = null;
		try
		{
			writer = new PrintWriter(new FileWriter(file));
			writer.print(script);
		}
		finally
		{
			FileUtil.closeQuietely(writer);
		}
	}

	@Override
	public void cancel()
		throws SQLException
	{
		super.cancel();
	}

	@Override
	public boolean isUpdatingCommand(WbConnection con, String sql)
	{
		return false;
	}

	@Override
	public String getVerb()
	{
		return VERB;
	}



}
