/*
 * SqlServerSynonymReaderTest
 *
 *  This file is part of SQL Workbench/J, http://www.sql-workbench.net
 *
 *  Copyright 2002-2012, Thomas Kellerer
 *  No part of this code may be reused without the permission of the author
 *
 *  To contact the author please send an email to: support@sql-workbench.net
 */
package workbench.db.mssql;

import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import workbench.TestUtil;
import workbench.WbTestCase;
import workbench.db.TableIdentifier;
import workbench.db.WbConnection;
import static org.junit.Assert.*;

/**
 *
 * @author Thomas Kellerer
 */
public class SqlServerTypeReaderTest
	extends WbTestCase
{

	public SqlServerTypeReaderTest()
	{
		super("SqlServerSynonymReaderTest");
	}

	@BeforeClass
	public static void setUpClass()
		throws Exception
	{
		SQLServerTestUtil.initTestcase("SqlServerProcedureReaderTest");
		WbConnection conn = SQLServerTestUtil.getSQLServerConnection();
		if (conn == null) return;
		SQLServerTestUtil.dropAllObjects(conn);

		String sql =
			"CREATE TYPE address_type \n" +
			"AS \n" +
			"TABLE \n" +
			"( \n" +
			"   streetname  varchar(50), \n" +
			"   city        varchar(50)     DEFAULT ('Munich'), \n" +
			"   some_value  numeric(12,4) \n" +
			")\n" +
			"commit;\n";
		TestUtil.executeScript(conn, sql);
	}

	@AfterClass
	public static void tearDownClass()
		throws Exception
	{
		WbConnection conn = SQLServerTestUtil.getSQLServerConnection();
		if (conn == null) return;
		SQLServerTestUtil.dropAllObjects(conn);
	}


	@Test
	public void testReader()
		throws Exception
	{
		WbConnection conn = SQLServerTestUtil.getSQLServerConnection();
		if (conn == null) return;
		List<TableIdentifier> types = conn.getMetadata().getObjectList(null, new String[] { "TYPE" });
		assertNotNull(types);
		assertEquals(1, types.size());
		assertEquals("address_type", types.get(0).getObjectName());

		String source = types.get(0).getSource(conn).toString().trim();
		String expected =
			"CREATE TYPE dbo.address_type\n" +
			"AS\n" +
			"TABLE\n" +
			"(\n" +
			"   streetname  varchar(50),\n" +
			"   city        varchar(50)     DEFAULT ('Munich'),\n" +
			"   some_value  numeric(12,4)\n" +
			");";
//		System.out.println("----------------\n" + source + "\n++++++++++++\n" + expected);
		assertEquals(expected, source);
	}
}
