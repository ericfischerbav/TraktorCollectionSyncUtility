package dance.danieldavisericvanthorn.traktorcollectionconverter.ui.mainpage;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dance.danieldavisericvanthorn.traktorcollectionconverter.converter.SettingsParser;
import dance.danieldavisericvanthorn.traktorcollectionconverter.enums.TraktorFileType;
import dance.danieldavisericvanthorn.traktorcollectionconverter.interfaces.Redrawer;
import dance.danieldavisericvanthorn.traktorcollectionconverter.settings.InternalSettingsManager;
import dance.danieldavisericvanthorn.traktorcollectionconverter.ui.filechooser.TraktorFileChooserFrame;
import dance.danieldavisericvanthorn.traktorcollectionconverter.ui.utils.GridBagLayoutUtils;

public class SettingsPanel extends JPanel {

	/**
	 *
	 */
	private static final long serialVersionUID = -5746874836269587411L;

	private GridBagLayout gbl = new GridBagLayout();
	private JTextField pathSettingsTSI = null;
	private JButton action;
	private JButton settingsTSIButton;
	private SettingsParser settingsParser;
	private Redrawer mainframe;
	private JLabel pathsLabel;
	private JTextField rootPathField;
	private JButton rootPathButton;

	public SettingsPanel(Redrawer mainframe) {
		this.mainframe = mainframe;

		setLayout(gbl);

		pathSettingsTSI = new JTextField();
		settingsTSIButton = new JButton("...");
		createChooseFileCombi("Choose your original settings file:", pathSettingsTSI, settingsTSIButton, 1,
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						TraktorFileChooserFrame fileChooser = new TraktorFileChooserFrame(TraktorFileType.SETTINGS);
						if (fileChooser.showSaveDialog(SettingsPanel.this) == JFileChooser.APPROVE_OPTION) {
							String path = fileChooser.getSelectedFile().getAbsolutePath();
							pathSettingsTSI.setText(path);
							settingsParser = new SettingsParser(pathSettingsTSI.getText());
							redrawPanel();
						}

					}
				});
		pathSettingsTSI.setText(InternalSettingsManager.getTraktorPath(TraktorFileType.SETTINGS));

	}

	private void createChooseFileCombi(String label, JTextField text, JButton button, int line,
			ActionListener actionListener) {
		JLabel chooseSettingsTSI = new JLabel(label);
		GridBagLayoutUtils.addComponent(this, gbl, chooseSettingsTSI, null, 0, line, 1, 1, 1, 1,
				GridBagConstraints.VERTICAL, GridBagConstraints.WEST);

		text.setEnabled(true);
		text.setMinimumSize(new Dimension(300, 30));
		text.setMaximumSize(new Dimension(600, 50));
		GridBagLayoutUtils.addComponent(this, gbl, text, null, 1, line, 2, 1, 1, 1, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.WEST);

		button.addActionListener(actionListener);
		GridBagLayoutUtils.addComponent(this, gbl, button, null, 3, line, 1, 1, 1, 1, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.EAST);
	}

	private void createSettingsParserOutputLabel() {
		pathsLabel = new JLabel(settingsParser.toString());
		GridBagLayoutUtils.addComponent(this, gbl, pathsLabel, null, 0, 2, 4, 1, 4, 1, GridBagConstraints.VERTICAL,
				GridBagConstraints.WEST);
	}

	private void createSelectNewRootDirectoryButton() {
		action = new JButton("Select new root directory");
		addButtonToComponent();
	}

	private void redrawPanel() {

		if (settingsParser != null) {
			action = null;
			settingsTSIButton.setEnabled(false);
			rootPathField = new JTextField();
			rootPathButton = new JButton("...");
			createChooseFileCombi("Choose new Root path:", rootPathField, rootPathButton, 4, new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					TraktorFileChooserFrame fileChooser = new TraktorFileChooserFrame(TraktorFileType.FOLDER);
					if (fileChooser.showSaveDialog(SettingsPanel.this) == JFileChooser.APPROVE_OPTION) {
						String path = fileChooser.getCurrentDirectory().getAbsolutePath();
						rootPathField.setText(path);
						redrawPanel();
					}
				}
			});
			repaint();
			mainframe.redraw();
		}
	}

	private void addButtonToComponent() {
		GridBagLayoutUtils.addComponent(this, gbl, action, null, 0, 3, 1, 1, 1, 1, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.WEST);
	}

}
