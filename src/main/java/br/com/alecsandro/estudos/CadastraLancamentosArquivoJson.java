package br.com.alecsandro.estudos;

import br.com.alecsandro.estudos.javaClient.FornecedoresDAO;
import br.com.alecsandro.estudos.javaClient.LancamentosDAO;
import br.com.alecsandro.estudos.model.Fornecedor;
import br.com.alecsandro.estudos.model.Lancamento;
import br.com.alecsandro.estudos.util.DateUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

public class CadastraLancamentosArquivoJson {

    public static void main(String[] args) throws IOException {

        int i = 0;
        JSONArray lista = new JSONArray(getFileContent());
        for (Object item : lista) {
            i++;
            JSONObject object = new JSONObject(item.toString());

            String data = object.getString("data");
            String fornecedorNome = object.getString("fornecedor");
            Double valor = object.getDouble("valor");
//            String pagamento = object.getString("pagamento");
            String observacoes = object.getString("observacoes");

            System.out.println();
            System.out.println(String.format("%d. Lançamento", i));
            System.out.println(data);
            System.out.println(fornecedorNome);
            System.out.println(valor);
//            System.out.println(pagamento);
            System.out.println(observacoes);
            System.out.println();

            FornecedoresDAO fornecedoresDAO = new FornecedoresDAO();

//            aguardar();

            List<Fornecedor> fornecedores = fornecedoresDAO.findByNome(fornecedorNome);

            aguardar();

            Fornecedor fornecedorSelecionado = null;
            for (Fornecedor fornecedor : fornecedores) {
                if (fornecedorNome.equalsIgnoreCase(fornecedor.getNome())) {
                    fornecedorSelecionado = fornecedor;
                }
            }

            if (fornecedorSelecionado == null) {
                System.out.println(String.format("[Não encontrou o fornecedor %s]", fornecedorNome));
                throw new RuntimeException(String.format("[Não encontrou o fornecedor %s]", fornecedorNome));
            }

            new LancamentosDAO().save(new Lancamento(DateUtil.localDate(data), fornecedorSelecionado, object.getDouble("valor"), object.isNull("pagamento") ? null : DateUtil.localDate(object.getString("pagamento")), object.getString("observacoes")));
        }
    }

    private static void aguardar() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static String getFileContent() throws IOException {
        List<String> linhas = Files.readAllLines(Paths.get("files/lancamentos.json"), Charset.forName("UTF-8"));
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
