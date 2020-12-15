package com.exemple.batch.entities;

public class ActeurEntity {
	
	private String id;
	private String nom;
	private String prenom;
	private int age;
	private String film;
	
	public ActeurEntity() {}
	
	public ActeurEntity(String id, String nom, String prenom, int age, String film) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.age = age;
		this.film = film;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getFilm() {
		return film;
	}

	public void setFilm(String film) {
		this.film = film;
	}

	@Override
	public String toString() {
		return "ActeurEntity [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", age=" + age + ", film="
				+ film + "]";
	}
}
