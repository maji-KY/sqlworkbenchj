/*
 * ToolDefinitionPanel.java
 *
 * This file is part of SQL Workbench/J, http://www.sql-workbench.net
 *
 * Copyright 2002-2011, Thomas Kellerer
 * No part of this code maybe reused without the permission of the author
 *
 * To contact the author please send an email to: support@sql-workbench.net
 *
 */
package workbench.gui.settings;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import workbench.gui.WbSwingUtilities;
import workbench.gui.components.FlatButton;
import workbench.gui.components.StringPropertyEditor;
import workbench.gui.components.TextComponentMouseListener;
import workbench.gui.components.WbFileChooser;
import workbench.interfaces.SimplePropertyEditor;
import workbench.log.LogMgr;
import workbench.resource.ResourceMgr;
import workbench.util.ExceptionUtil;
import workbench.util.StringUtil;
import workbench.util.ToolDefinition;

/**
 *
 * @author  Thomas Kellerer
 */
public class ToolDefinitionPanel
	extends JPanel
	implements ActionListener
{
	private ToolDefinition currentTool;
	private PropertyChangeListener changeListener;

	public ToolDefinitionPanel()
	{
		super();
		initComponents();
		tfName.addFocusListener(new FocusAdapter()
		{
			public void focusLost(FocusEvent evt)
			{
				nameFieldFocusLost(evt);
			}
		});

	}

	public void setPropertyListener(PropertyChangeListener l)
	{
		this.changeListener = l;
	}

	public void nameFieldFocusLost(FocusEvent evt)
	{
		if (this.changeListener != null)
		{
			PropertyChangeEvent pEvt = new PropertyChangeEvent(this.currentTool, "name", null, tfName.getText());
			this.changeListener.propertyChange(pEvt);
		}
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {
    java.awt.GridBagConstraints gridBagConstraints;

    lblName = new javax.swing.JLabel();
    tfName = new StringPropertyEditor();
    lblPath = new javax.swing.JLabel();
    tfPath = new StringPropertyEditor();
    selectLibButton = new FlatButton();

    setLayout(new java.awt.GridBagLayout());

    lblName.setText(ResourceMgr.getString("LblLnFName")); // NOI18N
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.insets = new java.awt.Insets(4, 10, 0, 7);
    add(lblName, gridBagConstraints);

    tfName.setHorizontalAlignment(javax.swing.JTextField.LEFT);
    tfName.setName("name"); // NOI18N
    tfName.addMouseListener(new TextComponentMouseListener());
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(4, 3, 0, 3);
    add(tfName, gridBagConstraints);

    lblPath.setText(ResourceMgr.getString("LblExePath")); // NOI18N
    lblPath.setToolTipText(ResourceMgr.getString("d_LblExePath")); // NOI18N
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.weighty = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(2, 10, 0, 7);
    add(lblPath, gridBagConstraints);

    tfPath.setColumns(10);
    tfPath.setHorizontalAlignment(javax.swing.JTextField.LEFT);
    tfPath.setName("commandLine"); // NOI18N
    tfPath.addMouseListener(new TextComponentMouseListener());
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(2, 3, 0, 3);
    add(tfPath, gridBagConstraints);

    selectLibButton.setText("...");
    selectLibButton.setMaximumSize(new java.awt.Dimension(20, 20));
    selectLibButton.setMinimumSize(new java.awt.Dimension(20, 20));
    selectLibButton.setPreferredSize(new java.awt.Dimension(20, 20));
    selectLibButton.addActionListener(this);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(2, 0, 0, 3);
    add(selectLibButton, gridBagConstraints);
  }

  // Code for dispatching events from components to event handlers.

  public void actionPerformed(java.awt.event.ActionEvent evt) {
    if (evt.getSource() == selectLibButton) {
      ToolDefinitionPanel.this.selectTool(evt);
    }
  }// </editor-fold>//GEN-END:initComponents

	private void selectTool(java.awt.event.ActionEvent evt)//GEN-FIRST:event_selectTool
	{//GEN-HEADEREND:event_selectTool
		try
		{
			File current = new File(this.tfPath.getText());
			JFileChooser jf = new WbFileChooser();
			if (current.isAbsolute())
			{
				File p = current.getParentFile();
				if (p != null) jf.setCurrentDirectory(p);
			}
			int answer = jf.showOpenDialog(SwingUtilities.getWindowAncestor(this));
			if (answer == JFileChooser.APPROVE_OPTION)
			{
				File f = jf.getSelectedFile();
				this.tfPath.setText(f.getAbsolutePath());
			}
		}
		catch (Throwable e)
		{
			LogMgr.logError("ToolDefinitionPanel.selectTool()", "Error selecting file", e);
			WbSwingUtilities.showErrorMessage(ExceptionUtil.getDisplay(e));
		}
	}//GEN-LAST:event_selectTool

	private void initPropertyEditors()
	{
		for (int i=0; i < this.getComponentCount(); i++)
		{
			Component c = this.getComponent(i);
			if (c instanceof SimplePropertyEditor)
			{
				SimplePropertyEditor editor = (SimplePropertyEditor)c;
				String property = c.getName();
				if (!StringUtil.isEmptyString(property))
				{
					editor.setSourceObject(this.currentTool, property);
					editor.setImmediateUpdate(true);
				}
			}
		}
	}


	public void setDefinition(ToolDefinition tool)
	{
		this.currentTool = tool;
		initPropertyEditors();
	}

	public ToolDefinition getDefinition()
	{
		return this.currentTool;
	}

  // Variables declaration - do not modify//GEN-BEGIN:variables
  public javax.swing.JLabel lblName;
  public javax.swing.JLabel lblPath;
  public javax.swing.JButton selectLibButton;
  public javax.swing.JTextField tfName;
  public javax.swing.JTextField tfPath;
  // End of variables declaration//GEN-END:variables


}
