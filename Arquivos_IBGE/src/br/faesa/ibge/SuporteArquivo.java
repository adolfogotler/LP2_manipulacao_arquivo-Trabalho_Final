package br.faesa.ibge;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map; 
import java.util.Map.Entry; 

public class SuporteArquivo {
	private static String header = "Sigla*C�d. UF.*Cod. Mun.*Munic�pio*Popula��o";

	public static List<Municipio> leArquivo(String arq){
		// define nome do arquivo
		arq = "../Atividade_T2_dados_IBGE.txt";
		// criar um objeto "arquivo"
		File f = new File(arq);
		// criar um objeto de varredura do arquivo
		Scanner sc = null;
		// criar uma lista para os munic�pios
		List<Municipio>municipios = new ArrayList<Municipio>();
		// teste arquivo
		System.out.println("Existe? "+f.exists()+"  \n"+f.getAbsolutePath());
		// l� arquivo
		try {
			sc = new Scanner(f);
			// enquanto houver linhas ...
			while (sc.hasNextLine()){
				String linha = sc.nextLine();
//				System.out.println("===>> "+linha);
				// dividir a linha pelo separador *
				StringTokenizer st = new StringTokenizer(linha, "*");
				// para cada campo do registro
				while (st.hasMoreElements()) {
					// extrair o municipio
					Municipio m = new Municipio(st.nextToken(), 
							st.nextToken(), st.nextToken(), 
							st.nextToken(), st.nextToken());
					// adicionar o municipio � lista
					municipios.add(m);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}		
		return municipios;
		
	}
	
	public static void imprimeArq(List<Municipio>municipios){
		System.out.printf("\n%-10s%-10s%10s%50s%10s","Sigla", "C�d. UF.", 
				                   "Cod. Mun.", "Munic�pio", "Popula��o");
		for (Municipio municipio : municipios) {
			System.out.printf("\n%-10s%-10s%10s%50s%10s",
					municipio.getSiglaUF(),
					municipio.getCodUF(),
					municipio.getCodMun(),
					municipio.getMunicipio(),
					municipio.getPopulacao());
		}
	}
	
	public static String getHeader() {
		return header;
	}
		
}

