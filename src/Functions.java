public interface Functions
{
    double activate(double input);
    double der(double input, double output);
    double activateOut(double input);
    double derOut(double preAct, double val, double exp);
    double error(double[] output, double[] expected);
}

class ReLuTanh implements Functions {
    public double activate(double input) {
        return Math.max(input, 0);
    }

    public double der(double input, double output) {
        return Math.max(input / Math.abs(input), 0);
    }

    public double activateOut(double input) {
        return Math.tanh(input);
    }

    public double derOut(double preAct, double val, double exp)
    {
        return (val - exp) *  (1 - val * val);
    }

    public double error(double[] output, double[] expected)
    {
        double error = 0;
        for(int i = 0; i < output.length; i++)
            error += 0.5 * Math.pow(output[i] - expected[i], 2);
        return error;
    }
}

class ReLuSig implements Functions {
    public double activate(double input) {
        return Math.max(input, 0);
    }

    public double der(double input, double output) {
        return Math.max(input / Math.abs(input), 0);
    }

    public double activateOut(double input) {
        return 1 / (1 + Math.exp(-input));
    }

    public double derOut(double preAct, double val, double exp)
    {
        return (val - exp) *  (1 - val) * val;
    }

    public double error(double[] output, double[] expected)
    {
        double error = 0;
        for(int i = 0; i < output.length; i++)
            error += 0.5 * Math.pow(output[i] - expected[i], 2);
        return error;
    }
}

class TanhTanh implements Functions {
    public double activate(double input) {
        return Math.tanh(input);
    }

    public double der(double input, double output) {
        return (1 - output * output);
    }

    public double activateOut(double input) {
        return Math.tanh(input);
    }

    public double derOut(double preAct, double val, double exp)
    {
        return (val - exp) *  (1 - val * val);
    }

    public double error(double[] output, double[] expected)
    {
        double error = 0;
        for(int i = 0; i < output.length; i++)
            error += 0.5 * Math.pow(output[i] - expected[i], 2);
        return error;
    }
}