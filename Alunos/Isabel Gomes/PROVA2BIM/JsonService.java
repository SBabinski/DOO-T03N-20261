package fag;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonService {
	
	private static final String ARQUIVO = "usuario.json";
	
	public void salvarUsuario(Usuario usuario) {
		try {
			
			System.out.println("Tentando salvar arquivo...");

	        File arquivo = new File(ARQUIVO);
	        
	        System.out.println("Caminho: " +
	                arquivo.getAbsolutePath());

	        Gson gson = new GsonBuilder()
	                .setPrettyPrinting()
	                .create();

	        FileWriter escrita = new FileWriter(arquivo);

	        gson.toJson(usuario, escrita);

	        escrita.close();
	        
	        System.out.println("Arquivo salvo!");


	    }catch(Exception e) {
	        e.printStackTrace();
	    }
		
	}
	
	public Usuario carregarUsuario() {
		try {
			Gson gson = new Gson();
			
			FileReader leitor = new FileReader(ARQUIVO);
			
			Usuario usuario = gson.fromJson(leitor, Usuario.class);
			
			leitor.close();
			
			return usuario;
			
		}catch(Exception e) {
			e.printStackTrace();
		    //return null;
			return new Usuario();
		}		
	}
	
	
	public boolean arquivoExiste() {
		File arquivo = new File(ARQUIVO);
		return arquivo.exists();
	}

	
	
	
	
	
	
	
	
	
	
}
