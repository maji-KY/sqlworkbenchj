/*
 * DbExplorerOptionsPanel.java
 *
 * This file is part of SQL Workbench/J, http://www.sql-workbench.net
 *
 * Copyright 2002-2006, Thomas Kellerer
 * No part of this code maybe reused without the permission of the author
 *
 * To contact the author please send an email to: support@sql-workbench.net
 *
 */
package workbench.gui.settings;

import javax.swing.JPanel;
import workbench.gui.components.WbCheckBoxLabel;
import workbench.resource.ResourceMgr;
import workbench.resource.Settings;

/**
 *
 * @author  support@sql-workbench.net
 */
public class DbExplorerOptionsPanel
	extends JPanel
	implements workbench.interfaces.Restoreable
{

	/** Creates new form DbExplorerOptionsPanel */
	public DbExplorerOptionsPanel()
	{
		initComponents();
		restoreSettings();
	}

	public void saveSettings()
	{
		Settings set = Settings.getInstance();
		set.setRetrieveDbExplorer(retrieveDbExplorer.isSelected());
		set.setShowDbExplorerInMainWindow(this.showDbExplorer.isSelected());
		//set.setStoreExplorerSchema(this.rememberSchema.isSelected());
		set.setStoreExplorerObjectType(this.rememberObject.isSelected());
		set.setProperty("workbench.dbexplorer.defTableType", this.defTableTypeField.getText());
	}

	public void restoreSettings()
	{

	}
	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
  // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
  private void initComponents()
  {
    java.awt.GridBagConstraints gridBagConstraints;

    dbExplorerLabel = new WbCheckBoxLabel();
    showDbExplorer = new javax.swing.JCheckBox();
    retrieveDbExplorer = new javax.swing.JCheckBox();
    retrieveDbExplorerLabel = new WbCheckBoxLabel();
    defTableTypeLabel = new javax.swing.JLabel();
    defTableTypeField = new javax.swing.JTextField();
    rememberObjectLabel = new WbCheckBoxLabel();
    rememberObject = new javax.swing.JCheckBox();

    setLayout(new java.awt.GridBagLayout());

    dbExplorerLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
    dbExplorerLabel.setLabelFor(showDbExplorer);
    dbExplorerLabel.setText(ResourceMgr.getString("LblDbExplorerCheckBox"));
    dbExplorerLabel.setToolTipText(ResourceMgr.getDescription("LblDbExplorerCheckBox"));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(7, 12, 0, 0);
    add(dbExplorerLabel, gridBagConstraints);

    showDbExplorer.setFont(null);
    showDbExplorer.setSelected(Settings.getInstance().getShowDbExplorerInMainWindow());
    showDbExplorer.setText("");
    showDbExplorer.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
    showDbExplorer.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
    showDbExplorer.setIconTextGap(5);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(3, 5, 0, 11);
    add(showDbExplorer, gridBagConstraints);

    retrieveDbExplorer.setFont(null);
    retrieveDbExplorer.setSelected(Settings.getInstance().getRetrieveDbExplorer());
    retrieveDbExplorer.setText("");
    retrieveDbExplorer.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
    retrieveDbExplorer.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
    retrieveDbExplorer.setIconTextGap(5);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(4, 5, 0, 5);
    add(retrieveDbExplorer, gridBagConstraints);

    retrieveDbExplorerLabel.setLabelFor(retrieveDbExplorer);
    retrieveDbExplorerLabel.setText(ResourceMgr.getString("LblRetrieveDbExplorer"));
    retrieveDbExplorerLabel.setToolTipText(ResourceMgr.getDescription("LblRetrieveDbExplorer"));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(8, 12, 0, 0);
    add(retrieveDbExplorerLabel, gridBagConstraints);

    defTableTypeLabel.setText(ResourceMgr.getString("LblDefTableType"));
    defTableTypeLabel.setToolTipText(ResourceMgr.getDescription("LblDefTableType"));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 4;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.weighty = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(11, 12, 0, 0);
    add(defTableTypeLabel, gridBagConstraints);

    defTableTypeField.setHorizontalAlignment(javax.swing.JTextField.LEFT);
    defTableTypeField.setText(Settings.getInstance().getProperty("workbench.dbexplorer.defTableType", null));
    defTableTypeField.setMinimumSize(new java.awt.Dimension(72, 20));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 4;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.weighty = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(9, 9, 0, 15);
    add(defTableTypeField, gridBagConstraints);

    rememberObjectLabel.setLabelFor(rememberObject);
    rememberObjectLabel.setText(ResourceMgr.getString("LblRememberObjectType"));
    rememberObjectLabel.setToolTipText(ResourceMgr.getDescription("LblRememberObjectType"));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 3;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(10, 12, 0, 0);
    add(rememberObjectLabel, gridBagConstraints);

    rememberObject.setSelected(Settings.getInstance().getStoreExplorerObjectType());
    rememberObject.setText("");
    rememberObject.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
    rememberObject.setMargin(new java.awt.Insets(0, 0, 0, 0));
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 3;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.insets = new java.awt.Insets(9, 9, 0, 0);
    add(rememberObject, gridBagConstraints);

  }// </editor-fold>//GEN-END:initComponents


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JLabel dbExplorerLabel;
  private javax.swing.JTextField defTableTypeField;
  private javax.swing.JLabel defTableTypeLabel;
  private javax.swing.JCheckBox rememberObject;
  private javax.swing.JLabel rememberObjectLabel;
  private javax.swing.JCheckBox retrieveDbExplorer;
  private javax.swing.JLabel retrieveDbExplorerLabel;
  private javax.swing.JCheckBox showDbExplorer;
  // End of variables declaration//GEN-END:variables

}
