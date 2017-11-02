package dance.danieldavisericvanthorn.traktorcollectionsyncutility.ui.mainpage;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import dance.danieldavisericvanthorn.traktorcollectionsyncutility.converter.SettingsParser;
import dance.danieldavisericvanthorn.traktorcollectionsyncutility.converter.SettingsWriter;
import dance.danieldavisericvanthorn.traktorcollectionsyncutility.enums.TraktorDirectories;
import dance.danieldavisericvanthorn.traktorcollectionsyncutility.enums.TraktorFileType;
import dance.danieldavisericvanthorn.traktorcollectionsyncutility.settings.InternalSettingsManager;
import dance.danieldavisericvanthorn.traktorcollectionsyncutility.ui.enums.ErrorCase;
import dance.danieldavisericvanthorn.traktorcollectionsyncutility.ui.filechooser.TraktorFileChooserFrame;
import dance.danieldavisericvanthorn.traktorcollectionsyncutility.ui.interfaces.Redrawer;
import dance.danieldavisericvanthorn.traktorcollectionsyncutility.ui.musicfolderselection.MusicFolderSelectionFrame;
import dance.danieldavisericvanthorn.traktorcollectionsyncutility.ui.utils.GridBagLayoutUtils;

public class SettingsPanel extends JPanel {

	/**
	 *
	 */
	private static final long serialVersionUID = -5746874836269587411L;

	private GridBagLayout gbl = new GridBagLayout();
	private JTextField pathSettingsTSI = null;
	private JButton settingsTSIButton;
	private SettingsParser originalSettingsParser;
	private Redrawer mainframe;
	private JTextField rootPathField;
	private JButton rootPathButton;
	private JTextField remixsetPathField;
	private JButton remixsetPathButton;
	private JTextField loopsPathField;
	private JButton loopsPathButton;
	private JTextField recordingsPathField;
	private JButton recordingsPathButton;
	private JButton changePaths;
	private FocusListener textFieldChangeListener;

	private JTextField itunesPathField;

	private JButton itunesPathButton;

