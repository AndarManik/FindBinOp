import java.util.ArrayList;
import java.util.Arrays;

public class KMeanCluster {
    int k;
    VectorArray data;
    ArrayList<Cluster> clusters = new ArrayList<>();

    public KMeanCluster(int k, VectorArray data)
    {
        this.k = k;
        this.data = data;
        for (int i = 0; i < k; i++)
            clusters.add(new Cluster(data.random()));
    }

    public void update()
    {
        for(Cluster c : clusters)
            c.clear();

        for(double[] d : data)
        {
            double minDistance = Integer.MAX_VALUE;
            int minDistanceIndex = 0;
            for (int i = 0; i < clusters.size(); i++) {
                double distance = Math.min(minDistance, clusters.get(i).distance(d));
                if(distance < minDistance){
                    minDistance = distance;
                    minDistanceIndex = i;
                }
            }
            clusters.get(minDistanceIndex).add(d);
        }

        for(Cluster c : clusters)
            c.update();
    }

    public double totalVariance()
    {
        double variance = 0;
        for(Cluster c : clusters)
            variance += c.variance();
        return variance;
    }

    public void update(int updateCount)
    {
        for (int i = 0; i < updateCount; i++) {
            update();
            System.out.println(totalVariance());
        }
    }

    public class Cluster
    {
        double[] mean;
        VectorArray nearestNeighbor;

        public Cluster(double[] mean)
        {
            this.mean = mean;
        }

        public double distance(double[] d) {
            double distance = 0;
            for (int i = 0; i < mean.length; i++)
                distance += Math.pow(mean[i] - d[i], 2);
            return Math.sqrt(distance);
        }

        public void add(double[] d) {
            nearestNeighbor.add(d);
        }

        public void clear() {
            nearestNeighbor = new VectorArray();
        }

        public void update() {
            if(nearestNeighbor.size() == 0) return;
            mean = nearestNeighbor.average();
        }

        public double variance() {
            if(nearestNeighbor.size() == 0) return 0;
            double sumDistance = 0;
            for(double[] d : nearestNeighbor)
                sumDistance += distance(d);
            return sumDistance / nearestNeighbor.size();
        }

        @Override
        public String toString() {
            return Arrays.toString(mean);
        }
    }

    @Override
    public String toString() {
        StringBuilder returnString = new StringBuilder();
        for(Cluster c : clusters)
            returnString.append(c).append("\n");
        return returnString.toString();
    }
}
