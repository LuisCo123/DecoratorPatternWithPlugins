package main;

import interfaces.PizzaComponent;

public class PizzaBasica implements PizzaComponent{
	public void prepara() {
		System.out.println("Preparando a Massa + Molho + Queijo");	
	}
}
