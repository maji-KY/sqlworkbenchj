/*
 * ObjectDropperUI.java
 *
 * This file is part of SQL Workbench/J, http://www.sql-workbench.net
 *
 * Copyright 2002-2005, Thomas Kellerer
 * No part of this code maybe reused without the permission of the author
 *
 * To contact the author please send an email to: support@sql-workbench.net
 *
 */
package workbench.gui.dbobjects;

import java.awt.Frame;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.WindowConstants;

import workbench.db.ObjectDropper;
import workbench.db.WbConnection;
import workbench.gui.WbSwingUtilities;
import workbench.gui.components.NoSelectionModel;
import workbench.gui.components.WbButton;
import workbench.resource.ResourceMgr;

/**
 *
 * @author  support@sql-workbench.net
 */
public class ObjectDropperUI
	extends javax.swing.JPanel
{
	private JDialog dialog;
	private List objectNames;
	private List objectTypes;
	private WbConnection connection;
	private boolean cancelled;
	private boolean typesAreTables;

	public ObjectDropperUI()
	{
		initComponents();
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
  // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
  private void initComponents()
  {
    buttonPanel = new javax.swing.JPanel();
    dropButton = new WbButton();
    cancelButton = new WbButton();
    mainPanel = new javax.swing.JPanel();
    jScrollPane1 = new javax.swing.JScrollPane();
    objectList = new javax.swing.JList();
    optionPanel = new javax.swing.JPanel();
    checkBoxCascadeConstraints = new javax.swing.JCheckBox();

    setLayout(new java.awt.BorderLayout());

    buttonPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

    dropButton.setText(ResourceMgr.getString("LabelDrop"));
    dropButton.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        dropButtonActionPerformed(evt);
      }
    });

    buttonPanel.add(dropButton);

    cancelButton.setText(ResourceMgr.getString("LabelCancel"));
    cancelButton.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        cancelButtonActionPerformed(evt);
      }
    });

    buttonPanel.add(cancelButton);

    add(buttonPanel, java.awt.BorderLayout.SOUTH);

    mainPanel.setLayout(new java.awt.BorderLayout());

    objectList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    objectList.setSelectionModel(new NoSelectionModel());
    jScrollPane1.setViewportView(objectList);

    mainPanel.add(jScrollPane1, java.awt.BorderLayout.CENTER);

    optionPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    checkBoxCascadeConstraints.setText(ResourceMgr.getString("LabelCascadeConstraints"));
    checkBoxCascadeConstraints.setToolTipText(ResourceMgr.getDescription("LabelCascadeConstraints"));
    optionPanel.add(checkBoxCascadeConstraints);

    mainPanel.add(optionPanel, java.awt.BorderLayout.SOUTH);

    add(mainPanel, java.awt.BorderLayout.CENTER);

  }
  // </editor-fold>//GEN-END:initComponents

	private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_cancelButtonActionPerformed
	{//GEN-HEADEREND:event_cancelButtonActionPerformed
		this.dialog.setVisible(true);
		this.dialog.dispose();
		this.dialog = null;
		this.cancelled = true;
	}//GEN-LAST:event_cancelButtonActionPerformed

	private void dropButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_dropButtonActionPerformed
	{//GEN-HEADEREND:event_dropButtonActionPerformed
		WbSwingUtilities.showWaitCursor(this.dialog);
		try
		{
			ObjectDropper dropper = new ObjectDropper(this.objectNames, this.objectTypes);
			dropper.setConnection(this.connection);
			dropper.setCascadeConstraints(this.checkBoxCascadeConstraints.isSelected());
			dropper.execute();
			WbSwingUtilities.showDefaultCursor(this.dialog);
		}
		catch (Exception ex)
		{
			String msg = ex.getMessage();
			WbSwingUtilities.showErrorMessage(this.dialog, msg);
		}
		finally
		{
			WbSwingUtilities.showDefaultCursor(this.dialog);
		}
		this.dialog.setVisible(true);
		this.dialog.dispose();
		this.dialog = null;
		this.cancelled = false;
	}//GEN-LAST:event_dropButtonActionPerformed

	public void setConnection(WbConnection aConn)
	{
		this.connection = aConn;
		if (this.objectTypes != null) this.checkCascade();
	}

	public boolean dialogWasCancelled()
	{
		return this.cancelled;
	}
	public void setObjects(List objects, List types)
	{
		this.objectNames = objects;
		this.objectTypes = types;
		this.typesAreTables = false;
		int numNames = this.objectNames.size();
		int numTypes = this.objectTypes.size();

		String[] display = new String[numNames];
		for (int i=0; i < numNames; i ++)
		{
			if (i >= numTypes) continue;
			display[i] = this.objectTypes.get(i) + " " + this.objectNames.get(i);
		}
		this.objectList.setListData(display);
		if (this.connection != null) this.checkCascade();
	}

	private void checkCascade()
	{
		boolean canCascade = false;
		
		int numTypes = this.objectTypes.size();
		for (int i=0; i < numTypes; i++)
		{
			String type = (String)this.objectTypes.get(i);
			String verb = this.connection.getMetadata().getCascadeConstraintsVerb(type);
			
			// if at least one type can be dropped with CASCADE, enable the checkbox
			if (verb != null)
			{
				canCascade = true;
				break;
			}
		}
		if (!canCascade)
		{
			this.mainPanel.remove(this.optionPanel);
			this.checkBoxCascadeConstraints.setSelected(canCascade);
		}
	}

	public void showDialog(Frame aParent)
	{
		this.dialog = new JDialog(aParent, ResourceMgr.getString("TxtDropObjectsTitle"), true);
		this.dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
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

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JPanel buttonPanel;
  private javax.swing.JButton cancelButton;
  private javax.swing.JCheckBox checkBoxCascadeConstraints;
  private javax.swing.JButton dropButton;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JPanel mainPanel;
  private javax.swing.JList objectList;
  private javax.swing.JPanel optionPanel;
  // End of variables declaration//GEN-END:variables

}
