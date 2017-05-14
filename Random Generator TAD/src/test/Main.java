package test;

import java.util.ArrayList;
import java.util.List;

import tads.Aleatorio;

public class Main {

	public static void main(String[] args) {
		long seed = System.nanoTime();
		
		System.out.println("Seed: "+seed);
		System.out.println("Multiplier 0x5DEECE66DL: "+0x5DEECE66DL);
		System.out.println("Increment 0xBL: "+0xBL);
		System.out.println("Mask (1L << 48) - 1: "+((1L << 48) - 1));
		System.out.println("New seed: "+ ((seed ^ 0x5DEECE66DL) & ((1L << 48) - 1)));
		for(int i=0; i<=10; i++)
			System.out.println("System nanotime on "+i+": "+System.nanoTime());
		System.out.println("(seed * 0x5DEECE66DL + 0xBL) ==> " + (seed * 0x5DEECE66DL + 0xBL));
		System.out.println("New number (seed * 0x5DEECE66DL + 0xBL) & ((1L << 48) - 1) ==> "+((seed * 0x5DEECE66DL + 0xBL) & ((1L << 48) - 1)));
		System.out.println("Return of next method: "+ (((seed * 0x5DEECE66DL + 0xBL) & ((1L << 32) - 1)) >>> (48 - 32)));
		System.out.println("\n\n");
		
		Aleatorio a = new Aleatorio();
		System.out.println("Inteiro aleatorio: "+a.aleatorioInt());
		System.out.println("Inteiro intervalo 10-1000: "+a.aleatorioIntervaloInt(10, 100));
		System.out.println("Double aleatorio 0.0 - 1.0: "+a.aleatorioDouble());
		System.out.println("Double intervalo 10-1000: "+a.aleatorioDoubleIntervalo(10, 1000));
		
		List<Integer> intBet10and1000 = new ArrayList<>();
		int[] countNumbers = new int[100];
		
		for (int i=0; i<1000000; i++){
			intBet10and1000.add(a.aleatorioIntervaloInt(0, 100));
		}
		for (int i: intBet10and1000){
			countNumbers[i]++;
		}
		System.out.println("Os números gerados 1 milhão de vezes:");
		for(int i=0; i<100; i++){
			if (countNumbers[i]>0)
				System.out.println(i + " = " + countNumbers[i]);
		}
		
		
	}

}
