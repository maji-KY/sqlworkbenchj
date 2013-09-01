/*
 * DerbyConstraintReader.java
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
package workbench.db.derby;

import workbench.db.AbstractConstraintReader;

/**
 * Constraint reader for the Derby database
 * @author  Thomas Kellerer
 */
public class DerbyConstraintReader
	extends AbstractConstraintReader
{
	private final String TABLE_SQL =
		"select cons.constraintname, c.checkdefinition \n" +
		"from sys.syschecks c, sys.systables t, sys.sysconstraints cons, sys.sysschemas s \n" +
		"where t.tableid = cons.tableid \n" +
		"  and t.schemaid = s.schemaid \n" +
		"  and cons.constraintid = c.constraintid \n" +
		"  and t.tablename = ? \n" +
		"  and s.schemaname = ?";

	public DerbyConstraintReader()
	{
		super("apache_derby");
	}


	@Override
	public String getColumnConstraintSql()
	{
		return null;
	}

	@Override
	public String getTableConstraintSql()
	{
		return TABLE_SQL;
	}

	@Override
	public int getIndexForTableNameParameter()
	{
		return 1;
	}

	@Override
	public int getIndexForSchemaParameter()
	{
		return 2;
	}
}
