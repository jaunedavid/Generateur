package Utilitaire;

import java.io.*;
import java.util.*;

import Main.Controleur;

public class Page implements Serializable
{
	private String nom;
	// ArrayList qui permet d'avoir les titres/paragraphes dans l'ordre
	private ArrayList<String> alOrdre;
	// ArrayList ou il y a les titres
	private ArrayList<String> alTitre;
	// ArrayList ou il y a les paragraphes
	private ArrayList<String> alParagraphe;
	// ArrayList ou il y a les "images"
	private ArrayList<String> alImage;
	
	public Page(String nom)
	{
		this.nom = nom;
		alOrdre = new ArrayList<String>();
		alTitre = new ArrayList<String>();
		alParagraphe = new ArrayList<String>();
		alImage = new ArrayList<String>();
	}
	
	/*
	 * Accesseurs
	 */
	public String getNom()						{	return nom;				}
	public ArrayList<String> getAlOrdre()		{	return alOrdre;			}
	public ArrayList<String> getAlTitre()		{	return alTitre;			}
	public ArrayList<String> getAlParagraphe()	{	return alParagraphe;	}
	public ArrayList<String> getAlImage()		{	return alImage;			}

	
	/*
	 * Ajout aux listes des elements
	 */
	
	public void ajouterOrdre(String texte)
	{
		alOrdre.add(texte);
	}
	
	public void ajouterTitre(String titre)
	{
		alTitre.add(titre);
	}
	
	public void ajouterImage(String s)
	{
	    alImage.add(s);
	}
	
	public void ajouterParagraphe(String s)
	{
	    alParagraphe.add(s);
	}
	
	
	/*
	 * Modification des elements
	 */
	public void modTitre(String titre, int indice) 
	{
		alTitre.remove(indice-1);
		alTitre.add(indice-1, titre);
	}
	
	public void modParagraphe(String s, int indice)
	{
	    alParagraphe.remove(indice-1);
		alParagraphe.add(indice-1, s);
	}
	
	public void modImage(String s, int indice)
	{
	    alImage.remove(indice-1);
		alImage.add(indice-1, s);
	}
	
	
	/*
	 * Modification de l'ordre des elements
	 */
	
	public void modOrdreElement(String type1, String niveau)
	{
		String type2 = "";
		// on parcours la liste pour savoir l'indice de l'element que l'on recherche
		int i = 0;
		for (String s : alOrdre)
		{
			if (s.equals(type1))
				break;
			i++;
		}
		if (niveau.equals("monter"))
			type2 = alOrdre.get(i-1);
		if (niveau.equals("diminuer"))
			type2 = alOrdre.get(i+1);
		
		// on verifie qu'on a le meme nom
		if(Controleur.verificationDesNom(type1, type2))
		{
			// on recupere le type et l'indice de l'element
			Scanner sc = new Scanner(type1);
			sc.useDelimiter(" ");
			String type = sc.next();
			
			String indice = sc.next();
			int ind = Integer.parseInt(indice)-1;
			
			/*
			 * On modifie les listes de titres/paragraphes et images
			 */
			if (type.equals("Titre"))
			{
				if (niveau.equals("monter"))
					modIndexTitre(ind-1, ind);	
				if (niveau.equals("diminuer"))
					modIndexTitre(ind+1, ind);	
			}
			if (type.equals("Paragraphe"))
			{
				if (niveau.equals("monter"))
					modIndexParagraphe(ind-1, ind);
				if (niveau.equals("diminuer"))
					modIndexParagraphe(ind+1, ind);
			}
			if (type.equals("Image"))
			{
				if (niveau.equals("monter"))
					modIndexImage(ind-1, ind);
				if (niveau.equals("diminuer"))
					modIndexImage(ind+1, ind);
			}
		}
		/*
		 * On modifie l'ordre des elements dans la liste
		 */
		alOrdre.remove(i);
		if (niveau.equals("monter"))
			alOrdre.add(i-1, type1);
		if (niveau.equals("diminuer"))
			alOrdre.add(i+1, type1);		
	}
	
	// On enleve le titre de l'ancienne position pour la remettre a la nouvelle
	public void modIndexTitre(int indexNouvelle, int indexAncienne)
	{
		String titre = alTitre.get(indexAncienne);
		alTitre.remove(indexAncienne);
		alTitre.add(indexNouvelle, titre);
	}
	
	// On enleve le paragraphe de l'ancienne position pour la remettre a la nouvelle
	public void modIndexParagraphe(int indexNouvelle, int indexAncienne)
	{
		String paragraphe = alParagraphe.get(indexAncienne);
		alParagraphe.remove(indexAncienne);
		alParagraphe.add(indexNouvelle, paragraphe);
	}
	
	// On enleve l'image de l'ancienne position pour la remettre a la nouvelle
	public void modIndexImage(int indexNouvelle, int indexAncienne)
	{
		String image = alImage.get(indexAncienne);
		alImage.remove(indexAncienne);
		alImage.add(indexNouvelle, image);
	}
	
	@Override
	public String toString()
	{
		return "Page [nom=" + nom + ", alTitre=" + alTitre + ", alParagraphe="
				+ alParagraphe + ", alImage=" + alImage + "]";
	}
}