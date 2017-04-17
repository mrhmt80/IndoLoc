package ch.joelniklaus.indoloc.statistics;

import java.text.DecimalFormat;

import weka.classifiers.Evaluation;

/**
 * Data object containing the train time, test time, accuracy and evaluation of a classifier.
 *
 * Created by joelniklaus on 26.11.16.
 */

public class ClassifierRating {
    private String name;
    private double meanTrainTime;
    private double meanTestTime;
    private double meanAccuracy;
    private Evaluation evaluation;

    public ClassifierRating(String name, double meanAccuracy, double meanTestTime, double meanTrainTime, Evaluation evaluation) {
        this.name = name;
        this.meanAccuracy = meanAccuracy;
        this.meanTestTime = meanTestTime;
        this.meanTrainTime = meanTrainTime;
        this.evaluation = evaluation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMeanTrainTime() {
        return meanTrainTime;
    }

    public void setMeanTrainTime(double meanTrainTime) {
        this.meanTrainTime = meanTrainTime;
    }

    public double getMeanTestTime() {
        return meanTestTime;
    }

    public void setMeanTestTime(double meanTestTime) {
        this.meanTestTime = meanTestTime;
    }

    public double getMeanAccuracy() {
        return meanAccuracy;
    }

    public void setMeanAccuracy(double meanAccuracy) {
        this.meanAccuracy = meanAccuracy;
    }

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }

    @Override
    public String toString() {
        return "\n" + fixedLengthString(name, 15) +
                fixedLengthString("Accuracy: " + new DecimalFormat("#.##").format(meanAccuracy) + " %", 25)
                + fixedLengthString("Test Time: " + meanTestTime + " µs", 25)
                + fixedLengthString("Train Time: " + meanTrainTime + " µs", 25);
    }

    private static String fixedLengthString(String string, int length) {
        return String.format("%1$" + length + "s", string);
    }
}
