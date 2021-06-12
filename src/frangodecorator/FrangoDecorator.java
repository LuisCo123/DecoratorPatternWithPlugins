package frangodecorator;

import interfaces.PizzaComponent;
import interfaces.PizzaDecorator;

public class FrangoDecorator extends PizzaDecorator{
	public FrangoDecorator(PizzaComponent decorated){
		this.decorated = decorated;
	}
	public void prepara() {
		this.decorated.prepara();
		System.out.println("Adicionando Frango");
		
	}
}
