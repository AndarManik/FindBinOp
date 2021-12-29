import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileHandler {
    String networkPath = "C:\\Users\\Agi\\Documents\\FindBinOp\\out\\New folder\\network.txt";
    String biasesPath = "C:\\Users\\Agi\\Documents\\FindBinOp\\out\\New folder\\biases.txt";

    PrintWriter networkOut;
    PrintWriter biasOut;

    public FileHandler(){
        try{
            networkOut = new PrintWriter(
                    new BufferedWriter(
                            new FileWriter(networkPath, true)));

            biasOut = new PrintWriter(
                    new BufferedWriter(
                            new FileWriter(biasesPath, true)));
        }
        catch (IOException e) {
            System.out.println("File DNE");
        }
    }
}
