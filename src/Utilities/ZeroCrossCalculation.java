package Utilities;


import java.util.ArrayList;

/**
 * Created by matt2929 on 1/24/18.
 */

public class ZeroCrossCalculation {
	float[] lastF = null;
	float zeroCross = Long.valueOf(0);
	ArrayList<Float> zeroCrosses = new ArrayList<>();
	ArrayList<Long> times = new ArrayList<>();
	long startTime = -1l;
 	public ZeroCrossCalculation() {

	}

	public void dataIn(long time, float[] f) {
		if (lastF != null) {
			for (int i = 0; i < f.length; i++) {
				f[i] = f[i] - lastF[i];
			}
			for (int i = 0; i < f.length; i++) {
				if ((f[i] < 0 && lastF[i] > 0) || (f[i] > 0 && lastF[i] < 0)) {
					zeroCross += 1;
				}
			}
		} else {
			startTime=time;
			lastF = new float[f.length];
		}
		for (int i = 0; i < f.length; i++) {
			lastF[i] = f[i];
		}
	}

	public void endRep(Long time) {
 		times.add(Math.abs(startTime-time));
		zeroCrosses.add(zeroCross);
		zeroCross = 0L;
		lastF = null;
	}

	public Float calculateZeroCross() {
		return averageTime(zeroCrosses);
	}

	public Float averageTime(ArrayList<Float> floats) {
		Float sum = 0f;
		for (int i = 0; i < floats.size(); i++) {
			sum += floats.get(i);
		}
		return ((sum / floats.size()));
	}

	public ArrayList<Long> getTimes() {
		return times;
	}

	public ArrayList<Float> getZeroCrosses() {
		return zeroCrosses;
	}
}
