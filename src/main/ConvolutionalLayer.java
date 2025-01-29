//package main;
//
//import java.util.function.Supplier;
//
//public class ConvolutionalLayer extends Layer {
//
//    private final double[][][] kernels;
//
//    private final double[][][] kernelsVelocity;
//
//    private final double[][][] kernelsVelocitySquared;
//
//    public ConvolutionalLayer(int inputWidth, int inputHeight, int inputLength,
//                              int kernelWidth, int kernelHeight, int numKernels,
//                              int strideWidth, int strideHeight, Supplier<Double> initializer) {
//        super(inputWidth * inputHeight * inputLength,
//                Math.ceilDiv(inputWidth - kernelWidth + 1, strideWidth) * Math.ceilDiv(inputHeight - kernelHeight + 1, strideHeight) * numKernels,
//                initializer); //todo integrate
//
//        this.kernels = new double[numKernels][kernelWidth][kernelHeight];
//        this.kernelsVelocity = new double[numKernels][kernelWidth][kernelHeight];
//        this.kernelsVelocitySquared = new double[numKernels][kernelWidth][kernelHeight];
//
//        for (int i = 0; i < numKernels; i++)
//            for (int j = 0; j < kernelWidth; j++)
//                for (int k = 0; k < kernelHeight; k++)
//                    kernels[i][j][k] = initializer.get();
//
//        for ()
//    }
//
//    /** Applies the weights and biases of this java.Layer to the given input. Returns a new array. */
//    public double[] calculateWeightedOutput(double[] input) {
//        double[] output = new double[nodes];
//
//        for (int i = 0; i < nodes; i++) {
//            for (int j = 0; j < input.length; j++) {
//                assert Double.isFinite(weights[i][j]);
//                output[i] += weights[i][j] * input[j];
//            }
//            output[i] += bias[i];
//            assert Double.isFinite(output[i]) : "Weighted output has reached Infinity";
//        }
//        //todo rewrite to convolutional logic
//
//        return output;
//    }
//
//    /**
//     * Given the derivative array of the latest input sum,
//     * calculates and shifts the given weight and bias gradients.
//     * @param weightGradient Rows: The neuron n stored in that layer <br>Columns: The weight deriv of a synapse pointing to n
//     * @param biasGradiant Index: The bias deriv of a node in that layer
//     * @return da_dC where a is the activation function of the layer before this one
//     */
//    public double[] updateGradient(double[][] weightGradient, double[] biasGradiant, double[] dz_dC, double[] x) {
//        //todo rewrite to convolutional logic
//        double[] da_dC = new double[weightGradient[0].length];
//        for (int i = 0; i < nodes; i++) {
//            for (int j = 0; j < weightGradient[0].length; j++) {
//                assert Double.isFinite(weightGradient[i][j]);
//                assert Double.isFinite(dz_dC[i]);
//                assert Double.isFinite(x[j]);
//
//                weightGradient[i][j] += dz_dC[i] * x[j];
//                assert Double.isFinite(weightGradient[i][j]) : "weightGradient[i][j](" + weightGradient[i][j] + ") + dz_dC[i](" + dz_dC[i] + ") * x[j](" + x[j] + ") is equal to an invalid (Infinite) value";
//
//                assert Double.isFinite(da_dC[j]);
//
//                da_dC[j] += dz_dC[i] * weights[i][j];
//            }
//            assert Double.isFinite(biasGradiant[i]);
//            assert Double.isFinite(dz_dC[i]);
//
//            biasGradiant[i] += dz_dC[i];
//        }
//        return da_dC;
//    }
//
//    /**
//     * Applies the {@code weightGradient} and {@code biasGradient} to the weight and bias of this java.Layer.
//     * <br>Updates the weight and bias's gradient velocity vectors accordingly as well.
//     */
//    public void applyGradiant(double[][] weightGradient, double[] biasGradient, double adjustedLearningRate, double momentum, double beta, double epsilon) {
//        //todo rewrite to convolutional logic
//        double correctionMomentum = 1 - Math.pow(momentum, t);
//        double correctionBeta = 1 - Math.pow(beta, t);
//        for (int i = 0; i < nodes; i++) {
//            for (int j = 0; j < weights[0].length; j++) {
//                assert Double.isFinite(weightGradient[i][j]);
////                weights[i][j] -= adjustedLearningRate * weightGradient[i][j];
////                weightsVelocity[i][j] = weightsVelocity[i][j] * momentum + (1 - momentum) * weightGradient[i][j];
////                weights[i][j] -= adjustedLearningRate * weightsVelocity[i][j];
////                weightsVelocity[i][j] = beta * weightsVelocity[i][j] + (1 - beta) * (weightGradient[i][j] * weightGradient[i][j]);
////                weights[i][j] -= adjustedLearningRate * weightGradient[i][j] / Math.sqrt(weightsVelocity[i][j] + epsilon);
//                weightsVelocity[i][j] = momentum * weightsVelocity[i][j] + (1 - momentum) * weightGradient[i][j];
//                weightsVelocitySquared[i][j] = beta * weightsVelocitySquared[i][j] + (1 - beta) * weightGradient[i][j] * weightGradient[i][j];
//                double correctedVelocity = weightsVelocity[i][j] / correctionMomentum;
//                double correctedVelocitySquared = weightsVelocitySquared[i][j] / correctionBeta;
//                weights[i][j] -= adjustedLearningRate * correctedVelocity / Math.sqrt(correctedVelocitySquared + epsilon);
//                assert Double.isFinite(weights[i][j]) : "\ncorrectedVelocity: " + correctedVelocity + "\ncorrectedVelocitySquared: " + correctedVelocitySquared + "\nweightsVelocity: " + weightsVelocity[i][j] + "\nweightsVelocitySquared: " + weightsVelocitySquared[i][j];
//            }
//            assert Double.isFinite(biasGradient[i]);
////            bias[i] -= adjustedLearningRate * biasGradient[i];
////            biasVelocity[i] = biasVelocity[i] * momentum + (1 - momentum) * biasGradient[i];
////            bias[i] -= adjustedLearningRate * biasVelocity[i];
////            biasVelocity[i] = beta * biasVelocity[i] + (1 - beta) * (biasGradient[i] * biasGradient[i]);
////            bias[i] -= adjustedLearningRate * biasGradient[i] / Math.sqrt(biasVelocity[i] + epsilon);
//            biasVelocity[i] = momentum * biasVelocity[i] + (1 - momentum) * biasGradient[i];
//            biasVelocitySquared[i] = beta * biasVelocitySquared[i] + (1 - beta) * biasGradient[i] * biasGradient[i];
//            double correctedVelocity = biasVelocity[i] / correctionMomentum;
//            double correctedVelocitySquared = biasVelocitySquared[i] / correctionBeta;
//            bias[i] -= adjustedLearningRate * correctedVelocity / Math.sqrt(correctedVelocitySquared + epsilon);
//            assert Double.isFinite(bias[i]) : "\ncorrectedVelocity: " + correctedVelocity + "\ncorrectedVelocitySquared: " + correctedVelocitySquared + "\nbiasVelocity: " + biasVelocity[i] + "\nbiasVelocitySquared: " + biasVelocitySquared[i];
//        }
//        t++;
//    }
//}
