import java.util.*;

class Computations {

	// Computes P(X_ij=x|D=d)
	public static double conditionalProbabilityXijgD(Model M, int i, int j,
			int x, int d) {
		if (x == 0)
			return 1 - (M.getPXijeq1gD(i, j, d));
		else
			return M.getPXijeq1gD(i, j, d);
	}

	// Computes P(X=x|D=d)
	public static double conditionalProbabilityXgD(Model M, int[][] x, int d) {
		double result = 1.0;
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 12; j++) {
				result *= conditionalProbabilityXijgD(M, i, j, x[i][j], d);
			}
		}
		return result;
	}

	// Computes P(X=x,D=d)
	public static double jointProbabilityXD(Model M, int[][] x, int d) {
		return conditionalProbabilityXgD(M, x, d) * M.getPD(d);
	}

	// Computes P(X=x)
	public static double marginalProbabilityX(Model M, int[][] x) {
		double result = 0;
		for (int i = 0; i < 10; i++) {
			result += jointProbabilityXD(M, x, i);
		}
		return result;
	}

	// Computes P(D=d|X=x)
	public static double conditionalProbabilityDgX(Model M, int d, int[][] x) {
		return jointProbabilityXD(M, x, d) / marginalProbabilityX(M, x);
	}

	// Computes the most likely digit type for image x
	public static int classify(Model M, int[][] x) {
		double result = conditionalProbabilityDgX(M, 0, x);
		int toreturn = 0;
		for (int i = 1; i < 10; i++) {
			if (conditionalProbabilityDgX(M, i, x) > result) {
				result = conditionalProbabilityDgX(M, i, x);
				toreturn = i;
			}
		}
		return toreturn;
	}

	// Computes the most likely digit type for image x
	// as fast as possible
	public static int fastClassify(Model M, int[][] x) {
		double result = jointProbabilityXD(M, x, 0);
		int toreturn = 0;
		for (int i = 1; i < 10; i++) {
			if (jointProbabilityXD(M, x, i) > result) {
				result = jointProbabilityXD(M, x, i);
				toreturn = i;
			}
		}
		return toreturn;
	}
	
}