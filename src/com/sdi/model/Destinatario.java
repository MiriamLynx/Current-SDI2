package com.sdi.model;

public class Destinatario {

	private int id;
	private int id_contacto;
	private int id_correo;
	
	public void setId(int id){
		this.id = id;
	}
	
	public int getId(){
		return this.id;
	}
	
	public void setId_Contacto(int id){
		this.id_contacto = id;
	}
	
	public int getId_Contacto(){
		return this.id_contacto;
	}
	
	public void setId_Correo(int id){
		this.id_correo = id;
	}
	
	public int getId_Correo(){
		return this.id_correo;
	}
}
