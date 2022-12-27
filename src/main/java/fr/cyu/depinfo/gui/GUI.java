package fr.cyu.depinfo.gui;

import fr.cyu.depinfo.xmlprocessor.*;
import fr.cyu.depinfo.filemanager.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * This is the main class, used for the Graphical User Interface.
 *
 * @author Farah ALIANE
 */
public class GUI extends JFrame {
    private JLabel thumbImage;
    private ImageIcon thumb;
    private JPanel jpGauche, miniature, jpMain;
    File x;
    ParsedFile parsedMeta;
    MetadataExtractor meta;
    private JTextField xtitle = new JTextField(20);
    private JTextField xsubject = new JTextField(20);
    private JTextField xdate = new JTextField(20);
    private JTextField xkeywords = new JTextField(20);
    private JTextField xdescription = new JTextField(20);
    private JTextField xcreator = new JTextField(20);
    private JTextField xNbTables = new JTextField(20);
    private JTextField xNbImages = new JTextField(20);
    private JTextField xNbPages = new JTextField(20);
    private JTextField xNbParagraphs = new JTextField(20);
    private JTextField xNbWords = new JTextField(20);
    private JTextField xNbCharacters = new JTextField(20);
    private JTextField xNbNonWhitespaceCharacters = new JTextField(20);
    private JTextField xHyperlinks = new JTextField(20);
    private JButton chooseFile = new JButton("ajouter un fichier");
    private JButton extract = new JButton("extraire");
    private JButton clear = new JButton("effacer");
    private JButton save = new JButton("enregistrer");
    private ArrayList<File> tabFileOdt;
    private DefaultListModel<String> dlm;
    private File currentODT;

    public GUI() {
        this("odt-metadata-editor");
    }

    public GUI(String titre) {
        super(titre);

        try {

            final JLabel title = new JLabel("Titre :");
            final JLabel subject = new JLabel("Sujet :");
            final JLabel date = new JLabel("Date de creation :");
            final JLabel creator = new JLabel("Auteur :");
            final JLabel keywords = new JLabel("Mots-clés :");
            final JLabel description = new JLabel("Description :");
            final JLabel nbTables = new JLabel("Number of tables :");
            final JLabel nbImages = new JLabel("Nombre d'images :");
            final JLabel nbPages = new JLabel("Numbre de pages :");
            final JLabel nbParagraphs = new JLabel("Nombre de paragraphes :");
            final JLabel nbWords = new JLabel("Nombre de mots :");
            final JLabel nbCharacters = new JLabel("Nombre de caractères :");
            final JLabel nbNonWhitespaceCharacters = new JLabel("Nombre de caractères non blanc :");
            final JLabel hyperlinks = new JLabel("Liens hypertexte :");

            chooseFile.addActionListener(new ActionChoose());
            extract.addActionListener(new ActionExtract());
            clear.addActionListener(new ActionClear());
            save.addActionListener(new ActionSave());

            tabFileOdt = new ArrayList<>();
            FileManager.getODTInDir(tabFileOdt, ".", true);
            dlm = new DefaultListModel<>();
            File outDir = new File("./out");

            for (int i = 0; i < tabFileOdt.size(); i++) {
                dlm.addElement(tabFileOdt.get(i).getName());
            }

            Container contentPane = getContentPane();
            jpMain = new JPanel();
            jpMain.setLayout(new FlowLayout(FlowLayout.LEFT));
            jpMain.setBackground(Color.BLACK);
            jpGauche = new JPanel();
            JPanel jpDroite = new JPanel();
            JPanel listeFichier = new JPanel();
            listeFichier.setBackground(Color.BLACK);
            miniature = new JPanel();
            thumb = new ImageIcon("./out/Thumbnails/thumbnail.png");
            JList<String> listeODT = new JList<>(dlm);
            JScrollPane scrollFichier= new JScrollPane(listeODT);
            thumbImage= new JLabel(thumb);
            miniature.add(thumbImage);
            thumbImage.setVisible(true);
            jpGauche.setLayout(new BoxLayout(jpGauche, BoxLayout.Y_AXIS));
            scrollFichier.setPreferredSize(new Dimension(400,400));
            miniature.setPreferredSize(new Dimension(400,300));
            jpGauche.add(scrollFichier);
            jpGauche.add(miniature);
            jpMain.add(jpGauche);

            listeODT.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if (e.getValueIsAdjusting()) return;
                    JList source = (JList) e.getSource();
                    String selected = source.getSelectedValue().toString();
                    for (File f : tabFileOdt) {
                        if (f.getName().equalsIgnoreCase(selected)) {
                            try {
                                ZipManager.unzip(f, outDir);
                                currentODT = f;
                                x = new File("./out/meta.xml");
                                parsedMeta = new ParsedFile(x);
                                meta = new MetadataExtractor(parsedMeta);
                                xtitle.setText(meta.getTitle());
                                xsubject.setText(meta.getSubject());
                                xdate.setText(meta.getCreationDate());
                                xcreator.setText(meta.getAuthor());
                                xkeywords.setText(meta.getKeywords());
                                xdescription.setText(meta.getDescription());
                                xNbTables.setText(meta.getNbTables());
                                xNbImages.setText(meta.getNbImages());
                                xNbPages.setText(meta.getNbPages());
                                xNbParagraphs.setText(meta.getNbParagraphs());
                                xNbWords.setText(meta.getNbWords());
                                xNbCharacters.setText(meta.getNbCharacters());
                                xNbNonWhitespaceCharacters.setText(meta.getNbNonWhitespaceCharacters());
                                xHyperlinks.setText(meta.getHyperlinks());
                                thumbImage.setIcon(new ImageIcon(new ImageIcon(x.getParent() + File.separator + "Thumbnails" + File.separator+"thumbnail.png").getImage().getScaledInstance(210, 297, Image.SCALE_DEFAULT)));
                                thumbImage.updateUI();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }
            });

            jpDroite.setLayout(new BoxLayout(jpDroite, BoxLayout.Y_AXIS));
            jpDroite.setPreferredSize(new Dimension(600,700));
            JPanel jp1 = new JPanel();
            JPanel jp2 = new JPanel();
            JPanel jp3 = new JPanel();
            JPanel jp4 = new JPanel();
            JPanel jp5 = new JPanel();
            JPanel jp6 = new JPanel();
            JPanel jp7 = new JPanel();
            JPanel jp8 = new JPanel();
            JPanel jp9 = new JPanel();
            JPanel jp10 = new JPanel();
            JPanel jp11 = new JPanel();
            JPanel jp12 = new JPanel();
            JPanel jp13 = new JPanel();
            JPanel jp14 = new JPanel();
            JPanel line = new JPanel();
            contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
            line.add(chooseFile);
            line.add(Box.createHorizontalStrut(400));
            line.add(extract);
            line.add(clear);
            line.add(save);
            contentPane.add(line);
            jp1.add(title);
            jp1.add(xtitle);
            jp2.add(date);
            jp2.add(xdate);
            jp3.add(subject);
            jp3.add(xsubject);
            jp4.add(creator);
            jp4.add(xcreator);
            jp5.add(description);
            jp5.add(xdescription);
            jp6.add(keywords);
            jp6.add(xkeywords);
            jp7.add(nbTables);
            jp7.add(xNbTables);
            jp8.add(nbImages);
            jp8.add(xNbImages);
            jp9.add(nbPages);
            jp9.add(xNbPages);
            jp10.add(nbParagraphs);
            jp10.add(xNbParagraphs);
            jp11.add(nbWords);
            jp11.add(xNbWords);
            jp12.add(nbCharacters);
            jp12.add(xNbCharacters);
            jp13.add(nbNonWhitespaceCharacters);
            jp13.add(xNbNonWhitespaceCharacters);
            jp14.add(hyperlinks);
            jp14.add(xHyperlinks);

            jpDroite.add(jp1);
            jpDroite.add(jp2);
            jpDroite.add(jp3);
            jpDroite.add(jp4);
            jpDroite.add(jp5);
            jpDroite.add(jp6);
            jpDroite.add(jp7);
            jpDroite.add(jp8);
            jpDroite.add(jp9);
            jpDroite.add(jp10);
            jpDroite.add(jp11);
            jpDroite.add(jp12);
            jpDroite.add(jp13);
            jpDroite.add(jp14);
            jpMain.add(jpDroite);
            contentPane.add(jpMain);

            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            pack();
            setLayout(null);
            setResizable(true);
            setVisible(true);
            addWindowListener(new CloseAdapter());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private class ActionClear implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            xtitle.setText("");
            xdate.setText("");
            xsubject.setText("");
            xcreator.setText("");
            xdescription.setText("");
            xkeywords.setText("");
            xNbTables.setText("");
            xNbImages.setText("");
            xNbPages.setText("");
            xNbParagraphs.setText("");
            xNbWords.setText("");
            xNbCharacters.setText("");
            xNbNonWhitespaceCharacters.setText("");
            xHyperlinks.setText("");
        }
    }

