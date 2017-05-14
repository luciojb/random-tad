package tads;

import java.util.concurrent.atomic.AtomicLong;

public class Aleatorio {
	
	private AtomicLong seed;
	
	//Multiplicador, número 25214903917 notado em hexadecimal com tipo L, indicando long
	private final static long MULTIPLICADOR = 0x5DEECE66DL;
	
	//Incremento, número 11 notado em hexadecimal com tipo L, indicando long
	private final static long INCREMENTO = 0xBL;
	
	/*
	 * Máscara, número 1 em binário em notação long com shift de 48 bits pela direita
	 * Representa o número 281474976710655 (281474976710656 - 1)
	 */
	private final static long MASCARA = (1L << 48) - 1;
	
	//O valor padrão do seed, conforme especificado na documentação da classe Random do Java
	private static volatile long seedUniquifier = 8682522807148012L;
	
	public int aleatorioInt(){
		int num;
		do{
			num = proximoRandom(32);
		} while (num<0);
		/*
		 * Retorna um novo inteiro
		 */
		return num;
	}
	
	public double aleatorioDouble(){
		
		/*
		 * Gera-se um número inteiro aleatório
		 * Com shift de 27 bits pela direita (diz-se left shift, mas os bits se inserem pela direita)
		 * Somado a um outro inteiro de 21 bits, dividindo o total por um double de valor 1 long com
		 * Shift de 53 bits, gerando um valor de 0.0 a 1.0
		 */
		return (((long)(proximoRandom(26)) << 27)
				+ 
				(int)(proximoRandom(27)))
				/
				(double)(1L << 53);
	}
	
	public int aleatorioIntervaloInt (int inicio, int fim){
		/*
		 *  Não está muito claro nos docs do java, mas
		 *  Entende-se por um número que distribua mais uniformemente
		 *  Uma sequência de aleatoriedade escolhendo 31 bits.
		 */
		int aleatorio = proximoRandom(31);
		
		//Vê se é potência de 2
		if ((fim & -fim) == fim){
			aleatorio = (int)((fim * (long) proximoRandom(31)) >> 31);
		} else {
			long proximo;
			//Repete até estar no intervalo
			do {
		       proximo = proximoRandom(31);
		       aleatorio = (int) (inicio + (proximo % fim));
			} while (aleatorio < inicio || aleatorio > fim);
		}
		return aleatorio;
	}
	
	public double aleatorioDoubleIntervalo(double inicio, double fim){
		double r = aleatorioDouble();
		do{
			r = r * (fim - inicio) + inicio;
		} while (r<inicio || r>fim);
		
		return r;
	}
	
	private final int proximoRandom(int bits){
		/*
		 * O seed é atualizado para um novo valor toda vez que for chamado o método.
		 * Depende do tempo do sistema em nanossegundos.
		 * Acredita-se uma entropia aceitável para este tipo de contexto.
		 */
		this.seed = new AtomicLong(((++seedUniquifier + (2 >> System.nanoTime()))^ MULTIPLICADOR) & MASCARA);
		
		/*
		 * Retorna um novo número pseudo-aleatório
		 * Depende do tempo do sistema em nanossegundos no momento que o método for chamado
		 * O método ".get" vem da classe AtomicLong, que serve aqui para armazenar grandes números
		 * E este método serve para retornar um long do número armazenado
		 */
		return (int)(((this.seed.get() * MULTIPLICADOR + INCREMENTO) & MASCARA) >>> (48 - bits));
	}
}
