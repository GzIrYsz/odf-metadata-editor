package fr.cyu.depinfo.gui;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

/**
 * This class represents the main JFileChooser of the GUI.
 *
 * @author Farah ALIANE
 */
public class ChooseODT extends JFileChooser {
    private FileNameExtensionFilter odtFilter;

    public ChooseODT() {
        new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        setDialogTitle("Ajouter un fichier");
        setAcceptAllFileFilterUsed(false);
        odtFilter = new FileNameExtensionFilter("Fichiers OpenDocument Text", "odt");
        addChoosableFileFilter(odtFilter);
    }
}