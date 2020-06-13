package principal;

import java.io.Serializable;

class Objeto implements Serializable {
	private String _dato;
	private boolean llamado;

	public Objeto(String dato) {
		this._dato = dato;
	}

	public Objeto(boolean llamada) {
		this.llamado = llamada;
	}

	public String toString() {
		return this._dato;
	}
}