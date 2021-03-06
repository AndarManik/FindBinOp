import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

/*
    BiasManager stores biases to be used in multi task settings.
    It's able to extract biases from the network
    set the bias in the network
    and update stored biases using the bias of network
 */
public class BiasManager extends NeuralNetworkBias{
    public final ArrayList<ArrayList<double[]>> biases;
    private int currentIndex;


    //construct a neural network along with a list of biases
    public BiasManager(int[] dim, double scale, int biasCount) {
        super(dim, scale);
        biases = new ArrayList<>();
        ArrayList<double[]> bias = getBias();
        for(int i = 0; i < biasCount; i++) {
            ArrayList<double[]> curBias = new ArrayList<>();
            for(double[] b : bias)
                curBias.add(b.clone());
            biases.add(curBias);
        }
    }

    //returns a copy of the current bias
    public ArrayList<double[]> getBias()
    {
        ArrayList<double[]> biases = new ArrayList<>();
        for(Layer l : network)
            biases.add(l.bias);
        return biases;
    }

    public void setBias(ArrayList<double[]> bias)
    {
        for(int i = 0; i < network.size(); i++)
            network.get(i).bias = bias.get(i);
    }

    public void setBias(int biasIndex)
    {
        currentIndex = biasIndex;
        setBias(biases.get(biasIndex));
    }

    public void updateBias(double rate) {
        for(Layer l : network)
            for(int i = 0; i < l.weight.length; i++)
                for(int j = 0; j < l.weight[0].length; j++)
                    l.weight[i][j] -= l.grad[i][j] * rate;
    }

    public ArrayList<double[]> getBiasPOINTER(int index) {
        return biases.get(index);
    }

    public void randomizeBias(int index) {
        ArrayList<double[]> curr = biases.get(index);
        for(double[] d : curr)
            for(int i = 0; i < d.length; i++)
                d[i] = Math.random() - 0.5 ;
    }

    public void saveNetwork(PrintWriter networkOut){
        for(Layer l : network)
            for (double[] d : l.weight)
                networkOut.println(Arrays.toString(d));
    }

    public void saveBiases(PrintWriter BiasOut){
        for(ArrayList<double[]> bias : biases) {
            for (double[] d : bias)
                BiasOut.print(Arrays.toString(d) + "  ");
            BiasOut.println();
        }
    }
}
