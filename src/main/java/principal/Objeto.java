package principal;

import java.io.Serializable;

class Objeto implements Serializable {
	private String _dato;

	public Objeto(String dato) {
		this._dato = dato;
	}

	public String toString() {
		return this._dato;
	}
}