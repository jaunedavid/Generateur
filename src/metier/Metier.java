package metier;

import java.io.*;
import java.util.*;

import util.*;

public class Metier
{
	private Generator generator;
	private ArrayList<Projet> alProjet;
	private Projet projetSelectionne;
	
	public Metier()
	{
		alProjet = init();
		generator = new Generator();		
	}

	private ArrayList<Projet> init()
	{
		try {
			FileInputStream fichier = new FileInputStream("temp/metier.dat");
			ObjectInputStream ois = new ObjectInputStream(fichier);
			alProjet = (ArrayList<Projet>) ois.readObject();
		}
		catch (IOException ignored) {}            // probleme de lecture
		catch (ClassNotFoundException e)	{}
		
		return (alProjet != null) ? alProjet : new ArrayList<Projet>();
	}

	public Generator getGenerator()			{	return generator;			}
	public Projet getProjetSelectionne()	{	return projetSelectionne;	}
	public ArrayList<Projet> getAlProjet()	{	return alProjet;			}
	
	public void ajouterProjet(Projet p)
	{
		alProjet.add(p);
	}

	public Projet getProjet(String nom)
	{
		for (Projet p : alProjet)
			if (nom.equals(p.getNom()))
				return p;
		
		return null;
	}
	
	public int getIndiceProjet(Projet projet)
	{
		int i = 0;
		for (Projet p : alProjet)
		{
			if (p.equals(projet))
				return i;
			i++;
		}
		return -1;
	}
	
	public void setProjetSelectionne(Projet p)
	{
		projetSelectionne = p;
	}
	
	public void enregistrerContenu()
	{
		try {
			FileOutputStream fichier = new FileOutputStream("temp/metier.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fichier);
			oos.writeObject(alProjet);
			oos.flush();
			oos.close();
		}
		catch (java.io.IOException e) 
		{
			e.printStackTrace();
		}
	}
}