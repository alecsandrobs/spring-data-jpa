package br.com.alecsandro.contas.javaClient;

import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LancamentosJavaClientTest {

    public static void main(String[] args) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL("http://localhost:8080/lancamentos");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.addRequestProperty("Authorization", String.format("Basic %s", encodeUserPass("teste", "1234567")));
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            System.out.println(sb.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            IOUtils.closeQuietly(reader);
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private static String encodeUserPass(String user, String pass) {
        return new String(Base64.encodeBase64(String.format("%s:%s", user, pass).getBytes()));
    }
}
