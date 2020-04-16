package br.com.alecsandro.contas.importarJson;

import br.com.alecsandro.contas.javaClient.FornecedoresDAO;
import br.com.alecsandro.contas.model.Fornecedor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CadastraFornecedoresArquivoJson {

    public static void main(String[] args) throws IOException {

        JSONArray lista = new JSONArray(getFileContent());
        for (Object item : lista) {
            JSONObject object = new JSONObject(item.toString());
            System.out.println(String.format("Cadastrando o fornecedor: %s", object.get("nome")));

            new FornecedoresDAO().save(new Fornecedor(object.getString("nome")));

        }
    }

    private static String getFileContent() throws IOException {
        List<String> linhas = Files.readAllLines(Paths.get("files/fornecedores.json"), Charset.forName("UTF-8"));
        String texto = null;
        for (String linha : linhas) {
            if (texto != null) {
                texto += linha;
            } else {
                texto = linha;
            }
        }
        return texto;
    }
}
