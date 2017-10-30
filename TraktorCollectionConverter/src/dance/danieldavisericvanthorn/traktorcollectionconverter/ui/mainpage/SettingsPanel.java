package dance.danieldavisericvanthorn.traktorcollectionconverter.ui.mainpage;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dance.danieldavisericvanthorn.traktorcollectionconverter.settings.SettingsManager;
import dance.danieldavisericvanthorn.traktorcollectionconverter.ui.filechooser.TraktorFileType;

public class SettingsPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5746874836269587411L;

	public SettingsPanel() {
		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);

		JLabel chooseSettingsTSI = new JLabel("Choose your original settings file:");
		addComponent(gbl, chooseSettingsTSI, null, 0, 0, 1, 1, 1, 1, GridBagConstraints.VERTICAL,
				GridBagConstraints.WEST);

		JFileChooser settingsTSIChooser = new JFileChooser();
		settingsTSIChooser.setCurrentDirectory(new File(SettingsManager.getTraktorSettingsPath()));
		settingsTSIChooser.setFileFilter(TraktorFileType.SETTINGS.getFileFilter());
		settingsTSIChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		addComponent(gbl, settingsTSIChooser, null, 1, 0, 1, 1, 1, 1, GridBagConstraints.VERTICAL,
				GridBagConstraints.WEST);

	}

	/**
	 * Adds a component to the given {@link Container}.
	 *
	 * @param cont
	 *            - The {@link Container} the {@link Component} should be added
	 *            to. The container has to be in {@link GridBagLayout}.
	 * @param gbl
	 *            - The {@link Layout} that has been added to the given
	 *            container.
	 * @param c
	 *            - The {@link Component} to be added to the container.
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
	 * @return The {@link GridBagConstraints} to apply more options.
	 */
	private GridBagConstraints addComponent(GridBagLayout gbl, Component c, Insets insets, int x, int y, int width,
			int height, double weightx, double weighty, int fill, int anchor) {
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
		add(c);
		return gbc;
	}

}
