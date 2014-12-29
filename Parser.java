import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

public class Parser {
  public String parseChickenToString(String line) {
	String[] splited = line.split("\\s+");
	int splitedLength = splited.length;

	String inWord = "";
	int inAscii = 0;

	for (int i = 0; i < splitedLength; i++) {
	  String[] howManyChicken = splited[i].split("C");
	  inAscii = howManyChicken.length;
	  inWord += Character.toString((char) inAscii);
	}

	return inWord;
  }

  public void saveAsJava(String name, String line) throws IOException {
	File file = new File(name + ".java");

	if (!file.exists()) file.createNewFile();

	FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
	BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
	bufferedWriter.write(line);
	bufferedWriter.close();
  }

  public void compile(String name) throws IOException, InterruptedException {
	Process process = Runtime.getRuntime().exec("javac " + name + ".java");
	process.waitFor();
  }	

  public static void main(String[] args) {
	if (args.length == 0) {
	  System.out.println("WHERE IS MY CHICKEN!!!");
	  System.out.println("Usage: Fry [Chicken name].chicken");
	  System.exit(0);
	}

	String chickenName = args[0].substring(0, args[0].lastIndexOf("."));

	File file = new File(args[0]);

	if (!file.exists()) {
	  System.out.println("No such chicken or chicken");
	  System.exit(0);
	}



	try {
  	  int line = 0;
  	  BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

  	  String manyChickens;
  	  Parser parser = new Parser();
  
	  while ((manyChickens = bufferedReader.readLine()) != null) {
  		String parsedChicken = parser.parseChickenToString(manyChickens);
  		parser.saveAsJava(chickenName, parsedChicken);
  	  }

	  parser.compile(chickenName);
	} catch (IOException | InterruptedException e) {
	  System.out.println(e.getMessage());
	}
  }
}
