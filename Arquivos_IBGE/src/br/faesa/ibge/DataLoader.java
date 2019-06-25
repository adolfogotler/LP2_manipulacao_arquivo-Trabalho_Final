package br.faesa.ibge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class DataLoader{
	// atributos
	private static String[] header;
	private static Object[][] data;
	private static String path = "../Atividade_T2_dados_IBGE.txt";

	static{
		convertData(SuporteArquivo.leArquivo(path));
		convertHeader(SuporteArquivo.getHeader());
	}
	
	// getters & setters
	public static String[] getHeader() {
		return header;
	}
	private static void setHeader(String[] header) {
		DataLoader.header = header;
	}
	public static Object[][] getData() {
		return data;
	}
	
	private static void setData(Object[][] data) {
		DataLoader.data = data;
	}

	// helper methods
	private static void convertHeader(String header) {
		setHeader(new String[5]);
		StringTokenizer st = new StringTokenizer(header, "*");
		int i = 0;
		while (st.hasMoreTokens()){
			DataLoader.header[i++] = st.nextToken();
		}
	}
	public static void convertData(List<Municipio>municipios){
		data = new String[municipios.size()][5];
		int i = 0;
		for (Municipio municipio: municipios) {
			data[i]    = new String[5];
			data[i][0] = municipio.getSiglaUF();
			data[i][1] = municipio.getCodUF();
			data[i][2] = municipio.getCodMun();
			data[i][3] = municipio.getMunicipio();
			data[i][4] = municipio.getPopulacao(); 
			i++;
		}
	}
	
	private static void calculaRepeticoesMunicipio(List<Municipio>municipios){
		Set<Municipio> municipios_lista = new HashSet<Municipio>(municipios); 
		String dados = "";
        for (Municipio municipio : municipios_lista) {
           dados += Collections.frequency(municipios_lista, municipio); 
		}
	}
	
	private static void calculoMediaDesvioHabitantesMunicipios(List<Municipio>municipios){
		Set<Municipio> municipios_lista = new HashSet<Municipio>(municipios); 
		String dados = "";
        for (Municipio municipio : municipios_lista) {
           dados += Collections.frequency(municipios_lista, municipio); 
		}
	}
	
	private static void calculoMediaDesvioHabitantesRegiao(List<Municipio>municipios){
		Set<Municipio> municipios_lista = new HashSet<Municipio>(municipios); 
		String dados = "";
        for (Municipio municipio : municipios_lista) {
           dados += Collections.frequency(municipios_lista, municipio); 
		}
	}
	
	public static void ordenarDados () {
    	List<Municipio> municipios = SuporteArquivo.leArquivo(path);
    	Collections.sort(municipios, new MunicipioComparator());
    	convertData(municipios);
    }
	
	public static void ordenarPorRegiao () {
		String estados[] = {"ES", "RJ", "SP", "MG"};
    	List<Municipio> municipios = SuporteArquivo.leArquivo(path);
    	List<Municipio> municipiosSudeste = new ArrayList<Municipio>();
    	for (Municipio municipio : municipios) {
			if ((municipio.getSiglaUF().equals(estados[0]) || municipio.getSiglaUF().equals(estados[1]) || municipio.getSiglaUF().equals(estados[2]) || municipio.getSiglaUF().equals(estados[3])) && (municipio.getMunicipio().charAt(0) == 'D' || municipio.getMunicipio().charAt(0) == 'M')) {
				municipiosSudeste.add(municipio);
			}
		}
    	convertData(municipiosSudeste);
    }
	
	public static void regioesSudeste () {
		String estados[] = {"ES", "RJ", "SP", "MG"};
    	List<Municipio> municipios = SuporteArquivo.leArquivo(path);
    	int contMunicipio = 0;
    	for (Municipio municipio : municipios) {
			if (municipio.getSiglaUF().equals(estados[0]) || municipio.getSiglaUF().equals(estados[1]) || municipio.getSiglaUF().equals(estados[2]) || municipio.getSiglaUF().equals(estados[3])) {
				contMunicipio++;
			}
		}
    	System.out.println("Quantidade de municípios da região sudeste: " + contMunicipio);
    }
	
	
	
}
