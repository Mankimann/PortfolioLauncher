package de.manuelanker.app.showcase;

import java.awt.Image;

public class Project {
	
	private  int id;
	private  Image img;
	private  String name;
	private  String description;
	private  String tech;
	private  String gitLink;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Image getImg() {
		return img;
	}
	public void setImg(Image img) {
		this.img = img;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getGitLink() {
		return gitLink;
	}
	public void setGitLink(String gitLink) {
		this.gitLink = gitLink;
	}
	public String getTech() {
		return tech;
	}
	public void setTech(String tech) {
		this.tech = tech;
	}

}
