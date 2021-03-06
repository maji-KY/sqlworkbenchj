/*
 * TransferableProfileNode.java
 *
 * This file is part of SQL Workbench/J, http://www.sql-workbench.net
 *
 * Copyright 2002-2017, Thomas Kellerer
 *
 * Licensed under a modified Apache License, Version 2.0
 * that restricts the use for certain governments.
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at.
 *
 *     http://sql-workbench.net/manual/license.html
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
package workbench.gui.profiles;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.tree.TreePath;


/**
 * Handle drag and drop in the profile Tree.
 *
 * @author Thomas Kellerer
 */
class ProfileTreeTransferable
	implements Transferable
{
	private TransferableProfileNode node;

	ProfileTreeTransferable(TreePath[] tp, ProfileTree source)
	{
    node = new TransferableProfileNode(tp, source);
	}

	@Override
	public DataFlavor[] getTransferDataFlavors()
	{
		return new DataFlavor[] { ProfileFlavor.FLAVOR };
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor)
	{
		return (flavor.getRepresentationClass() == ProfileFlavor.FLAVOR.getRepresentationClass());
	}

	@Override
	public synchronized TransferableProfileNode getTransferData(DataFlavor flavor)
		throws UnsupportedFlavorException, IOException
	{
		if (isDataFlavorSupported(flavor))
		{
			return node;
		}
		else
		{
			throw new UnsupportedFlavorException(flavor);
		}
	}
}