	public SettingsPanel(Redrawer mainframe) {
		this.mainframe = mainframe;

		try {
			InternalSettingsManager.loadInternalSettings();
		} catch (ParserConfigurationException | SAXException | IOException | TransformerException e3) {
			createErrorMessage(ErrorCase.SETTINGS_NOT_LOADABLE);
		}

		setLayout(gbl);

		textFieldChangeListener = new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				redrawPanel();
			}

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub

			}
		};

		pathSettingsTSI = new JTextField(300);
		pathSettingsTSI.addFocusListener(textFieldChangeListener);
		settingsTSIButton = new JButton("...");
		createChooseFileCombi("Choose your original settings file:", pathSettingsTSI, settingsTSIButton, 1,
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						TraktorFileChooserFrame fileChooser = new TraktorFileChooserFrame(TraktorFileType.SETTINGS,
								pathSettingsTSI.getText());
						if (fileChooser.showSaveDialog(SettingsPanel.this) == JFileChooser.APPROVE_OPTION) {
							String path = fileChooser.getSelectedFile().getAbsolutePath();
							pathSettingsTSI.setText(path);
							try {
								originalSettingsParser = new SettingsParser(pathSettingsTSI.getText());
							} catch (ParserConfigurationException | SAXException | IOException e2) {
								createErrorMessage(ErrorCase.SETTINGS_FILE_COULD_NOT_BE_OPENED);
							}
							try {
								InternalSettingsManager.updateOriginalSettings(originalSettingsParser);
							} catch (ParserConfigurationException | SAXException | IOException
									| TransformerException e1) {
								createErrorMessage(ErrorCase.FILE_UPDATE_NOT_POSSIBLE);
							}
							redrawPanel();
						}

					}
				});
		pathSettingsTSI.setText(InternalSettingsManager.getOriginalTraktorPath(TraktorFileType.SETTINGS));

		rootPathField = new JTextField(300);
		rootPathField.addFocusListener(textFieldChangeListener);
		rootPathButton = new JButton("...");
		createChooseFileCombi("Choose target settings file:", rootPathField, rootPathButton, 4, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				TraktorFileChooserFrame fileChooser = new TraktorFileChooserFrame(TraktorFileType.SETTINGS,
						rootPathField.getText());
				if (fileChooser.showSaveDialog(SettingsPanel.this) == JFileChooser.APPROVE_OPTION) {
					String path = fileChooser.getSelectedFile().getAbsolutePath();
					rootPathField.setText(path);
					InternalSettingsManager.setTargetTraktorPath(TraktorFileType.SETTINGS, path);
					try {
						InternalSettingsManager.loadTargetSettingsFromTSI();
					} catch (ParserConfigurationException | SAXException | IOException | TransformerException e1) {
						createErrorMessage(ErrorCase.getErrorCase(e1.getMessage()));
					}
					redrawPanel();
				}
			}
		});
		rootPathField.setText(InternalSettingsManager.getTargetTraktorPath(TraktorFileType.SETTINGS));

		remixsetPathField = new JTextField(300);
		remixsetPathField.addFocusListener(textFieldChangeListener);
		remixsetPathButton = new JButton("...");
		createChooseFileCombi("Choose target remix sets path:", remixsetPathField, remixsetPathButton, 5,
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						TraktorFileChooserFrame fileChooser = new TraktorFileChooserFrame(TraktorFileType.FOLDER,
								remixsetPathField.getText());
						if (fileChooser.showSaveDialog(SettingsPanel.this) == JFileChooser.APPROVE_OPTION) {
							String path = fileChooser.getSelectedFile().getAbsolutePath();
							remixsetPathField.setText(path);
							List<String> value = new ArrayList<>();
							value.add(path);
							InternalSettingsManager.setTargetDirectory(TraktorDirectories.REMIXSETS, value);
							redrawPanel();
						}
					}
				});
		remixsetPathField.setText(InternalSettingsManager.getTargetDirecory(TraktorDirectories.REMIXSETS).get(0));

		loopsPathField = new JTextField(300);
		loopsPathField.addFocusListener(textFieldChangeListener);
		loopsPathButton = new JButton("...");
		createChooseFileCombi("Choose target loops path:", loopsPathField, loopsPathButton, 6, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				TraktorFileChooserFrame fileChooser = new TraktorFileChooserFrame(TraktorFileType.FOLDER,
						loopsPathField.getText());
				if (fileChooser.showSaveDialog(SettingsPanel.this) == JFileChooser.APPROVE_OPTION) {
					String path = fileChooser.getSelectedFile().getAbsolutePath();
					loopsPathField.setText(path);
					List<String> value = new ArrayList<>();
					value.add(path);
					InternalSettingsManager.setTargetDirectory(TraktorDirectories.LOOPS, value);
					redrawPanel();
				}
			}
		});
		loopsPathField.setText(InternalSettingsManager.getTargetDirecory(TraktorDirectories.LOOPS).get(0));

		recordingsPathField = new JTextField(300);
		recordingsPathField.addFocusListener(textFieldChangeListener);
		recordingsPathButton = new JButton("...");
		createChooseFileCombi("Choose target recordings path:", recordingsPathField, recordingsPathButton, 7,
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						TraktorFileChooserFrame fileChooser = new TraktorFileChooserFrame(TraktorFileType.FOLDER,
								recordingsPathField.getText());
						if (fileChooser.showSaveDialog(SettingsPanel.this) == JFileChooser.APPROVE_OPTION) {
							String path = fileChooser.getSelectedFile().getAbsolutePath();
							recordingsPathField.setText(path);
							List<String> value = new ArrayList<>();
							value.add(path);
							InternalSettingsManager.setTargetDirectory(TraktorDirectories.RECORDINGS, value);
							redrawPanel();
						}
					}
				});
		recordingsPathField.setText(InternalSettingsManager.getTargetDirecory(TraktorDirectories.RECORDINGS).get(0));

		itunesPathField = new JTextField(300);
		itunesPathField.addFocusListener(textFieldChangeListener);
		itunesPathButton = new JButton("...");
		createChooseFileCombi("Choose target iTunes path:", itunesPathField, itunesPathButton, 8, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				TraktorFileChooserFrame fileChooser = new TraktorFileChooserFrame(TraktorFileType.ITUNES,
						itunesPathField.getText());
				if (fileChooser.showSaveDialog(SettingsPanel.this) == JFileChooser.APPROVE_OPTION) {
					String path = fileChooser.getSelectedFile().getAbsolutePath();
					itunesPathField.setText(path);
					List<String> value = new ArrayList<>();
					value.add(path);
					InternalSettingsManager.setTargetDirectory(TraktorDirectories.ITUNES, value);
					redrawPanel();
				}
			}
		});
		itunesPathField.setText(InternalSettingsManager.getTargetDirecory(TraktorDirectories.ITUNES).get(0));

		JButton chooseMusicFolders = new JButton("Choose music folders");
		chooseMusicFolders.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MusicFolderSelectionFrame mfsf = new MusicFolderSelectionFrame();
				mfsf.setVisible(true);
			}
		});
		GridBagLayoutUtils.addComponent(this, gbl, chooseMusicFolders, null, 1, 9, 1, 1, 1, 1,
				GridBagConstraints.VERTICAL, GridBagConstraints.WEST);

		changePaths = new JButton("Start writing");
		changePaths.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					SettingsWriter
							.updateTSIFile(InternalSettingsManager.getTargetTraktorPath(TraktorFileType.SETTINGS));
				} catch (TransformerException | ParserConfigurationException | SAXException | IOException e1) {
					createErrorMessage(ErrorCase.UPDATE_ERROR);
				}
			}
		});
		GridBagLayoutUtils.addComponent(this, gbl, changePaths, null, 2, 9, 1, 1, 1, 1, GridBagConstraints.VERTICAL,
				GridBagConstraints.WEST);
		if (!allFieldsFilled()) {
			changePaths.setEnabled(false);
		}

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
			changePaths.setEnabled(true);
		}

		repaint();
		mainframe.redraw();
	}

	private boolean allFieldsFilled() {
		return pathSettingsTSI.getText() != null && !pathSettingsTSI.getText().isEmpty()
				&& rootPathField.getText() != null && !rootPathField.getText().isEmpty()
				&& remixsetPathField.getText() != null && !remixsetPathField.getText().isEmpty()
				&& loopsPathField.getText() != null && !loopsPathField.getText().isEmpty()
				&& recordingsPathField.getText() != null && !recordingsPathField.getText().isEmpty()
				&& itunesPathField.getText() != null && !itunesPathField.getText().isEmpty();
	}

	private void createErrorMessage(ErrorCase error) {
		switch (error) {
		case FILE_UPDATE_NOT_POSSIBLE:
			new JOptionPane("Settings could not be updated.", JOptionPane.ERROR_MESSAGE);
			break;
		case COLLECTION_FILE_NOT_FOUND:
			new JOptionPane(
					"Collection file could not be found at the default location. Please make shure you synced all files right.",
					JOptionPane.ERROR_MESSAGE);
			break;
		case UPDATE_ERROR:
			new JOptionPane("Target settings file could not be updated.", JOptionPane.ERROR_MESSAGE);
		case SETTINGS_FILE_COULD_NOT_BE_OPENED:
			new JOptionPane("Target settings file could not be opened.", JOptionPane.ERROR_MESSAGE);
			break;
		case SETTINGS_NOT_LOADABLE:
			new JOptionPane("The settings could not be opened.", JOptionPane.ERROR_MESSAGE);
			break;
		default:
			break;

		}
	}

}
