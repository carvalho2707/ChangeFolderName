package bd;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import java.util.StringTokenizer;


public class App implements Serializable {
	private static final long serialVersionUID = 1L;
	private LinkedList<String> wrongMoviesList = new LinkedList<String>();
	
	public App(){		
	}

	public void lePasta(String dirFolder){
		try{
			File folder = new File(dirFolder);
			if(folder.isDirectory()){
					listFilesForFolder(folder);
			}
			else throw new IOException();
		}catch(IOException e){
			System.out.println("Folder not found");
		}
	}
	
	public void listFilesForFolder(File folder) throws IOException {
		String folderName = "";
		String pathToFolder = "";
		String folderNameNew = "";
		for (File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				if(isValidName(fileEntry.getName())){
						Path path = Paths.get(fileEntry.getPath());
						BasicFileAttributeView basicView = Files.getFileAttributeView(path, BasicFileAttributeView.class);
						BasicFileAttributes basicAttr = basicView.readAttributes();
						String data =String.format("BasicFileAttribute creationTime: %s", basicAttr.creationTime());
						StringTokenizer st = new StringTokenizer(data, ":");
			    		st.nextToken(); //doesn't matter
						folderName = fileEntry.getName();
						folderNameNew = calcToRemove(folderName);
						pathToFolder = fileEntry.getPath();
						System.out.println("Nome Pasta -> " + folderName);
						System.out.println("Caminho -> " + pathToFolder);
						System.out.println("Caminho -> " + folderNameNew);
						File[] ficheiros =fileEntry.listFiles();
						for(int i = 0; i < ficheiros.length; i++){
							File f = new File(pathToFolder+ "/" + ficheiros[i].getName());
							if(f.getPath().contains(".mkv")){
								System.out.println("Old file: " + f.getPath());
								System.out.println("Converting movie name");
								File fnew = new File(pathToFolder + "/"  +  folderNameNew + ".mkv");
								System.out.println("New file: " + fnew.getPath());
								f.renameTo(fnew);
							}
							if(f.getPath().contains(".srt")){
								System.out.println("Old file: " + f.getPath());
								System.out.println("Converting subtitles");
								File fnew = new File(pathToFolder + "/" +  folderNameNew + ".srt");
								System.out.println("New file: " + fnew.getPath());
								f.renameTo(fnew);
							}
						}
						File novaPasta = new File(folder.getPath() + "/" +  folderNameNew);
						fileEntry.renameTo(novaPasta);
						System.out.println("New Folder: " + novaPasta.getPath());
						System.out.println("------------------------------------------------");
				}
				else wrongMoviesList.add(fileEntry.getName());
			}
		}
	}


	/**
	 * 
	 * This folder search for sequences that common appear in my movie folder
	 * 
	 * @param folderName
	 * @return
	 */
	public boolean isValidName(String folderName) {
		if(folderName.toLowerCase().contains("bluray"))
			return false;
		else if(folderName.contains("  (720p)") || folderName.contains("  (1080p)") || folderName.contains("  (DVDrip)"))
			return false;
		else if(folderName.contains("  "))
			return false;
		else if(!contemQualidade(folderName))
			return false;
		else if(folderName.contains("(") || folderName.contains(")"))
			return true;
		return true;
	}
	
	public boolean contemQualidade(String nome){
		if(nome.contains("(720p)") || nome.contains("(1080p)") || nome.contains("(DVDRip)") || nome.contains("(480p)"))
			return true;
		return false;
				
	}

	
	public void saveTxtWrong(){
		String dest = "H:/Testes/";
		try{
			PrintWriter pw = new PrintWriter (new FileWriter(dest + "ficheiroErrados.txt"));
			for(String pas : wrongMoviesList)
				pw.println(pas.toString());
			pw.flush();
			pw.close();
		}
		catch(IOException k) {}
	}
	
	private String calcToRemove(String antigo) {
		String novo = "";
		char[] tmp = antigo.toCharArray();
		int i = 0;
		while(i < tmp.length){
			if(tmp[i] == '+'){
				//
			}
			else if(tmp[i] == '%'){
				novo += ' ';
			}
			else if(tmp[i+1] == '('){
				return novo;
			}
			else{
				novo += tmp[i];
			}
			i++;
		}
		return novo;
	}
}