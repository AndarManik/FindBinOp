import java.util.ArrayList;

public class BinOpMethods {
    public DoubleArrayList inputSpace = BinaryData.getInputSpace();
    public DoubleArrayList outputSpace = BinaryData.getOutputSpace();

    public void train(BiasManager nn, int epocMag, double rate) {
        nn.clear();
        for(int epoc = 0; epoc < Math.pow(10, epocMag); epoc++){
            nn.setBias(epoc % 16);
            double[] currentOp = outputSpace.get(epoc % 16);
            for(int i = 0; i < inputSpace.size(); i++)
                nn.backProp(inputSpace.get(i), new double[] {currentOp[i]}).getError(new double[] {currentOp[i]});
            nn.update(rate);
        }
    }

    public DoubleArrayList getScoreList(BiasManager nn)
    {
        DoubleArrayList scoreList = new DoubleArrayList();
        for(int epoc = 0; epoc < 16; epoc++)
        {
            nn.setBias(epoc);
            double[] currentOp = outputSpace.get(epoc);
            double error = 0;
            for(int i = 0; i < currentOp.length; i++)
                error += Math.abs((nn.calc(inputSpace.get(i)).getOutput()[0] - currentOp[i]));
            scoreList.add(new double[] {epoc, error});
        }

        return scoreList;
    }

    public void randomizeBias(BiasManager nn) {
        for(ArrayList<double[]> b : nn.biases)
            for(double[] d : b)
                for(int i = 0; i < d.length; i++)
                    d[i] = Math.random() - 0.5;
    }

    public void trainBias(BiasManager nn, int epocMag, double rate) {
        nn.clear();
        double error = 0;
        for(int epoc = 0; epoc < Math.pow(10, epocMag); epoc++){
            nn.setBias(epoc % 16);
            double[] currentOp = outputSpace.get(epoc % 16);
            for(int i = 0; i < inputSpace.size(); i++)
                error += nn.backProp(inputSpace.get(i), new double[] {currentOp[i]}).getError(new double[] {currentOp[i]});
            nn.updateBias(rate);

            if(epoc % 16 == 15) {
                System.out.println(error);
                error = 0;
            }
        }
    }
}
