package br.faesa.ibge;

import java.text.Collator;
import java.util.Comparator;

public class MunicipioComparator implements Comparator<Municipio>{
	
	@Override
	public int compare(Municipio a0, Municipio a1) {
		Collator c = Collator.getInstance();
		return c.compare(a0.getMunicipio(), a1.getMunicipio());

	}
}
