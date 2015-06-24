package edu.itba.hci.chopi;

import android.content.Intent;
import android.view.View;

public class DrawerItem {
	private String titulo;
	private int icono;
	
	public DrawerItem (String titulo, int icono){
		this.titulo=titulo;
		this.icono=icono;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public int getIcono() {
		return icono;
	}

	public void setIcono(int icono) {
		this.icono = icono;
	}

	
}
