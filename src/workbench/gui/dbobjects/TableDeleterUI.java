/*
 * TableDeleterUI.java
 *
 * This file is part of SQL Workbench/J, http://www.sql-workbench.net
 *
 * Copyright 2002-2008, Thomas Kellerer
 * No part of this code maybe reused without the permission of the author
 *
 * To contact the author please send an email to: support@sql-workbench.net
 *
 */
package workbench.gui.dbobjects;

import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import workbench.db.TableIdentifier;
import workbench.db.WbConnection;
import workbench.db.importer.TableDependencySorter;
import workbench.gui.WbSwingUtilities;
import workbench.gui.components.EditWindow;
import workbench.gui.components.NoSelectionModel;
import workbench.gui.components.WbButton;
import workbench.interfaces.TableDeleteListener;
import workbench.log.LogMgr;
import workbench.resource.ResourceMgr;
import workbench.util.ExceptionUtil;
import workbench.util.SqlUtil;
import workbench.util.WbThread;

/**
 *
 * @author  support@sql-workbench.net
 */
public class TableDeleterUI
	extends javax.swing.JPanel
	implements WindowListener
{
	private JDialog dialog;
	private List<TableIdentifier> objectNames;
	private boolean cancelled;
	private WbConnection connection;
	private Thread deleteThread;
	private Thread checkThread;
	private List<TableDeleteListener> deleteListener;
	private Statement currentStatement;
	
	public TableDeleterUI()
	{
		initComponents();
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {
    java.awt.GridBagConstraints gridBagConstraints;

    buttonGroup1 = new javax.swing.ButtonGroup();
    buttonPanel = new javax.swing.JPanel();
    deleteButton = new WbButton();
    cancelButton = new WbButton();
    mainPanel = new javax.swing.JPanel();
    jScrollPane1 = new javax.swing.JScrollPane();
    objectList = new javax.swing.JList();
    optionPanel = new javax.swing.JPanel();
    statusLabel = new javax.swing.JLabel();
    jPanel1 = new javax.swing.JPanel();
    checkFKButton = new javax.swing.JButton();
    jPanel2 = new javax.swing.JPanel();
    commitEach = new javax.swing.JRadioButton();
    commitAtEnd = new javax.swing.JRadioButton();
    useTruncateCheckBox = new javax.swing.JCheckBox();
    jPanel3 = new javax.swing.JPanel();
    showScript = new javax.swing.JButton();
    addMissingTables = new javax.swing.JCheckBox();

    setLayout(new java.awt.BorderLayout());

    buttonPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

    deleteButton.setText(ResourceMgr.getString("LblDeleteTableData"));
    deleteButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        deleteButtonActionPerformed(evt);
      }
    });
    buttonPanel.add(deleteButton);

    cancelButton.setText(ResourceMgr.getString("LblCancel"));
    cancelButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cancelButtonActionPerformed(evt);
      }
    });
    buttonPanel.add(cancelButton);

    add(buttonPanel, java.awt.BorderLayout.SOUTH);

    mainPanel.setLayout(new java.awt.BorderLayout(0, 5));

    objectList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    objectList.setSelectionModel(new NoSelectionModel());
    jScrollPane1.setViewportView(objectList);

    mainPanel.add(jScrollPane1, java.awt.BorderLayout.CENTER);

    optionPanel.setLayout(new java.awt.BorderLayout(0, 5));

    statusLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
    statusLabel.setMaximumSize(new java.awt.Dimension(32768, 24));
    statusLabel.setMinimumSize(new java.awt.Dimension(150, 24));
    statusLabel.setPreferredSize(new java.awt.Dimension(150, 24));
    optionPanel.add(statusLabel, java.awt.BorderLayout.SOUTH);

    jPanel1.setLayout(new java.awt.GridBagLayout());

    checkFKButton.setText(ResourceMgr.getString("LblCheckFKDeps"));
    checkFKButton.setToolTipText(ResourceMgr.getDescription("LblCheckFKDeps"));
    checkFKButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        checkFKButtonActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.weighty = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(3, 3, 0, 5);
    jPanel1.add(checkFKButton, gridBagConstraints);

    jPanel2.setLayout(new java.awt.GridBagLayout());

    buttonGroup1.add(commitEach);
    commitEach.setSelected(true);
    commitEach.setText(ResourceMgr.getString("LblCommitEachTableDelete")
    );
    commitEach.setBorder(null);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(0, 6, 0, 0);
    jPanel2.add(commitEach, gridBagConstraints);

    buttonGroup1.add(commitAtEnd);
    commitAtEnd.setText(ResourceMgr.getString("LblCommitTableDeleteAtEnd"));
    commitAtEnd.setBorder(null);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(4, 6, 0, 0);
    jPanel2.add(commitAtEnd, gridBagConstraints);

    useTruncateCheckBox.setText(ResourceMgr.getString("LblUseTruncate"));
    useTruncateCheckBox.setBorder(null);
    useTruncateCheckBox.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        useTruncateCheckBoxItemStateChanged(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
    jPanel2.add(useTruncateCheckBox, gridBagConstraints);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 3;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    jPanel2.add(jPanel3, gridBagConstraints);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.gridheight = 3;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(3, 4, 0, 9);
    jPanel1.add(jPanel2, gridBagConstraints);

    showScript.setText(ResourceMgr.getString("LblShowScript"));
    showScript.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        showScriptActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.weighty = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(9, 3, 0, 5);
    jPanel1.add(showScript, gridBagConstraints);

    addMissingTables.setSelected(true);
    addMissingTables.setText(ResourceMgr.getString("LblIncFkTables"));
    addMissingTables.setToolTipText(ResourceMgr.getDescription("LblIncFkTables"));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.weighty = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 0);
    jPanel1.add(addMissingTables, gridBagConstraints);

    optionPanel.add(jPanel1, java.awt.BorderLayout.CENTER);

    mainPanel.add(optionPanel, java.awt.BorderLayout.SOUTH);

    add(mainPanel, java.awt.BorderLayout.CENTER);
  }// </editor-fold>//GEN-END:initComponents

	private void useTruncateCheckBoxItemStateChanged(java.awt.event.ItemEvent evt)//GEN-FIRST:event_useTruncateCheckBoxItemStateChanged
	{//GEN-HEADEREND:event_useTruncateCheckBoxItemStateChanged
		if (this.useTruncateCheckBox.isSelected())
		{
			this.disableCommitSettings();
		}
		else if (!this.connection.getAutoCommit())
		{
			this.enableCommitSettings();
		}
	}//GEN-LAST:event_useTruncateCheckBoxItemStateChanged

	private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_cancelButtonActionPerformed
	{//GEN-HEADEREND:event_cancelButtonActionPerformed
		this.cancelled = true;
		closeWindow();
	}//GEN-LAST:event_cancelButtonActionPerformed

	private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_deleteButtonActionPerformed
	{//GEN-HEADEREND:event_deleteButtonActionPerformed
		this.startDelete();
	}//GEN-LAST:event_deleteButtonActionPerformed

	private void checkFKButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkFKButtonActionPerformed

		if (this.connection.isBusy()) return;

		this.deleteButton.setEnabled(false);
		this.showScript.setEnabled(false);
		this.statusLabel.setText(ResourceMgr.getString("MsgFkDeps"));
		
		WbSwingUtilities.showWaitCursor(dialog);
		
		this.checkThread = new WbThread("FKCheck")
		{
			public void run()
			{
				List<TableIdentifier> sorted = null;
				try
				{
					connection.setBusy(true);
					TableDependencySorter sorter = new TableDependencySorter(connection);
					sorted = sorter.sortForDelete(objectNames, addMissingTables.isSelected());
				}
				catch (Exception e)
				{
					LogMgr.logError("TableDeleterUI.checkFK()", "Error checking FK dependencies", e);
					WbSwingUtilities.showErrorMessage(ExceptionUtil.getDisplay(e));
					sorted = null;
				}
				finally
				{
					connection.setBusy(false);
					fkCheckFinished(sorted);
				}
			}
		};
		
		checkThread.start();
	}//GEN-LAST:event_checkFKButtonActionPerformed
	
	private void showScriptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showScriptActionPerformed
		showScript();
	}//GEN-LAST:event_showScriptActionPerformed


	protected void fkCheckFinished(final List<TableIdentifier> newlist)
	{
		this.checkThread = null;
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				statusLabel.setText("");
				if (newlist != null)
				{
					setObjects(newlist);
				}
				deleteButton.setEnabled(true);
				showScript.setEnabled(true);
				WbSwingUtilities.showDefaultCursor(dialog);
			}
		});
	}
	
	protected void closeWindow()
	{
		try
		{
			if (this.deleteThread != null)
			{
				this.deleteThread.interrupt();
				this.deleteThread = null;
			}
		}
		catch (Exception e)
		{
			LogMgr.logWarning("TableDeleterUI.cancel()", "Error when trying to kill delete Thread", e);
		}

		try
		{
			if (this.checkThread != null)
			{
				this.checkThread.interrupt();
				this.checkThread = null;
			}
		}
		catch (Exception e)
		{
			LogMgr.logWarning("TableDeleterUI.cancel()", "Error when trying to kill check thread", e);
		}
		
		this.dialog.setVisible(false);
//		this.dialog.dispose();
		this.dialog = null;
	}

	public void setConnection(WbConnection aConn)
	{
		this.connection = aConn;
		if (this.connection != null)
		{

			this.useTruncateCheckBox.setEnabled(this.connection.getDbSettings().supportsTruncate());
			boolean autoCommit = this.connection.getAutoCommit();
			if (autoCommit)
			{
				this.disableCommitSettings();
			}
			else
			{
				this.enableCommitSettings();
			}
		}
	}

	protected void disableCommitSettings()
	{
		this.commitAtEnd.setEnabled(false);
		this.commitEach.setEnabled(false);
	}

	protected void enableCommitSettings()
	{
		this.commitAtEnd.setEnabled(true);
		this.commitEach.setEnabled(true);
	}

	protected void startDelete()
	{
		this.deleteThread = new WbThread("TableDeleteThread")
		{
			public void run()
			{
				doDelete();
			}
		};
		this.deleteThread.start();
	}

	protected void doDelete()
	{
		this.cancelled = false;
		boolean ignoreAll = false;

		boolean doCommitEach = this.commitEach.isSelected();
		boolean useTruncate = this.useTruncateCheckBox.isSelected();
		if (useTruncate)
		{
			doCommitEach = false;
		}
		boolean hasError = false;
		List<TableIdentifier> tables = new ArrayList<TableIdentifier>();
		int count = this.objectNames.size();
		TableIdentifier table = null;

		try
		{
			this.currentStatement = this.connection.createStatement();
		}
		catch (SQLException e)
		{
			WbSwingUtilities.showErrorMessage(e.getMessage());
			LogMgr.logError("TableDeleterUI.doDelete()", "Error creating statement", e);
			return;
		}
		
		try
		{
			this.connection.setBusy(true);

			for (int i = 0; i < count; i++)
			{
				if (this.cancelled)
				{
					break;
				}
				table = this.objectNames.get(i);
				this.statusLabel.setText(ResourceMgr.getString("TxtDeletingTable") + " " + table + " ...");
				try
				{
					this.deleteTable(table, useTruncate, doCommitEach);
					tables.add(table);
				}
				catch (Exception ex)
				{
					String error = ExceptionUtil.getDisplay(ex);
					LogMgr.logError("TableDeleterUI.doDelete()", "Error deleting table " + table, ex);

					if (!ignoreAll)
					{
						String question = ResourceMgr.getString("ErrDeleteTableData");
						question = question.replaceAll("%table%", table.toString());
						question = question.replaceAll("%error%", error);
						question = question + "\n" + ResourceMgr.getString("MsgContinueQ");

						int choice = WbSwingUtilities.getYesNoIgnoreAll(this.dialog, question);
						if (choice == JOptionPane.NO_OPTION)
						{
							// the hasError flag will cause a rollback at the end.
							hasError = true;
							break;
						}
						if (choice == WbSwingUtilities.IGNORE_ALL)
						{
							// if we ignore all errors we should do a commit at the
							// end in order to ensure that the delete's which were
							// successful are committed.
							hasError = false;
							ignoreAll = true;
						}
					}
				}
			}

			boolean doCommit = true && !cancelled;
			try
			{
				if (!doCommitEach)
				{
					if (hasError || cancelled)
					{
						doCommit = false;
						this.connection.rollback();
					}
					else
					{
						this.connection.commit();
					}
				}
			}
			catch (SQLException e)
			{
				LogMgr.logError("TableDeleterUI.doDelete()", "Error on commit/rollback", e);
				String msg = null;

				if (doCommit)
				{
					ResourceMgr.getString("ErrCommit");
				}
				else
				{
					msg = ResourceMgr.getString("ErrRollbackTableData");
				}
				msg = msg.replaceAll("%error%", e.getMessage());

				WbSwingUtilities.showErrorMessage(this.dialog, msg);
			}
		}
		finally
		{
			SqlUtil.closeStatement(currentStatement);
			this.connection.setBusy(false);
		}

		this.fireTableDeleted(tables);

		this.statusLabel.setText("");
		if (!hasError)
		{
			this.closeWindow();
		}
	}

	protected void deleteTable(final TableIdentifier table, final boolean useTruncate, final boolean doCommit)
		throws SQLException
	{
		try
		{
			String deleteSql = getDeleteStatement(table, useTruncate);
			LogMgr.logInfo("TableDeleterUI.deleteTable()", "Executing: [" + deleteSql + "] to delete target table...");
			currentStatement.executeUpdate(deleteSql);
			if (doCommit && !this.connection.getAutoCommit())
			{
				this.connection.commit();
			}
		}
		catch (SQLException e)
		{
			if (doCommit && !this.connection.getAutoCommit())
			{
				this.connection.rollback();
			}
			LogMgr.logError("TableDeleterUI.deleteTable()", "Error when deleting table!", e);
			throw e;
		}
	}

	protected String getDeleteStatement(final TableIdentifier table, final boolean useTruncate)
	{
		String deleteSql = null;
		String tableName = table.getTableExpression(this.connection);
		if (useTruncate)
		{
			deleteSql = "TRUNCATE TABLE " + tableName;
		}
		else
		{
			deleteSql = "DELETE FROM " + tableName;
		}
		return deleteSql;
	}

	protected void showScript()
	{
		boolean doCommitEach = this.commitEach.isSelected();
		boolean useTruncate = this.useTruncateCheckBox.isSelected();
		if (useTruncate)
		{
			doCommitEach = false;
		}
		StringBuilder script = new StringBuilder(objectNames.size() * 30);
		for (TableIdentifier table : objectNames)
		{
			String sql = this.getDeleteStatement(table, useTruncate);
			script.append(sql);
			script.append(";\n");
			if (doCommitEach)
			{
				script.append("COMMIT;\n\n");
			}
		}

		if (!doCommitEach)
		{
			script.append("\nCOMMIT;\n");
		}

		final EditWindow w = new EditWindow(this.dialog, ResourceMgr.getString("TxtWindowTitleGeneratedScript"), script.toString(), "workbench.tabledeleter.scriptwindow", true);
		w.setVisible(true);
		w.dispose();
	}

	public boolean dialogWasCancelled()
	{
		return this.cancelled;
	}

	public void setObjects(List<TableIdentifier> objects)
	{
		this.objectNames = objects;
		int numNames = this.objectNames.size();

		String[] display = new String[numNames];
		for (int i = 0; i < numNames; i++)
		{
			display[i] = this.objectNames.get(i).toString();
		}
		this.objectList.setListData(display);
	}

	public void showDialog(Frame aParent)
	{
		this.dialog = new JDialog(aParent, ResourceMgr.getString("TxtDeleteTableData"), false);
		this.dialog.getContentPane().add(this);
		this.dialog.pack();
		if (this.dialog.getWidth() < 200)
		{
			this.dialog.setSize(200, this.dialog.getHeight());
		}
		WbSwingUtilities.center(this.dialog, aParent);
		this.cancelled = true;
		this.dialog.setVisible(true);
	}

	public void addDeleteListener(TableDeleteListener listener)
	{
		if (this.deleteListener == null)
		{
			this.deleteListener = new ArrayList<TableDeleteListener>();
		}
		this.deleteListener.add(listener);
	}

	public void removeDeleteListener(TableDeleteListener listener)
	{
		if (this.deleteListener == null)
		{
			return;
		}
		this.deleteListener.remove(listener);
	}

	protected void fireTableDeleted(List tables)
	{
		if (this.deleteListener == null)
		{
			return;
		}
		for (TableDeleteListener l : this.deleteListener)
		{
			l.tableDataDeleted(tables);
		}
	}

	public void windowActivated(WindowEvent e)
	{
	}

	public void windowClosed(WindowEvent e)
	{
	}

	public void windowClosing(WindowEvent e)
	{
		this.cancelled = true;
		closeWindow();
	}

	public void windowDeactivated(WindowEvent e)
	{
	}

	public void windowDeiconified(WindowEvent e)
	{
	}

	public void windowIconified(WindowEvent e)
	{
	}

	public void windowOpened(WindowEvent e)
	{
	}
	
	
  // Variables declaration - do not modify//GEN-BEGIN:variables
  public javax.swing.JCheckBox addMissingTables;
  public javax.swing.ButtonGroup buttonGroup1;
  public javax.swing.JPanel buttonPanel;
  public javax.swing.JButton cancelButton;
  public javax.swing.JButton checkFKButton;
  public javax.swing.JRadioButton commitAtEnd;
  public javax.swing.JRadioButton commitEach;
  public javax.swing.JButton deleteButton;
  public javax.swing.JPanel jPanel1;
  public javax.swing.JPanel jPanel2;
  public javax.swing.JPanel jPanel3;
  public javax.swing.JScrollPane jScrollPane1;
  public javax.swing.JPanel mainPanel;
  public javax.swing.JList objectList;
  public javax.swing.JPanel optionPanel;
  public javax.swing.JButton showScript;
  public javax.swing.JLabel statusLabel;
  public javax.swing.JCheckBox useTruncateCheckBox;
  // End of variables declaration//GEN-END:variables
}
