package tests;

import main.Activation;
import main.Cost;
import main.NN;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MNISTDatasetTest {

    private static final int MNIST_Size = 70_000;
    private static final double[][] images = new double[MNIST_Size][784];
    private static final int[] answers = new int[MNIST_Size];

    static {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("lib/MNIST DATA.csv"))) {
            String line;
            int count = 0;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(",");
                answers[count] = Integer.parseInt(parts[0]);
                for (int i = 1; i < parts.length; i++) {
                    images[count][i - 1] = Integer.parseInt(parts[i]) / 255.0;
                }
                count++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testDataset() {
        NN NeuralNet = new NN(Activation.sigmoid, Activation.softmax, Cost.crossEntropy, 784, 200, 10);

        final int batchSize = 10;
        final int report_interval = MNIST_Size / batchSize / 10 * batchSize;
        for (int trainingIndex = 0; trainingIndex < MNIST_Size; trainingIndex += batchSize) {
            double[][] trainBatchInputs = new double[batchSize][784];
            double[][] trainBatchOutputs = new double[batchSize][10];
            for (int i = 0; i < batchSize; i++) {
                trainBatchInputs[i] = images[trainingIndex + i];
                trainBatchOutputs[i][answers[trainingIndex + i]] = 1;
            }

            NN.learn(NeuralNet, 0.02, 0.9, trainBatchInputs, trainBatchOutputs);

            if ((trainingIndex+batchSize) % report_interval == 0) {
                System.out.print("Iteration " + ((int)(((double) trainingIndex) / batchSize) + 1));
                System.out.println(", "+(int)((trainingIndex + 1.0) / MNIST_Size * 10000) / 100.0+"% finished");
                reportPerformanceOnTest(NeuralNet,trainingIndex);
                reportPerformanceOnTrain(NeuralNet,trainingIndex);
                System.out.println("Predicted Output for " + answers[0] + ": " + getOutput(NeuralNet.calculateOutput(images[0])));
                System.out.println(Arrays.toString(NeuralNet.calculateOutput(images[0])));
                System.out.println("--------------------");
            }
        }
        reportPerformanceOnTrain(NeuralNet,MNIST_Size);
    }

    /**
     * Reports the performance of {@code NeuralNet} on the first {@code n} inputs of the MNIST dataset
     * <br>In other words, evaluate the model on examples it has seen
     */
    private static void reportPerformanceOnTrain(NN NeuralNet,int n) {
        double cost = 0;
        int accuracy = 0;
        for (int i = 0; i < n; i++) {
            double[] expectedOutput = new double[10];
            expectedOutput[answers[i]] = 1;
            cost += NeuralNet.calculateCosts(images[i], expectedOutput);
            if (evaluateOutput(NeuralNet.calculateOutput(images[i]), answers[i])) accuracy++;
        }
        System.out.println("Train Avg Cost: " + (int)(cost * 100) / (n*100.0));
        System.out.println("Train Accuracy: " + accuracy * 10000 / (n*100.0) + "%");
    }

    /**
     * Reports the performance of {@code NeuralNet} on everything BUT the first {@code n} inputs of the MNIST dataset
     * <br>In other words, evaluate the model on examples it hasn't seen yet
     */
    private static void reportPerformanceOnTest(NN NeuralNet,int n) {
        double cost = 0;
        int accuracy = 0;
        for (int i = n; i < MNIST_Size; i++) {
            double[] expectedOutput = new double[10];
            expectedOutput[answers[i]] = 1;
            cost += NeuralNet.calculateCosts(images[i], expectedOutput);
            if (evaluateOutput(NeuralNet.calculateOutput(images[i]), answers[i])) accuracy++;
        }
        System.out.println("Test Avg Cost: " + (int)(cost * 100) / (MNIST_Size-n) * 0.01);
        System.out.println("Test Accuracy: " + accuracy * 10000 / (MNIST_Size-n) * 0.01 + "%");
    }

    private static boolean evaluateOutput(double[] output, int answer) {
        return getOutput(output) == answer;
    }

    private static int getOutput(double[] output){
        int guess = 0;
        for (int j = 0; j < output.length; j++) {
            if (output[j] > output[guess]) guess = j;
        }
        return guess;
    }
}