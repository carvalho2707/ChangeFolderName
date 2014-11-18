package bd;

import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String REQ_ESCOLHA = "Choose one option:";
	private static final String OPTION0 = "0 - Quit";
	private static final String OPTION1 = "1 - Read";
	private static final String OPTION2 = "2 - Save Errors";
	private static final String WRONGOPTION = "Choose one option 0 - 2";
	private static final String COMMAND = ">";
	private static Scanner in;
	
	public final void  meteMenu(){
		System.out.println(REQ_ESCOLHA);
		System.out.println(OPTION0);
		System.out.println(OPTION1);
		System.out.println(OPTION2);
		System.out.print(COMMAND);
	}
	
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		String folder1 = "E:/Filmes/HD/";
		String folder2 = "D:/";
		App p = new App();
		Gestor g = new Gestor(p);
		Main mainPrincipal = new Main();
		mainPrincipal.meteMenu();
		boolean sair = false;
		do{
			int optionSelected;
			try{
				in = new Scanner(System.in);
				optionSelected = in.nextInt();
			}catch(InputMismatchException e){
				optionSelected = 66;
			}
			switch(optionSelected){
			case 1:
				g.getApp().lePasta(folder1);
				System.out.println("-----------------------------END OF FOLDER------------------------------");
				g.getApp().lePasta(folder2);
				mainPrincipal.meteMenu();
				break;
			case 2:
				g.getApp().saveTxtWrong();
				mainPrincipal.meteMenu();
				break;
			case 0:
				sair = true;
				break;
			default:
                System.out.println(mainPrincipal.WRONGOPTION);
                System.out.print(mainPrincipal.COMMAND);
			}
		}while(!sair);
		System.out.println("BYE!");
		
	} 
}
	
