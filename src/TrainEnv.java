import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class TrainEnv {
    public static BinOpMethods bom = new BinOpMethods();
    public static BiasManager nn = new BiasManager(new int[]{2, 3, 1}, 0.5, 16);
    public static Scanner sc = new Scanner(System.in);
    public static DoubleArrayList score = bom.getScoreList(nn);

    public static FileHandler fl = new FileHandler();

    public static void main(String[] args){
        generateData();
    }

    public static int generateData(){
        for(int i = 0; i < 1000000; i++) {
            bom.train(nn, 5, 0.1);
            score = bom.getScoreList(nn);
            double e = score.max()[1];
            if(e < 0.05) {
                System.out.println("Write");
                nn.saveNetwork(fl.networkOut);
                nn.saveBiases(fl.biasOut);

                fl.networkOut.println();
                fl.biasOut.println();
                fl.networkOut.flush();
                fl.biasOut.flush();
            }
            resetCommand();
        }
        return 1;
    }

    public static int trainMethod()
    {
        System.out.println("1: train, 2: score, 3: bias train, 4: randomize bias, 5: reset, 6: script");
        try { switch(sc.nextInt()){
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
        }}
        catch(Exception e)
        {
            System.out.println("Oops");
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
