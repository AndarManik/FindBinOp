import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class TrainEnv {
    public static BinOpMethods bom = new BinOpMethods();
    public static BiasManager nn = new BiasManager(new int[]{2, 3, 1}, 0.5, 16);
    public static Scanner sc = new Scanner(System.in);
    public static DoubleArrayList score = bom.getScoreList(nn);
    public static void main(String[] args) throws IOException {
        generateData();
    }

    public static int generateData() throws IOException {
        File networkFile = new File("C:\\Users\\Agi\\Documents\\FindBinOp\\out\\New folder\\network.txt");
        networkFile.createNewFile();
        FileWriter networkWriter = new FileWriter(networkFile.getAbsolutePath());

        File biasFile = new File("C:\\Users\\Agi\\Documents\\FindBinOp\\out\\New folder\\biases.txt");
        biasFile.createNewFile();
        FileWriter biasWriter = new FileWriter(biasFile.getAbsolutePath());

        int writeCount = 0;
        for(int i = 0; i < 10000; i++) {
            bom.train(nn, 6, 0.01);
            score = bom.getScoreList(nn);
            double[] max = score.max();
            if(max[1] < 0.04) {
                networkWriter.write(writeCount + "\n");
                biasWriter.write(writeCount + "\n");
                writeCount++;

                System.out.println("Write");
                nn.saveNetwork(networkWriter);
                nn.saveBiases(biasWriter);

                networkWriter.flush();
                biasWriter.flush();
            }
            resetCommand();
        }

        return 1;
    }

    public static int trainMethod()
    {
        System.out.println("1: train, 2: score, 3: bias train, 4: randomize bias, 5: reset, 6: script");
        switch(sc.nextInt()){
            case 1: trainCommand();
                break;
            case 2: scoreCommand();
                break;
            case 3: biasTrainCommand();
                break;
            case 4: randomizeBiasCommand();
                break;
            case 5: resetCommand();
                break;
            case 6: script();
        }

        return trainMethod();
    }

    public static void script()
    {
        System.out.println("int count")  ;
        int count = sc.nextInt();
        for(int i = 0; i < count; i++) {
            bom.train(nn, 4, 0.001);
            randomizeBiasCommand();
        }
    }

    public static void resetCommand()
    {
        nn = new BiasManager(new int[]{2, 3, 1}, 0.5, 16);
    }


    public static void trainAbilityCommand(){
            bom.randomizeBias(nn);
            trainCommand();
            scoreCommand();
    }

    public static void scoreCommand() {
        score = bom.getScoreList(nn).sort(1);
        for(int i = 0; i < 16; i++)
            System.out.println((int) score.get(i)[0] + " " + score.get(i)[1]);
    }

    private static void trainCommand() {
        System.out.println("Int epocMag, Double rate");
        bom.train(nn, sc.nextInt(), sc.nextDouble());
    }

    private static void biasTrainCommand(){
        System.out.println("Int epocMag, Double rate");
        bom.trainBias(nn, sc.nextInt(), sc.nextDouble());
    }

    private static void randomizeBiasCommand(){
        bom.randomizeBias(nn);
    }

}
