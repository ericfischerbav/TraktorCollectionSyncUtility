package dance.danieldavisericvanthorn.traktorcollectionsyncutility.ui.utils;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class GridBagLayoutUtils {

	private GridBagLayoutUtils() {

	}

	/**
	 * Adds a component to the given {@link Container}.
	 * 
	 * @param frame
	 *            - The {@link Container} to display the component in.
	 * @param gbl
	 *            - The {@link Layout} that has been added to the given
	 *            container.
	 * @param c
	 *            - The {@link Component} to be added to the container.
	 * @param insets
	 *            - Can be <code>null</code>
	 * @param x
	 *            - The position's x coordinate <code>(int)</code>.
	 * @param y
	 *            - The position's y coordinate <code>(int)</code>.
	 * @param width
	 *            - The width of the cell the component is displayed in (SWT:
	 *            wSpan) <code>(int)</code>.
	 * @param height
	 *            - The height of the cell the component is displayed in (SWT:
	 *            hSpan) <code>(int)</code>.
	 * @param weightx
	 * @param weighty
	 * @param fill
	 *            - One of <code>GridBagConstraints.VERTICAL</code> or
	 *            <code>GridBagConstraints.HORIZONTAL</code>
	 * @param anchor
	 *            - The position the component should be pinned to. One of
	 *            <code>GridBagConstraints.WEST</code>,
	 *            <code>GridBagConstraints.NORTH</code>,
	 *            <code>GridBagConstraints.EAST</code> and
	 *            <code>GridBagConstraints.SOUTH</code>
	 * @return The {@link GridBagConstraints} to apply more options.
	 */
	public static GridBagConstraints addComponent(Container frame, GridBagLayout gbl, Component c, Insets insets, int x,
			int y, int width, int height, double weightx, double weighty, int fill, int anchor) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = width;
		gbc.gridheight = height;
		gbc.weightx = weightx;
		gbc.weighty = weighty;
		if (insets != null) {
			gbc.insets = insets;
		}
		gbc.fill = fill;
		gbc.anchor = anchor;
		gbl.setConstraints(c, gbc);
		frame.add(c);
		return gbc;
	}

}
