package interfaces;

public abstract class PizzaDecorator implements PizzaComponent{
	protected PizzaComponent decorated;
	public final void setDecorated(PizzaComponent decorated) {
		this.decorated = decorated;
	}
}
