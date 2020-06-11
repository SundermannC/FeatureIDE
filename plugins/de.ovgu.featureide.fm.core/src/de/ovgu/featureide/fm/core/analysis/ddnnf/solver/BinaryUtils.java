package de.ovgu.featureide.fm.core.analysis.ddnnf.solver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class BinaryUtils {

	public static BinaryResult runBinary(String command, long timeout) {
		final Runtime rt = Runtime.getRuntime();
		try {
			final Process ps = rt.exec(command);
			try {
				if (!ps.waitFor(timeout, TimeUnit.MILLISECONDS)) {
					ps.destroy();
					return new BinaryResult("", SolverStatus.TIMEOUT);
				}
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}

			String val = "";
			String line;

			final BufferedReader in = new BufferedReader(new InputStreamReader(ps.getInputStream()));
			while ((line = in.readLine()) != null) {
				val += line + "\n";
			}
			final BufferedReader err = new BufferedReader(new InputStreamReader(ps.getErrorStream()));
			while ((line = err.readLine()) != null) {
				val += line + "\n";
			}
			in.close();
			return new BinaryResult(val, SolverStatus.SOLVED);
		} catch (final IOException e) {
			return null;
		}
	}

}
