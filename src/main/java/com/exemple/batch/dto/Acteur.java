package com.exemple.batch.dto;

public class Acteur {
	
	private String id;
	private String nomComplet;
	private int age;
	private String film;
	
	
	public Acteur() {}
	
	public Acteur(String id, String nomComplet, int age, String film) {
		super();
		this.id = id;
		this.nomComplet = nomComplet;
		this.age = age;
		this.film = film;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNomComplet() {
		return nomComplet;
	}
	public void setNomComplet(String nomComplet) {
		this.nomComplet = nomComplet;
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
		return "Acteur [id=" + id + ", nomComplet=" + nomComplet + ", age=" + age + ", film=" + film + "]";
	}
}
