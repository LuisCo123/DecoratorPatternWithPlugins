package camaraodecorator;

import interfaces.PizzaComponent;
import interfaces.PizzaDecorator;

public class CamaraoDecorator extends PizzaDecorator {

	public CamaraoDecorator(PizzaComponent decorated){
		this.decorated = decorated;
	}
	public void prepara() {
		this.decorated.prepara();
		System.out.println("Adicionando Camarao");
		
	}

}
