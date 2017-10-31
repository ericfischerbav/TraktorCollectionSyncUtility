package dance.danieldavisericvanthorn.traktorcollectionsyncutility.ui.filechooser;

import java.awt.Dimension;
import java.io.File;

import javax.swing.JFileChooser;

import dance.danieldavisericvanthorn.traktorcollectionsyncutility.enums.TraktorFileType;
import dance.danieldavisericvanthorn.traktorcollectionsyncutility.settings.InternalSettingsManager;

public class TraktorFileChooserFrame extends JFileChooser {

	/**
	 *
	 */
	private static final long serialVersionUID = -1633178812024938448L;

	public TraktorFileChooserFrame(TraktorFileType type, String directory) {
		setDialogTitle("Choose " + type.getFileFilter().getDescription());
		setMultiSelectionEnabled(false);
		setDialogType(JFileChooser.OPEN_DIALOG);
		setCurrentDirectory(new File(directory));
		Dimension d = getToolkit().getScreenSize();
		setLocation(((d.width - getSize().width) / 2), ((d.height - getSize().height) / 2));
		setCurrentDirectory(new File(InternalSettingsManager.getTraktorPath(type)));
		setFileFilter(type.getFileFilter());
		setFileSelectionMode(type.getFileFilter().getFileSelectionMode());
		setAcceptAllFileFilterUsed(false);
	}

}
