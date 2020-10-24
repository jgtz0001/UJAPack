package es.ujaen.dae.ujapack.app;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.*;
import java.text.ParseException;
import java.util.Iterator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages="es.ujaen.dae.ujapack.app")
public class UjapackApplication {

	public static void main(String[] args)throws FileNotFoundException, IOException, ParseException {
            SpringApplication.run(UjapackApplication.class, args);
             
            JsonParser parser = new JsonParser();
		try{
			Object obj = parser.parse(new FileReader("redujapack.json"));
			 
			JsonObject jsonObject = (JsonObject) obj;
	 
			String nombre = (String) jsonObject.get("nombre").getAsString();
			System.out.println("nombre:"+nombre);
	 
			String localizacion = (String) jsonObject.get("localizacion").getAsString();
			System.out.println("localizacion:"+localizacion);
	 
			// recorrer arreglo
			JsonArray leng= (JsonArray) jsonObject.get("provincias");
			System.out.println("provincias:");
			Iterator iterator =leng.iterator();
			while (iterator.hasNext()) {
				System.out.println(iterator.next());
			}
			
		}catch(Exception ex){
			System.err.println("Error: "+ex.toString());
		}finally{
			
		}
}

}

           
//            String jsonStr=Files.readString(new File("redujapack.json").toPath());
//            JsonObject raiz=new Gson().fromJson(jsonStr, JsonObject.class);
//            System.out.println(raiz.keySet());
//            JsonObject centro1= raiz.getAsJsonObject("1");
//    
//            System.out.println(centro1.get("nombre").getAsString());
//            System.out.println(centro1.get("localizacion").getAsString());
//    
//            JsonArray provincias = centro1.getAsJsonArray("provincias");
//            
//            System.out.println(provincias.size());
//            System.out.println(provincias.get(0).getAsString());

