public class BinaryData {

    public static DoubleArrayList getInputSpace(){
        DoubleArrayList inputSpace = new DoubleArrayList();

        for(int i = 0; i < 2; i++)
            for(int j = 0; j < 2; j++)
                inputSpace.add(normalize(new double[] {i, j}));

        return inputSpace;
    }

    public static DoubleArrayList getOutputSpace(){
        DoubleArrayList outputSpace = new DoubleArrayList();

        for(int i = 0; i < Math.pow(2,4); i++)
        {
            int value = i;
            double[] output = new double[4];
            for(int j = 0; j < 4; j++, value /= 2)
                output[j] = value % 2;
            outputSpace.add(normalize(output));
        }

        return outputSpace;
    }

    private static double[] normalize(double[] input) {
        double[] output = new double[input.length];
        for(int i = 0 ; i < input.length; i++)
            output[i] = (input[i] == 0) ? -1 : 1;
        return output;
    }
}
