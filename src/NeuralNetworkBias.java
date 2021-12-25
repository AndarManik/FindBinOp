import java.util.ArrayList;

public class NeuralNetworkBias
{
    ArrayList<Layer> network = new ArrayList<>();
    Functions f = new TanhTanh();
    public class Layer
    {
        double[] val;
        double[] preAct;
        double[][] weight;
        double[][] grad;
        double[] bias;
        double[] biasGrad;
        double[] preActGrad;

        public void pass(double[] prevVal, boolean isOut)
        {
            preAct = bias.clone();
            for (int pos = 0; pos < val.length; pos++) {
                for (int prev = 0; prev < prevVal.length; prev++)
                    preAct[pos] += prevVal[prev] * weight[pos][prev];
                val[pos] = (isOut) ? f.activateOut(preAct[pos]) : f.activate(preAct[pos]);
            }
        }

        public void outback(double[] expected) {
            for (int pos = 0; pos < val.length; pos++) {
                preActGrad[pos] = f.derOut(preAct[pos], val[pos], expected[pos]);
                biasGrad[pos] += preActGrad[pos];
            }
        }

        public void back(double[] nextPreActGrad, double[][] nextWeight)
        {
            preActGrad = new double[preActGrad.length];
            for(int pos = 0; pos < preActGrad.length; pos++) {
                for (int next = 0; next < nextPreActGrad.length; next++)
                    preActGrad[pos] += nextPreActGrad[next] * nextWeight[next][pos];
                preActGrad[pos] *= f.der(preAct[pos], val[pos]);
                biasGrad[pos] += preActGrad[pos];
            }
        }

        public void weightGrad(double[] prevVal)
        {
            for(int pos = 0; pos < grad.length; pos++)
                for(int prev = 0; prev < grad[0].length; prev++)
                    grad[pos][prev] += preActGrad[pos] * prevVal[prev];
        }

        public void update(double rate) {
                for(int i = 0; i < weight.length; i++)
                    for(int j = 0; j < weight[i].length; j++) {
                        weight[i][j] -= grad[i][j] * rate;
                        grad[i][j] = 0;
                    }

                for(int i = 0; i < bias.length; i++) {
                    bias[i] -= biasGrad[i] * rate;
                    biasGrad[i] = 0;
                }

        }
    }

    public NeuralNetworkBias(int[] dim, double scale)
    {
        for(int layer = 1; layer < dim.length; layer++)
        {
            Layer l = new Layer();
            l.val = new double[dim[layer]];
            l.preAct = new double[dim[layer]];

            l.weight = new double[dim[layer]][dim[layer - 1]];
            l.grad = new double[dim[layer]][dim[layer - 1]];
            for (double[] w : l.weight)
                for (int i = 0; i < w.length; i++)
                    w[i] = 2 * (Math.random() - 0.5) * scale;

            l.bias = new double[dim[layer]];
            for (int i = 0; i < l.bias.length; i++)
                l.bias[i] = 2 * (Math.random() - 0.5) * scale;
            l.biasGrad = new double[dim[layer]];
            l.preActGrad = new double[dim[layer]];

            network.add(l);
        }
    }

    public NeuralNetworkBias calc(double[] input)
    {
        network.get(0).pass(input, false);
        for(int layer = 1; layer < network.size(); layer++)
            network.get(layer).pass(network.get(layer-1).val, layer == network.size() - 1);
        return this;
    }

    public NeuralNetworkBias backProp(double[] input, double[] expected) {
        calc(input);

        network.get(network.size() - 1).outback(expected);
        for(int layer = network.size() - 2; layer >= 0; layer--) {
            network.get(layer).back(network.get(layer+1).preActGrad, network.get(layer+1).weight);
        }

        network.get(0).weightGrad(input);
        for(int layer = 1; layer < network.size(); layer++)
            network.get(layer).weightGrad(network.get(layer - 1).val);
        return this;
    }

    public void update(double rate)
    {
        for(Layer l : network)
            l.update(rate);
    }

    public void clear()
    {
        for(Layer l : network) {
            l.grad = new double[l.grad.length][l.grad[0].length];
            l.biasGrad = new double[l.biasGrad.length];
        }

    }

    public double[] getOutput()
    {
        return network.get(network.size() - 1).val;
    }

    public double getError(double[] expected)
    {
        return f.error(getOutput(), expected);
    }

}

