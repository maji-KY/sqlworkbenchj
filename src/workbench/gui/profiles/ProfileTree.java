/*
 * ProfileTree.java
 *
 * This file is part of SQL Workbench/J, http://www.sql-workbench.net
 *
 * Copyright 2002-2005, Thomas Kellerer
 * No part of this code maybe reused without the permission of the author
 *
 * To contact the author please send an email to: support@sql-workbench.net
 *
 */
package workbench.gui.profiles;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.List;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import workbench.db.ConnectionProfile;
import workbench.gui.WbSwingUtilities;
import workbench.gui.actions.DeleteListEntryAction;
import workbench.gui.actions.WbAction;
import workbench.gui.menu.CutCopyPastePopup;
import workbench.interfaces.ClipboardSupport;
import workbench.resource.ResourceMgr;
import workbench.util.StringUtil;

/**
 *
 * @author support@sql-workbench.net
 */
public class ProfileTree
	extends JTree
	implements TreeModelListener, MouseListener, ClipboardSupport, ActionListener, TreeSelectionListener
{
	private ProfileListModel profileModel;
	private DefaultMutableTreeNode[] clipboardNodes;
	private static final int CLIP_COPY = 1;
	private static final int CLIP_CUT = 2;
	private int clipboardType = 0;
	private CutCopyPastePopup popup;
	private WbAction pasteToFolderAction;
	
	public ProfileTree()
	{
		super();
		setRootVisible(false);
		putClientProperty("JTree.lineStyle", "Angled");
		setShowsRootHandles(true);
		setEditable(true);
		setExpandsSelectedPaths(true);
		addMouseListener(this);
		getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
		addTreeSelectionListener(this);
		DefaultTreeCellRenderer rend = (DefaultTreeCellRenderer)getCellRenderer();
		rend.setOpenIcon(ResourceMgr.getImage("Tree"));
		rend.setClosedIcon(ResourceMgr.getImage("Tree"));
		rend.setLeafIcon(ResourceMgr.getImage("profile"));
		
		InputMap im = this.getInputMap(WHEN_FOCUSED);
		ActionMap am = this.getActionMap();
		
		this.popup = new CutCopyPastePopup(this);
		
		WbAction a = popup.getPasteAction();
		a.addToInputMap(im, am);
		
		a = popup.getCopyAction();
		a.addToInputMap(im, am);
		
		a = popup.getCutAction();
		a.addToInputMap(im, am);

		pasteToFolderAction = new WbAction(this, "pasteToFolder");
		pasteToFolderAction.removeIcon();
		pasteToFolderAction.initMenuDefinition("MnuTxtPasteNewFolder");
		popup.addAction(pasteToFolderAction, false);
		
	}

	public void setDeleteAction(DeleteListEntryAction delete)
	{
		this.popup.addSeparator();
		this.popup.add(delete);
	}
	
	public void setModel(TreeModel model)
	{
		super.setModel(model);
		if (model instanceof ProfileListModel)
		{
			this.profileModel = (ProfileListModel)model;
		}
		model.addTreeModelListener(this);
	}	

	public boolean isPathEditable(TreePath path)
	{
		if (path == null) return false;
		// Only allow editing of groups
		if (path.getPathCount() != 2) return false;
		
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)path.getLastPathComponent();
		
//		Object g = node.getUserObject();
		return node.getAllowsChildren();
//		if (g instanceof GroupNode) return true;
//		{
//			GroupNode gnode = (GroupNode)g;
//			return !gnode.isDefaultGroup();
//		}
//		return false;
	}

	public void treeNodesChanged(TreeModelEvent e)
	{
		Object[] changed = e.getChildren();
		DefaultMutableTreeNode group = (DefaultMutableTreeNode)changed[0];
		Object data = group.getUserObject();
		
		if (group.getAllowsChildren())
		{
			String newGroup = null;
			if (data instanceof String)
			{
				// When a group was edited, the tree puts a String 
				// object into the user object. As we rely on having
				// a GroupNode object as the UserObject, this has
				// to be re-assigned here!
				newGroup = (String)data;
				//group.setUserObject(newGroup);
			}
//			else if (data instanceof GroupNode)
//			{
//				GroupNode groupNode = (GroupNode)data;
//				newGroup = groupNode.getGroup();
//			}
			
			int count = profileModel.getChildCount(group);
			for (int i = 0; i < count; i++)
			{
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)profileModel.getChild(group,i);
				ConnectionProfile prof = (ConnectionProfile)node.getUserObject();
				prof.setGroup(newGroup);
				this.profileModel.profileGroupModified(prof);
			}
		}
		else if (data instanceof ConnectionProfile)
		{
			// If the connection profile has changed, the title
			// of the profile possibly changed as well, so we need to
			// trigger a repaint to display the correct title
			// in the tree
			this.repaint();
		}
	}

	public void expandAll()
	{
		TreePath[] groups = this.profileModel.getGroupNodes();
		for (int i = 0; i < groups.length; i++)
		{
			if (groups[i] != null) expandPath(groups[i]);
		}
	}
	
	public void collapseAll()
	{
		TreePath[] groups = this.profileModel.getGroupNodes();
		for (int i = 0; i < groups.length; i++)
		{
			if (groups[i] != null) collapsePath(groups[i]);
		}
	}
	
	/**
	 * Expand the groups that are contained in th list. 
	 * The list is expected to contain Sting objects that identify
	 * the names of the groups. If the name is equal to {@link GroupNode#DEFAULT_GROUP_MARKER}
	 * the default group will be expanded
	 */
	public void expandGroups(List groupList)
	{
		if (groupList == null) return;
		TreePath[] groupNodes = this.profileModel.getGroupNodes();
		if (groupNodes == null) return;
		for (int i = 0; i < groupNodes.length; i++)
		{
			DefaultMutableTreeNode node = (DefaultMutableTreeNode)groupNodes[i].getLastPathComponent();
			String g = (String)node.getUserObject();
			if (groupList.contains(g))
			{
				if (!isExpanded(groupNodes[i])) expandPath(groupNodes[i]);
			}
		}
	}
	
	/**
	 * Return the names of the expaned groups.
	 * For the default group {@link GroupNode#DEFAULT_GROUP_MARKER} will 
	 * be returned instead
	 */
	public List getExpandedGroupNames()
	{
		LinkedList result = new LinkedList();
		TreePath[] groupNodes = this.profileModel.getGroupNodes();
		for (int i = 0; i < groupNodes.length; i++)
		{
			if (isExpanded(groupNodes[i]))
			{
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)groupNodes[i].getLastPathComponent();
				String g = (String)node.getUserObject();
				result.add(g);
			}
		}
		return result;
	}
	
	public void treeNodesInserted(TreeModelEvent e)
	{
	}

	public void treeNodesRemoved(TreeModelEvent e)
	{
	}

	public void treeStructureChanged(TreeModelEvent e)
	{
	}

	private boolean isGroup(TreePath p)
	{
		if (p == null) return false;
		TreeNode n = (TreeNode)p.getLastPathComponent();
		return n.getAllowsChildren();
	}

	/**
	 * Enable/disable the cut/copy/paste actions
	 * according to the current selection and the content
	 * of the "clipboard"
	 */
	private void checkActions()
	{
		boolean canPaste = this.clipboardNodes != null && onlyGroupSelected();
		boolean canCopy = onlyProfilesSelected();
		
		pasteToFolderAction.setEnabled(canPaste);

		WbAction a = popup.getPasteAction();
		a.setEnabled(canPaste);

		a = popup.getCopyAction();
		a.setEnabled(canCopy);

		a = popup.getCutAction();
		a.setEnabled(canCopy);
	}
	
	public void mouseClicked(MouseEvent e)
	{
		if (e.getButton() == MouseEvent.BUTTON3 && e.getClickCount() == 1)
		{
			TreePath p = this.getClosestPathForLocation(e.getX(), e.getY());
			if (p == null) return;
			
			if (this.getSelectionCount() == 1 || isGroup(p))
			{
				setSelectionPath(p);
			}
			checkActions();
			popup.show(this, e.getX(), e.getY());
		}
	}

	/** 
	 * Finds and selects the connection profile with the given 
	 * name. If the profile is not found, the first profile
	 * will be selected (and expanded)
	 */
	public void selectProfile(String profile)
	{
		if (profileModel == null) return;
		TreePath path = this.profileModel.getPath(profile);
		if (path == null)
		{
			path = this.profileModel.getFirstProfile();
		}
		selectPath(path);
	}
	
	/**
	 * Checks if the current selection contains only profiles
	 */
	private boolean onlyProfilesSelected()
	{
		TreePath[] selection = getSelectionPaths();
		if (selection == null) return false;
		for (int i = 0; i < selection.length; i++)
		{
			TreeNode n = (TreeNode)selection[i].getLastPathComponent();
			if (n.getAllowsChildren()) return false;
		}
		return true;
	}

	/**
	 * Checks if the current selection contains only groups
	 */
	private boolean onlyGroupSelected()
	{
		if (getSelectionCount() > 1) return false;
		TreePath[] selection = getSelectionPaths();
		if (selection == null) return false;
		for (int i = 0; i < selection.length; i++)
		{
			TreeNode n = (TreeNode)selection[i].getLastPathComponent();
			if (!n.getAllowsChildren()) return false;
		}
		return true;
	}
	
	/**
	 * Returns the currently selected Profile. If either more then one
	 * entry is selected or a group is selected, null is returned
	 *
	 * @return the selected profile if any
	 */
	public ConnectionProfile getSelectedProfile()
	{
		TreePath[] selection = getSelectionPaths();
		if (selection == null) return null;
		if (selection.length != 1) return null;
		
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)getLastSelectedPathComponent();
		if (node == null) return null;
		
		Object o = node.getUserObject();
		if (o instanceof ConnectionProfile)
		{
			ConnectionProfile prof = (ConnectionProfile)o;
			return prof;
		}
		return null;
	}
	
	public void mousePressed(MouseEvent e)
	{
	}

	public void mouseReleased(MouseEvent e)
	{
	}

	public void mouseEntered(MouseEvent e)
	{
	}

	public void mouseExited(MouseEvent e)
	{
	}

	/**
	 * Stores the selected nodes in the internal "clipboard"
	 */
	private void storeSelectedNodes()
	{
		TreePath[] p = getSelectionPaths();
		
		this.clipboardNodes = new DefaultMutableTreeNode[p.length];
		for (int i = 0; i < p.length; i++)
		{
			this.clipboardNodes[i] = (DefaultMutableTreeNode)p[i].getLastPathComponent();
		}
		
	}
	public void copy()
	{
		storeSelectedNodes();
		this.clipboardType = CLIP_COPY;
	}

	public void selectAll()
	{
	}

	public void clear()
	{
	}

	public void cut()
	{
		storeSelectedNodes();
		this.clipboardType = CLIP_CUT;
	}

	public void paste()
	{
		if (clipboardNodes == null) return;
		if (clipboardNodes.length == 0) return;

		String groupName = null;
		
		try
		{
			DefaultMutableTreeNode group = (DefaultMutableTreeNode)getLastSelectedPathComponent();
			if (group == null) return;
			if (!group.getAllowsChildren()) return;
			
			groupName = (String)group.getUserObject();
			
			for (int i = 0; i < clipboardNodes.length; i++)
			{
				Object o = clipboardNodes[i].getUserObject();
				ConnectionProfile original = null;
				if (o instanceof ConnectionProfile)
				{
					original = (ConnectionProfile)o;
				}
				if (original == null) continue;


				if (clipboardType == CLIP_CUT)
				{
					profileModel.removeNodeFromParent(clipboardNodes[i]);
					profileModel.insertNodeInto(clipboardNodes[i], group, group.getChildCount());
					original.setGroup(groupName);
				}
				else
				{
					ConnectionProfile copy = original.createCopy();
					copy.setGroup(groupName);
					DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(copy, false);
					profileModel.insertNodeInto(newNode, group, group.getChildCount());
				}
				
				profileModel.profileGroupModified(original);
			}
		}
		finally
		{
			this.clipboardType = 0;
			this.clipboardNodes = null;
		}
	}

	public void actionPerformed(ActionEvent e)
	{
		// invoked from the "paste into new folder" action
		String group = addProfileGroup();
		if (group != null)
		{
			paste();
		}
	}

	/**
	 * Prompts the user for a group name and creates a new group 
	 * with the provided name. The new group node is automatically
	 * after creation.
	 * @return the name of the new group or null if the user cancelled the name input
	 */
	public String addProfileGroup()
	{
		String group = WbSwingUtilities.getUserInput(SwingUtilities.getWindowAncestor(this), ResourceMgr.getString("LblNewProfileGroup"), "");
		if (StringUtil.isEmptyString(group)) return null;
		List groups = this.profileModel.getGroups();
		if (groups.contains(group))
		{
			WbSwingUtilities.showErrorMessage(SwingUtilities.getWindowAncestor(this), ResourceMgr.getString("ErrGroupNotUnique"));
			return null;
		}
		TreePath path = this.profileModel.addGroup(group);
		selectPath(path);
		return group;
	}
	
	public void selectPath(TreePath path)
	{
		if (path == null) return;
		expandPath(path);
		setSelectionPath(path);
		scrollPathToVisible(path);
	}

	public void valueChanged(TreeSelectionEvent e)
	{
		checkActions();
	}
	
}
