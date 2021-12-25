import java.util.ArrayList;

public class DoubleArrayList
{
    public ArrayList<double[]> list = new ArrayList<>();
    public DoubleArrayList(){}

    public int size() {return list.size();}

    public DoubleArrayList add(double[] input){list.add(input); return this;}

    public DoubleArrayList add(int val) {add(new double[] {val}); return this;}

    public double[] get(int index) {return list.get(index);}

    public double[] remove(int index)
    {
        double[] output = get(index);
        list.remove(index);
        return output;
    }

    public double[] average()
    {
        double[] average = new double[list.get(0).length];
        for(double[] arr : list)
            for(int i = 0; i < arr.length; i++)
                average[i] += arr[i];
        for(int i = 0; i < average.length; i++)
            average[i] /= list.size();
        return average;
    }

    public double[] max()
    {
        double[] max = list.get(0).clone();
        for(double[] arr : list)
            for(int i = 0; i < arr.length; i++)
                if(arr[i] > max[i])
                    max[i] = arr[i];
        return max;
    }

    public DoubleArrayList sort(int index) {
        for(int i = 0; i < list.size() - 1; i++)
            for(int j = i + 1; j < list.size(); j++)
                if(list.get(i)[index] < list.get(j)[index])
                    swap(i, j);
        return this;
    }

    private void swap(int i, int j) {
        double[] store = list.get(i);
        list.set(i, list.get(j));
        list.set(j, store);
    }
}
