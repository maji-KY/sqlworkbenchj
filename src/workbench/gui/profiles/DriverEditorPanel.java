/*
 * DriverEditorPanel.java
 *
 * Created on 8. Juli 2002, 21:39
 */

package workbench.gui.profiles;

import java.awt.Frame;
import java.io.File;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;
import workbench.WbManager;
import workbench.db.DbDriver;
import workbench.gui.components.TextComponentMouseListener;
import workbench.resource.ResourceMgr;

/**
 *
 * @author  workbench@kellerer.org
 */
public class DriverEditorPanel extends javax.swing.JPanel
{
	private DbDriver currentDriver;

	/** Creates new form BeanForm */
	public DriverEditorPanel()
	{
		initComponents();
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	private void initComponents()//GEN-BEGIN:initComponents
	{
		java.awt.GridBagConstraints gridBagConstraints;

		lblName = new javax.swing.JLabel();
		tfName = new javax.swing.JTextField();
		lblClassName = new javax.swing.JLabel();
		tfClassName = new javax.swing.JTextField();
		lblLibrary = new javax.swing.JLabel();
		tfLibrary = new javax.swing.JTextField();
		jPanel1 = new javax.swing.JPanel();
		jButton1 = new javax.swing.JButton();
		lblSample = new javax.swing.JLabel();
		tfSampleUrl = new javax.swing.JTextField();

		setLayout(new java.awt.GridBagLayout());

		setFont(null);
		lblName.setFont(null);
		lblName.setText(ResourceMgr.getString("LabelDriverName"));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(11, 10, 0, 7);
		add(lblName, gridBagConstraints);

		tfName.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		tfName.setMinimumSize(new java.awt.Dimension(50, 20));
		tfName.setPreferredSize(new java.awt.Dimension(100, 20));
		tfName.addMouseListener(new TextComponentMouseListener());
		tfName.addFocusListener(new java.awt.event.FocusAdapter()
		{
			public void focusLost(java.awt.event.FocusEvent evt)
			{
				DriverEditorPanel.this.focusLost(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(11, 3, 0, 3);
		add(tfName, gridBagConstraints);

		lblClassName.setFont(null);
		lblClassName.setText(ResourceMgr.getString("LabelDriverClass"));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 7);
		add(lblClassName, gridBagConstraints);

		tfClassName.setColumns(10);
		tfClassName.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		tfClassName.addMouseListener(new TextComponentMouseListener());
		tfClassName.addFocusListener(new java.awt.event.FocusAdapter()
		{
			public void focusLost(java.awt.event.FocusEvent evt)
			{
				DriverEditorPanel.this.focusLost(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(0, 3, 0, 3);
		add(tfClassName, gridBagConstraints);

		lblLibrary.setFont(null);
		lblLibrary.setText(ResourceMgr.getString("LabelDriverLibrary"));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 7);
		add(lblLibrary, gridBagConstraints);

		tfLibrary.setColumns(10);
		tfLibrary.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		tfLibrary.addMouseListener(new TextComponentMouseListener());
		tfLibrary.addFocusListener(new java.awt.event.FocusAdapter()
		{
			public void focusLost(java.awt.event.FocusEvent evt)
			{
				DriverEditorPanel.this.focusLost(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(0, 3, 0, 3);
		add(tfLibrary, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
		gridBagConstraints.weighty = 1.0;
		add(jPanel1, gridBagConstraints);

		jButton1.setFont(null);
		jButton1.setText("...");
		jButton1.setMaximumSize(new java.awt.Dimension(20, 20));
		jButton1.setMinimumSize(new java.awt.Dimension(20, 20));
		jButton1.setPreferredSize(new java.awt.Dimension(20, 20));
		jButton1.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				selectLibrary(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 5;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 3);
		add(jButton1, gridBagConstraints);

		lblSample.setFont(null);
		lblSample.setText(ResourceMgr.getString("LabelSampleUrl"));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 7);
		add(lblSample, gridBagConstraints);

		tfSampleUrl.setColumns(10);
		tfSampleUrl.setFont(null);
		tfSampleUrl.setHorizontalAlignment(javax.swing.JTextField.LEFT);
		tfSampleUrl.addMouseListener(new TextComponentMouseListener());
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(0, 3, 0, 3);
		add(tfSampleUrl, gridBagConstraints);

	}//GEN-END:initComponents

	private void focusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event_focusLost
	{//GEN-HEADEREND:event_focusLost
		this.updateDriver();
	}//GEN-LAST:event_focusLost

	private void selectLibrary(java.awt.event.ActionEvent evt)//GEN-FIRST:event_selectLibrary
	{//GEN-HEADEREND:event_selectLibrary
		String lastDir = WbManager.getSettings().getLastLibraryDir();
		JFileChooser jf = new JFileChooser(lastDir);
		jf.setFileFilter(new JarFileFilter());
		int answer = jf.showOpenDialog(SwingUtilities.getWindowAncestor(this));
		if (answer == JFileChooser.APPROVE_OPTION)
		{
			this.tfLibrary.setText(jf.getSelectedFile().getAbsolutePath());
			this.updateDriver();
			WbManager.getSettings().setLastLibraryDir(jf.getCurrentDirectory().getAbsolutePath());
		}
	}//GEN-LAST:event_selectLibrary

	public void setDriver(DbDriver aDriver)
	{
		this.currentDriver = aDriver;
		this.tfName.setText(aDriver.getName());
		this.tfClassName.setText(aDriver.getDriverClass());
		this.tfLibrary.setText(aDriver.getLibrary());
		this.tfSampleUrl.setText(aDriver.getSampleUrl());
	}

	void updateDriver()
	{
		this.currentDriver.setName(tfName.getText());
		this.currentDriver.setDriverClass(tfClassName.getText());
		this.currentDriver.setLibrary(tfLibrary.getText());
		this.currentDriver.setSampleUrl(tfSampleUrl.getText());
	}

	public DbDriver getDriver()
	{
		this.updateDriver();
		return this.currentDriver;
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JPanel jPanel1;
	private javax.swing.JButton jButton1;
	private javax.swing.JTextField tfLibrary;
	private javax.swing.JLabel lblLibrary;
	private javax.swing.JLabel lblName;
	private javax.swing.JTextField tfName;
	private javax.swing.JTextField tfSampleUrl;
	private javax.swing.JTextField tfClassName;
	private javax.swing.JLabel lblClassName;
	private javax.swing.JLabel lblSample;
	// End of variables declaration//GEN-END:variables

	public static void main(String args[])
	{
		JDialog d = new JDialog((Frame)null, "Driver", true);
		DriverEditorPanel editor = new DriverEditorPanel();
		d.getContentPane().add(editor);
		DbDriver test = new DbDriver();
		test.setLibrary("this is a very long string path to a library");
		editor.setDriver(test);
		d.pack();
		d.show();
		//WbManager.getSettings().saveSettings();
	}
	private class JarFileFilter extends FileFilter
	{
		public String getExtension(File f)
		{
			String ext = null;
			String s = f.getName();
			int i = s.lastIndexOf('.');

			if (i > 0 &&  i < s.length() - 1)
			{
				ext = s.substring(i+1).toLowerCase();
			}
			return ext;
		}

		public boolean accept(File f)
		{
			if (f.isDirectory())
			{
				return true;
			}

			String extension = this.getExtension(f);
			if (extension != null)
			{
				if (extension.equalsIgnoreCase("jar") || extension.equals("zip"))
					return true;
			}
			return false;
		}

		public String getDescription()
		{
			String s = ResourceMgr.getString("MsgArchivesFilterName");
			return s;
		}

		/** Whether the given file is accepted by this filter.
		 *
		 */

	}

}
