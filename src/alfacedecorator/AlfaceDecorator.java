package alfacedecorator;

import interfaces.PizzaComponent;
import interfaces.PizzaDecorator;

public class AlfaceDecorator extends PizzaDecorator{

	public AlfaceDecorator(PizzaComponent decorated){
		this.decorated = decorated;
	}
	public void prepara() {
		this.decorated.prepara();
		System.out.println("Adicionando Alface");		
	}

}
