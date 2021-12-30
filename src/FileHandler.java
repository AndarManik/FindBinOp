import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileHandler extends TrainEnv{
    static String networkPath = "C:\\Users\\Agi\\Documents\\FindBinOp\\out\\New folder\\network.txt";
    static String biasesPath = "C:\\Users\\Agi\\Documents\\FindBinOp\\out\\New folder\\biases.txt";

    static PrintWriter networkOut;
    static PrintWriter biasOut;

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

    public int generate(int count){
        int counter = 0;
        while(counter < count){
            train(nn, 5, 0.1);
            score = getScoreList(nn);
            double e = score.max()[1];
            if(e < 0.05) {
                System.out.println("Write");
                nn.saveNetwork(networkOut);
                nn.saveBiases(biasOut);

                networkOut.println();
                biasOut.println();
                networkOut.flush();
                biasOut.flush();

                counter++;
            }
            resetCommand();
        }
        return 1;
    }
}
