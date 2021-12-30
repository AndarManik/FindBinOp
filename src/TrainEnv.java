import java.util.Scanner;

public class TrainEnv extends BinOpMethods{
    public static BiasManager nn = new BiasManager(new int[]{2, 3, 1}, 0.5, 16);
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args)
    {
        VectorArray data = new VectorArray();
        for (int i = 0; i < 1000; i++)
            data.add(new double[] {random() - 2, random() - 2});

        for (int i = 0; i < 1000; i++)
            data.add(new double[] {random() + 2, random() - 2});

        for (int i = 0; i < 1000; i++)
            data.add(new double[] {random() - 2, random() + 2});

        for (int i = 0; i < 1000; i++)
            data.add(new double[] {random() + 2, random() + 2});
        KMeanCluster kmc = new KMeanCluster(4, data);

        kmc.update(6);

        System.out.println(kmc);

    }

    //General BON training environment
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
            case 6: scriptCommand();
        }}
        catch(Exception e)
        {
            System.out.println("Oops");
        }

        return trainMethod();
    }

    public static void scriptCommand()
    {
        System.out.println("int count")  ;
        int count = sc.nextInt();
        for(int i = 0; i < count; i++) {
            train(nn, 4, 0.001);
            randomizeBiasCommand();
        }
    }

    public static void resetCommand()
    {
        nn = new BiasManager(new int[]{2, 3, 1}, 0.5, 16);
    }

    public static void scoreCommand() {
        score = getScoreList(nn).sort(1);
        for(int i = 0; i < 16; i++)
            System.out.println((int) score.get(i)[0] + " " + score.get(i)[1]);
    }

    private static void trainCommand() {
        System.out.println("Int epocMag, Double rate");
        train(nn, sc.nextInt(), sc.nextDouble());
    }

    private static void biasTrainCommand(){
        System.out.println("Int epocMag, Double rate");
        trainBias(nn, sc.nextInt(), sc.nextDouble());
    }

    private static void randomizeBiasCommand(){
        randomizeBias(nn);
    }
}
