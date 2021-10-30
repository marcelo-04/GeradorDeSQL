import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;

/* objetivo aqui é converter o CSV para instruções SQL*/
public class Gerador {

	public static void main(String[] args) {
		HashMap<String, Integer> mapaDeEstados = new HashMap<String, Integer>();

		try {
			FileWriter dst = new FileWriter(new File("insert_estados.sql"));
			FileReader src = new FileReader(new File("estados.csv"));
			BufferedReader br = new BufferedReader(src);
			PrintWriter pr = new PrintWriter(dst);

			String linha;
			String templateSQL = "INSERT INTO tb_estado VALUES ( %CODIGO%, '%NOME%', '%SIGLA%');";
			while ((linha = br.readLine()) != null) {
				// Teste das linhas
				// System.out.println("lido = "+ linha);
				String campos[] = linha.split(";");
				// para teste
				// System.out.println(campos[0]+ " - "+ campos[1]);
				String novoSQL = templateSQL.replace("%CODIGO%", campos[0]).replace("%NOME%", campos[1])
						.replace("%SIGLA%", campos[2]);
				// para teste
				// System.out.println(novoSQL);
				mapaDeEstados.put(campos[2], Integer.parseInt(campos[0]));
				pr.println(novoSQL);
			}
			br.close();
			pr.close();
			dst.close();
			src.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		// Os comando abaixo gera SQL das cidades
		try {
			FileWriter dst = new FileWriter(new File("insert_cidades.sql"));
			FileReader src = new FileReader(new File("cidades.csv"));
			BufferedReader br = new BufferedReader(src);
			PrintWriter pr = new PrintWriter(dst);

			String linha;
			String templateSQL = "INSERT INTO tb_cidade VALUES ( null, '%NOME%', %ESTADO%);";
			while ((linha = br.readLine()) != null) {
				String campos[] = linha.split(";");
				Integer codEstado = mapaDeEstados.get(campos[3]);
				
				String novoSQL = templateSQL.replace("%NOME%", campos[4]).replace("%ESTADO%", codEstado.toString());
				pr.println(novoSQL);
			}
			br.close();
			pr.close();
			dst.close();
			src.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
