package dance.danieldavisericvanthorn.traktorcollectionsyncutility.ui.musicfolderselection;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dance.danieldavisericvanthorn.traktorcollectionsyncutility.enums.TraktorDirectories;
import dance.danieldavisericvanthorn.traktorcollectionsyncutility.enums.TraktorFileType;
import dance.danieldavisericvanthorn.traktorcollectionsyncutility.settings.InternalSettingsManager;
import dance.danieldavisericvanthorn.traktorcollectionsyncutility.ui.filechooser.TraktorFileChooserFrame;
import dance.danieldavisericvanthorn.traktorcollectionsyncutility.ui.interfaces.Redrawer;
import dance.danieldavisericvanthorn.traktorcollectionsyncutility.ui.utils.GridBagLayoutUtils;

public class MusicFolderPanel extends JPanel {

	/**
	 *
	 */
	private static final long serialVersionUID = -5746874836269587411L;

	private GridBagLayout gbl = new GridBagLayout();
	private Redrawer mainframe;
	private List<JTextField> fields = new ArrayList<>();
	private List<String> targetMusicPaths = InternalSettingsManager.getTargetDirecory(TraktorDirectories.MUSIC);
	private JButton finish;

	public MusicFolderPanel(Redrawer mainframe, Redrawer motherframe) {
		this.mainframe = mainframe;

		setLayout(gbl);

		List<String> originalMusicPaths = InternalSettingsManager.getOriginalDirecory(TraktorDirectories.MUSIC);
		Integer line = Integer.valueOf(1);
		for (String string : originalMusicPaths) {
			final Integer index = line - 1;
			JTextField text = new JTextField(300);
			String prefill = InternalSettingsManager.getTargetDirecory(TraktorDirectories.MUSIC).size() < index + 1 ? ""
					: InternalSettingsManager.getTargetDirecory(TraktorDirectories.MUSIC).get(index);
			text.setText(prefill == null ? "" : prefill);
			fields.add(text);
			JButton button = new JButton("...");
			createChooseFileCombi("Please select corresponding directory for " + string, text, button, line,
					new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							TraktorFileChooserFrame fileChooser = new TraktorFileChooserFrame(TraktorFileType.FOLDER,
									text.getText());
							if (fileChooser.showSaveDialog(MusicFolderPanel.this) == JFileChooser.APPROVE_OPTION) {
								String path = fileChooser.getSelectedFile().getAbsolutePath();
								text.setText(path);
								targetMusicPaths.add(index, path);
								InternalSettingsManager.setTargetDirectory(TraktorDirectories.MUSIC, targetMusicPaths);
								redrawPanel();
							}
						}
					});
			line++;
		}

		finish = new JButton("Finish");
		finish.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				motherframe.redraw();
				MusicFolderPanel.this.mainframe.close();
			}
		});
		GridBagLayoutUtils.addComponent(this, gbl, finish, null, 1, line, 1, 1, 1, 1, GridBagConstraints.VERTICAL,
				GridBagConstraints.WEST);

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

	private void redrawPanel() {

		if (allFieldsFilled()) {
			InternalSettingsManager.setTargetDirectory(TraktorDirectories.MUSIC, targetMusicPaths);
		}

		repaint();
		mainframe.redraw();
	}

	private boolean allFieldsFilled() {
		for (JTextField jTextField : fields) {
			if (jTextField.getText() == null || jTextField.getText().isEmpty()) {
				return false;
			}
		}
		return true;
	}

}
