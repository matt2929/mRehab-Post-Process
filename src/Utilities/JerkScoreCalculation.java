package Utilities;


import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by matt2929 on 1/16/18.
 */

public class JerkScoreCalculation {
	float[] lastData = null;
	ArrayList<Float> dataAccDerivative = new ArrayList<>();
	ArrayList<Long> times = new ArrayList<>();
	ArrayList<Float> jerkScores = new ArrayList<>();
	Long timeStart = null;

	public void JerkScoreCalculation() {

	}

	public void AccelerationIn(long time, float[] acc) {
		if (timeStart == null) {
			timeStart = time;
		}
		if (lastData != null) {

			float[] diff = new float[acc.length];
			for (int i = 0; i < acc.length; i++) {
				diff[i] = acc[i] - lastData[i];
				dataAccDerivative.add((float) Math.pow(Math.pow(diff[0], 2.0) + Math.pow(diff[1], 2.0) + Math.pow(diff[2], 2.0), .5));
			}
		}
		lastData = new float[acc.length];
		for (int i = 0; i < acc.length; i++) {
			lastData[i] = acc[i];
		}
	}

	public float CalculateJerkSingle(long time, float amplitude) {
		times.add(Math.abs(timeStart-time));

		float sum = 0;
		for (Float f : dataAccDerivative) {
			sum += f;
		}
		sum = (float) (sum * (Math.pow((Math.abs(timeStart - time) / 100l), 3) / Math.pow(amplitude, 2)));
		sum *= .5;
		jerkScores.add((float) Math.pow(sum, .5));
		lastData = null;
		dataAccDerivative.clear();
		timeStart = null;
		return (float) Math.pow(sum, .5);
	}

	public float CalculateJerkAverage() {
		double sum = 0;
		for (float f : jerkScores) {
			sum += f;
		}
		return (float) (sum / jerkScores.size());
	}

	public ArrayList<Float> getAllJerks() {
		return jerkScores;
	}

	public ArrayList<Long> getTimes() {
		return times;
	}
}
