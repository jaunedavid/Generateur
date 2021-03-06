package ihm.panel.creation;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

import javax.swing.*;

import org.apache.commons.io.IOUtils;

import main.*;
import util.*;

public class PanelCreerProjet extends JPanel implements ActionListener
{	
	// Bouton choix utilisateur
	private JTextField 	txNom;
	private JTextField 	txAuteur;
	private JTextField 	txChemin;
	private JComboBox 	combo;
	
	// bouton choix dossier
	private JButton parcourir;
	
	private JButton valider;
	
	public PanelCreerProjet()
	{
		setLayout(new FlowLayout(FlowLayout.LEFT, 310, 0));
		
		JPanel panelElement = new JPanel();
		panelElement.setLayout(new GridLayout(3,2));
		
		// Element nom
		JLabel label = new JLabel("Nom du projet :");
		txNom = new JTextField(20);
		
		panelElement.add(label);
		panelElement.add(txNom);
		
		// Element auteur
		label = new JLabel("Nom de l'auteur :");
		txAuteur = new JTextField(20);
		
		panelElement.add(label);
		panelElement.add(txAuteur);
		
		// Element Theme
		label = new JLabel("Choisissez un theme");
		combo = new JComboBox();
		combo.setPreferredSize(new Dimension(100, 30));
		combo.addItem("Aucun");
		combo.addItem("Theme 1");
		combo.addItem("Theme 2");
		combo.addItem("Theme 3");
		combo.addActionListener(this);
		
		
		panelElement.add(label);
		panelElement.add(combo);
		
		// Panel Dossier
		JPanel panelDossier = new JPanel();
		
		label = new JLabel("Dossier :");
		txChemin = new JTextField(20);
		txChemin.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				if(me.getButton() == MouseEvent.BUTTON1)
					choisirDossier();
			}
		});;
		txChemin.setEditable(false);
		
		// bouton pour recuperer le dossier
		parcourir = new JButton("Parcourir");
		parcourir.addActionListener(this);
		
		panelDossier.add(label);
		panelDossier.add(txChemin);
		panelDossier.add(parcourir);
		
		// Panel englobant le tout
		JPanel panelCentre = new JPanel();
		panelCentre.setLayout(new GridLayout(2, 1));
		panelCentre.add(panelElement);
		panelCentre.add(panelDossier);
		
		valider = new JButton("Valider");
		valider.addActionListener(this);
		
		add(panelCentre);
		add(valider);
		
		// on ajoute une bordure
		this.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createCompoundBorder(
						BorderFactory.createTitledBorder("Creation d'un nouveau projet"),
						BorderFactory.createEmptyBorder(2, 2, 2, 2)),
						this.getBorder()));
		
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// si l'action vient d'un bouton
		if (e.getSource() instanceof JButton)
		{
			JButton b = (JButton) e.getSource();
			if (b.equals(valider))
			{
				Projet projet = creerProjet();
				if (projet != null)
				{
					Controleur.fenetre.getPanelAjout().supprimerPanel();
					Controleur.creerPanelPropriete(projet);
				}
			}
			else if (b.equals(parcourir))
				choisirDossier();
		}
		// si l'action vient d'un JCombo
		else if (e.getSource() instanceof JComboBox)
		{
			JComboBox cb = (JComboBox) e.getSource();
			String nameCombo = cb.getSelectedItem().toString();
			
			String nomDossier = System.getProperty("user.dir");
			
			if (nameCombo.equals("Theme 1"))
			{
				// on ouvre un navigateur pour un onglet dans un navigateur pour montrer les themes
				try
				{
					Desktop.getDesktop().browse(new URI("file:///" + nomDossier + "/util/Previsualisation_du_theme_1/Page_1.html"));
				}
				catch (IOException e1)			{	e1.printStackTrace();	} 
				catch (URISyntaxException e1) 	{	e1.printStackTrace();	}
			}
			else if (nameCombo.equals("Theme 2"))
			{
				try
				{
					Desktop.getDesktop().browse(new URI("file:///" + nomDossier + "/util/Previsualisation_du_theme_2/Page_1.html"));
				} 
				catch (IOException e1)			{	e1.printStackTrace();	} 
				catch (URISyntaxException e1) 	{	e1.printStackTrace();	}
			}
			else if (nameCombo.equals("Theme 3"))
			{
				try
				{
					Desktop.getDesktop().browse(new URI("file:///" + nomDossier + "/util/Previsualisation_du_theme_3/Page_1.html"));
				} 
				catch (IOException e1)			{	e1.printStackTrace();	} 
				catch (URISyntaxException e1) 	{	e1.printStackTrace();	}
			}
		}
	}

	private void choisirDossier()
	{
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("Users"));
		chooser.changeToParentDirectory();
		// on peut selectionner qu'un dossier
		chooser.setMultiSelectionEnabled(false);
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int fichier = chooser.showOpenDialog(null);

		if (fichier == 0)
			txChemin.setText(chooser.getSelectedFile().getAbsolutePath());
	}

	private Projet creerProjet()
	{
		String nomProjet = txNom.getText();
		String auteur = txAuteur.getText();
		String chemin = txChemin.getText();
		String style = combo.getSelectedItem().toString();
		
		// verification que toutes les informations sont presentes
		if (nomProjet.length() == 0  || chemin.length() == 0 || auteur.length() == 0 || combo.getSelectedItem().equals("Aucun"))
		{
			Controleur.creerOptionPane("warning", "Veuillez saisir toutes les informations");
			return null;
		}
		
		Scanner sc = new Scanner(nomProjet);
		sc.useDelimiter(" ");
		
		// on renomme le fichier
		String nameProjet = sc.next();
		while (sc.hasNext())
			nameProjet += "_" + sc.next();
		
		// on remplace les accents
		nameProjet = nameProjet.replaceAll("[éèêë]", "e");
		nameProjet = nameProjet.replaceAll("[àâä]", "a");
		nameProjet = nameProjet.replaceAll("[ùûü]", "u");
		nameProjet = nameProjet.replaceAll("[ôö]", "o");
		
		// on cree le fichier
		File file = new File(chemin + "/" + nameProjet);
		file.mkdir();
		
		// on cree les dossiers que l'on a besoin
		File content = new File (chemin + "/" + nameProjet + "/content");
		File css = new File (chemin + "/" + nameProjet + "/content/css");
		File img = new File (chemin + "/" + nameProjet + "/content/img");
		
		content.mkdir();
		css.mkdir();
		img.mkdir();

		InputStream input = null;
		OutputStream output = null;
		
		try
		{
			// on selectionne le theme puis on l'ajoute au dossier
			if (combo.getSelectedItem().equals("Theme 1"))
			{
				style = "style1";
				input = new FileInputStream("util/style1.css");
				output = new FileOutputStream(chemin + "/" + nameProjet + "/content/css/style1.css");
			}
			else if (combo.getSelectedItem().equals("Theme 2"))
			{
				style = "style2";
				input = new FileInputStream("util/style2.css");
				output = new FileOutputStream(chemin + "/" + nameProjet + "/content/css/style2.css");
			}
			else if (combo.getSelectedItem().equals("Theme 3"))
			{
				style = "style3";
				input = new FileInputStream("util/style3.css");
				output = new FileOutputStream(chemin + "/" + nameProjet + "/content/css/style3.css");
			}
			IOUtils.copy(input, output);	
		}
		catch (FileNotFoundException e1){	e1.printStackTrace();	}
		catch (IOException e)			{	e.printStackTrace();	}
		
		Projet projet = new Projet(nomProjet, auteur,style, chemin);
		Controleur.metier.ajouterProjet(projet);
		Controleur.metier.setProjetSelectionne(projet);
		Controleur.fenetre.getArborescence().ajoutFils(null, "projet", nomProjet);
		
		return projet;
	}
}