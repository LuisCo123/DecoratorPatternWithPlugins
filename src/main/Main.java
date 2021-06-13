package main;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

import interfaces.PizzaComponent;
import thread.ThreadA;

public class Main {

	public static void main(String[] args) throws MalformedURLException, InstantiationException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		UserInterface ui = new UserInterface();
		ArrayList <String> sequence;
		ThreadA thread = new ThreadA();
		ui.setThread(thread);
		File currentDir;
		String[] pluginsName;
		do {
			currentDir = new File("./src/plugins");
			pluginsName = currentDir.list();
			ui.addList(pluginsName);
			do {
				synchronized (thread) {
					try {
						System.out.println("Aguardando escolha dos Ingredientes");
						thread.wait();
					}catch(InterruptedException e) {
						e.printStackTrace();
					}
				}
				sequence = ui.getSequence();
			}while(sequence==null);
		}while(sequence.get(0)=="Refresh");
		
		URL[] jars = new URL[pluginsName.length];
		for(int i = 0; i < pluginsName.length; i++) {
			jars[i] = new File("./src/plugins" + pluginsName[i]).toURL();
		}
		URLClassLoader ucl = new URLClassLoader(jars);
		
		String decoratorName = sequence.get(sequence.size() - 1).concat("Decorator");
		
		PizzaComponent[] pizzadec = new PizzaComponent[sequence.size()];
		pizzadec[sequence.size() - 1] = (PizzaComponent) Class.forName(decoratorName.toLowerCase() + "." + decoratorName,true, ucl)
				.getDeclaredConstructor(PizzaComponent.class).newInstance(new PizzaBasica());

		for(int i = sequence.size()-2; i >= 0 ; i--) {
			decoratorName = sequence.get(i).concat("Decorator");
				pizzadec[i] = (PizzaComponent) Class.forName(decoratorName.toLowerCase() + "." + decoratorName ,true, ucl)
											.getDeclaredConstructor(PizzaComponent.class).newInstance(pizzadec[i+1]);
		}
		pizzadec[0].prepara();

	}
}
