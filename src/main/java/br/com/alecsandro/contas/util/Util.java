package br.com.alecsandro.contas.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.UnaryOperator;

public class Util {

    private static final File file = new File("files/nomes.txt");
    public static final DecimalFormat numerico = new DecimalFormat("#,##0.00");
    public static final DecimalFormat real = new DecimalFormat("R$ #,##0.00");

    public static final UnaryOperator<String> maiuscula = texto -> texto.toUpperCase();
    public static final UnaryOperator<String> minuscula = texto -> texto.toLowerCase();
    public static final UnaryOperator<String> pipe = texto -> texto.replace(" ", "|");
    public static final UnaryOperator<String> semicolon = texto -> texto.replace(" ", ";").replace("|", ";");

    public static final String semicolon(String texto) {
        return texto.replace(" ", ";").replace("|", ";");
    }

    public static final String emReal(Double valor) {
        return real.format(valor);
    }

    public static final String emValor(Double valor) {
        return numerico.format(valor);
    }

    private static Scanner getScanner() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return scanner;
    }

    public static List<String> getNomesList() {
        List<String> nomes = new ArrayList<>();
        Scanner scanner = getScanner();
        while (scanner.hasNextLine()) {
            nomes.add(scanner.nextLine());
        }
        return nomes;
    }

    public static String getNomesString(String separator) {
        String lista = null;
        Scanner scanner = getScanner();
        while (scanner.hasNextLine()) {
            if (lista != null) {
                lista += separator + scanner.nextLine();
            } else {
                lista = scanner.nextLine();
            }
        }
        return lista;
    }

    public static String completaDireita(String texto) {
        int tamanho = 15;
        int resto = 15 - texto.length();
        String completar = null;
        for (int i = 0; i < resto; i++) {
            if (i != 0) {
                completar += " ";
            } else {
                completar = " ";
            }
        }
        return completar + texto;
    }

    public static String completaEsquerda(String texto, int tamanho) {
        int tamanhoTexto = texto.length();
        if (tamanhoTexto > tamanho) {
            return texto.substring(0, tamanho) + " ";
        }
        while (texto.length() <= tamanho) {
            texto += " ";
        }
        return texto;
    }
}