    private class ActionChoose implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            ChooseODT c = new ChooseODT();
            int state = c.showOpenDialog(GUI.this);
            if (state == JFileChooser.APPROVE_OPTION) {
                tabFileOdt.add(c.getSelectedFile());
                dlm.addElement(c.getSelectedFile().getName());
            }
        }
    }

    private class ActionSave implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                meta.setTitle(xtitle.getText());
                meta.setAuthor(xcreator.getText());
                meta.setDescription(xdescription.getText());
                meta.setKeywords(xkeywords.getText());
                meta.setSubject(xsubject.getText());
                parsedMeta.save(x);
                ZipManager.zipDir(new File("./out"), currentODT);
                File outDir = new File("./out");
                FileManager.deleteDir(outDir, outDir.listFiles());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    private class ActionExtract implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            xtitle.setText(meta.getTitle());
            xdate.setText(meta.getCreationDate());
            xsubject.setText(meta.getSubject());
            xcreator.setText(meta.getAuthor());
            xdescription.setText(meta.getDescription());
            xkeywords.setText(meta.getKeywords());
            xNbTables.setText(meta.getNbTables());
            xNbImages.setText(meta.getNbImages());
            xNbPages.setText(meta.getNbPages());
            xNbParagraphs.setText(meta.getNbParagraphs());
            xNbWords.setText(meta.getNbWords());
            xNbCharacters.setText(meta.getNbCharacters());
            xNbNonWhitespaceCharacters.setText(meta.getNbNonWhitespaceCharacters());
            xHyperlinks.setText(meta.getHyperlinks());
        }
    }

    private class CloseAdapter extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            super.windowClosing(e);
            File outDir = new File("./out");
            if (outDir.exists()) {
                FileManager.deleteDir(outDir, outDir.listFiles());
            }
        }
    }
    public static void main(String[] args) {
        new GUI();
    }
}