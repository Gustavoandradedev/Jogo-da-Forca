package br.com.zup.jogoDaForcaRefatorado;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

@SuppressWarnings("unused")
public class JogoDaForca {

	public static final String MENU = ("==================================================================\n")
			+ ("                **SEJA BEM VINDO AO JOGO DA FORCA**\n")
			+ ("==================================================================\n") + ("REGRAS DO JOGO:\n")
			+ ("O objetivo deste jogo é descobrir uma palavra adivinhando as letras que ela possui. "
					+ "\nA cada acerto, é escrito a letra no espaço correspondente. "
					+ "\nCaso o jogador erre, ele perde uma vida. \n" + "\nLembrando: Você tem apenas 6 vidas!"
					+ ("\n==================================================================\n"));

	public static String[] palavrasArquivo() throws IOException {
		FileReader estrutura = new FileReader("palavras.txt");
		BufferedReader leitor = new BufferedReader(estrutura);
		String linha;

		String[] palavras = new String[10];
		int contador = 0;

		while ((linha = leitor.readLine()) != null) {
			palavras[contador] = linha;
			contador++;
		}
		leitor.close();
		estrutura.close();
		return palavras;
	}

	public static String sorteador() throws IOException {

		String[] palavras = palavrasArquivo();
		Random random = new Random();
		int quantidadesPalavras = palavras.length;
		int indiceSorteio = random.nextInt(quantidadesPalavras);
		String sorteada = palavras[indiceSorteio];

		return sorteada;
	}

	public static String adicionaPalavra(Scanner teclado) throws IOException {

		
		FileWriter writer = new FileWriter("palavras.txt", true);
		System.out.println("Parabéns!!! Você ganhou o direito de adicionar uma palavra ao jogo.");
		String palavraAdicionada = teclado.nextLine().toLowerCase();
		writer.append("\n");
		writer.append(String.format("%s", palavraAdicionada));
		writer.close();
		return null;
	}
	
	public static void main(String[] args) throws IOException {

		Scanner teclado = new Scanner(System.in);

		System.out.println(MENU);

		char letra;
		String letrasUtilizadas = "";
		int vidas = 6;
		String sorteada = sorteador();
		char[] acertos = new char[sorteada.length()];
		for (int i = 0; i < acertos.length; i++) {
			acertos[i] = 0;
			if (sorteada.charAt(i) == ' ')
				acertos[i] = 1;
		}

		boolean ganhou;
		do {

			System.out.println("\n" + "Você tem " + vidas + " vidas" + "\n-> Letras utilizadas: " + letrasUtilizadas
					+ "\n-> Qual letra você deseja tentar?");
			letra = teclado.next().toUpperCase().charAt(0);
			letrasUtilizadas += " " + letra;

			boolean perdeVida = true;
			for (int i = 0; i < sorteada.length(); i++) {

				if (letra == sorteada.charAt(i)) {
					System.out.println("Tem essa letra na posição " + i);
					acertos[i] = 1;
					perdeVida = false;
				}
			}
			if (perdeVida) {
				vidas--;
				System.out.println("\n");
			}
			ganhou = true;
			for (int i = 0; i < sorteada.length(); i++) {
				if (acertos[i] == 0) {
					System.out.print(" _ ");
					ganhou = false;
				} else {
					System.out.print(" " + sorteada.charAt(i) + "");
				}
			}
			System.out.println("\n");

		} while (!ganhou && vidas > 0);

		if (vidas != 0) {
			System.out.println("\nCongratulations my friend!");
			adicionaPalavra(teclado);
		} else {
			System.out.println("Você perdeu, jogue novamente!");
			System.out.println("\tA palavra era " + sorteada);
		}
		teclado.close();
	}

}
