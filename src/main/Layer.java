package main;

import java.util.Random;

/** A single layer of Neurons. Contains fully connected edges to every neuron in the previous layer */
public class Layer {

    /** The number of Neurons in this layer */
    //made public for testing purposes
    public final int nodes;

    /**
     * A 2D matrix of weights
     * <br>Rows: The neuron {@code n} in this layer
     * <br>Columns: The weight of a synapse pointing to {@code n}
     */ //made public for testing purposes
    public final double[][] weights;

    /** The bias of each neuron in this layer */
    //made public for testing purposes
    public final double[] bias;

    public Layer(int nodesBefore,int nodes) {
        this.nodes = nodes;
        this.bias = new double[nodes];
        this.weights = new double[nodes][nodesBefore];

        Random random = new Random();
        for(int i = 0; i < nodes; i++) {
            for(int j = 0; j < nodesBefore; j++) weights[i][j] = random.nextDouble(0.5,1.5) * (Math.random()>0.75?-1:1);
            bias[i] = random.nextDouble(0.5,1.5) * (Math.random()>0.75?-1:1);
        }
    }

    /** Applies the weights and biases of this java.Layer to the given input. Returns a new array. */
    public double[] calculateWeightedOutput(double[] input){
        double[] output = new double[nodes];

        for(int i=0;i<nodes;i++){
            for(int j=0;j<input.length;j++){
                assert Double.isFinite(weights[i][j]);
                output[i] += weights[i][j]*input[j];
            }
            output[i] += bias[i];
        }

        return output;
    }

    /**
     * Given the derivative array of the latest input sum,
     * calculates and shifts the given weight and bias gradients.
     * @param weightGradient Rows: The neuron n stored in that layer <br>Columns: The weight deriv of a synapse pointing to n
     * @param biasGradiant Index: The bias deriv of a node in that layer
     * @return da_dC where a is the activation function of the layer before this one
     */
    public double[] updateGradient(double[][] weightGradient, double[] biasGradiant, double[] dz_dC, double[] x){
        double[] da_dC = new double[weightGradient[0].length];
        for(int i=0;i<nodes;i++){
            for(int j=0;j<weightGradient[0].length;j++){
                weightGradient[i][j] += dz_dC[i] * x[j];
                da_dC[j] += dz_dC[i] * weights[i][j];
            }
            biasGradiant[i] += dz_dC[i];
        }
        return da_dC;
    }

    /** Return the number of Neurons contained in this java.Layer */
    public int getNumNodes() {return nodes;}

    /**
     * Applies the {@code weightGradient} and {@code biasGradient} to the weight and bias of this java.Layer.
     * <br>Updates the weight and bias's gradient velocity vectors accordingly as well.
     */
    public void applyGradiant(double[][] weightGradient,double[] biasGradient,double adjustedLearningRate,double momentum){
        for(int i=0;i<nodes;i++){
            for(int j=0;j<weights[0].length;j++){
                assert Double.isFinite(weightGradient[i][j]);
                weights[i][j] -= adjustedLearningRate * weightGradient[i][j];
            }
            assert Double.isFinite(biasGradient[i]);
            bias[i] -= adjustedLearningRate * biasGradient[i];
        }
    }
}
