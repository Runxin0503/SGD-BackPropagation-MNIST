package tests;

import org.junit.jupiter.api.Test;

import main.Activation;
import main.Cost;
import main.NN;
import java.util.Arrays;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

public class EnumTest {

    /**
     * Tests expected crossEntropy loss on 1 output node
     */
    @Test
    void crossEntropyLossTest() {
        double[] exampleData = getRandomDoubleArr(784, () -> Math.random() > 0.5 ? 1.0 : 0.0);
        NN NeuralNet = new NN(null, Activation.softmax, Cost.crossEntropy, 784, 1);
        assertEquals(0, NeuralNet.calculateCosts(exampleData, new double[]{1}));
        assertEquals(1_000_000, NeuralNet.calculateCosts(exampleData, new double[]{0}));
    }

    /**
     * Tests expected crossEntropy loss on 10 output node
     */
    @Test
    void crossEntropyLossTest2() {
        double[] exampleData = getRandomDoubleArr(784, () -> Math.random() > 0.5 ? 1.0 : 0.0);
        NN NeuralNet = new NN(null, Activation.softmax, Cost.crossEntropy, 784, 10);
        double[] ones = new double[10];
        Arrays.fill(ones, 1);
        assertEquals(-10 * Math.log(0.9), NeuralNet.calculateCosts(exampleData, new double[10]),1e-6);
        assertEquals(-10 * Math.log(0.1), NeuralNet.calculateCosts(exampleData, ones),1e-6);
    }

    /**
     * Tests java.Activation Function none
     */
    @Test
    void noneAFTest() {
        double[] exampleData = getRandomDoubleArr(784, () -> Math.random() > 0.5 ? 1.0 : 0.0);
        NN NeuralNet = new NN(Activation.none, Activation.none, null, 784, 1);

        assertArrayEquals(new double[]{0}, NeuralNet.calculateOutput(exampleData));
    }

    /**
     * Tests java.Activation Function ReLU
     */
    @Test
    void ReLUTest() {
        //TODO Implement
    }

    /**
     * Tests java.Activation Function Sigmoid
     */
    @Test
    void SigmoidTest() {
        double[] exampleData = getRandomDoubleArr(784, () -> Math.random() > 0.5 ? 1.0 : 0.0);
        NN NeuralNet = new NN(Activation.none, Activation.sigmoid, null, 784, 1);

        assertArrayEquals(new double[]{0.5}, NeuralNet.calculateOutput(exampleData));
    }

    /**
     * Tests java.Activation Function Tanh
     */
    @Test
    void TanhTest() {
        double[] exampleData = getRandomDoubleArr(784, () -> Math.random() > 0.5 ? 1.0 : 0.0);
        NN NeuralNet = new NN(Activation.none, Activation.tanh, null, 784, 1);

        assertArrayEquals(new double[]{0}, NeuralNet.calculateOutput(exampleData));
    }

    /**
     * Tests java.Activation Function LeakyReLU
     */
    @Test
    void LeakyReLUTest() {
        //TODO implement
    }

    /**
     * Tests java.Activation Function softmax
     */
    @Test
    void softmaxTest() {
        double[] exampleData = getRandomDoubleArr(784, () -> Math.random() > 0.5 ? 1.0 : 0.0);
        NN NeuralNet = new NN(Activation.none, Activation.softmax, null, 784, 1);

        assertArrayEquals(new double[]{1}, NeuralNet.calculateOutput(exampleData));
    }

    public static double[] getRandomDoubleArr(int length, Supplier<Double> randomNumberGenerator) {
        double[] arr = new double[length];
        for (int i = 0; i < arr.length; i++) arr[i] = randomNumberGenerator.get();
        return arr;
    }
}
