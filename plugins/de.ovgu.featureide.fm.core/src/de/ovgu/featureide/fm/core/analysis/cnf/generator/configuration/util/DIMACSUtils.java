package de.ovgu.featureide.fm.core.analysis.cnf.generator.configuration.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Random;

import de.ovgu.featureide.fm.core.analysis.cnf.CNF;
import de.ovgu.featureide.fm.core.io.dimacs.DimacsWriter;

public class DIMACSUtils {

	public final static String TEMPORARY_DIMACS_PATH = "temp.dimacs";

	public static void createTemporaryDimacs(CNF cnf) {
		// clear if one exists
		deleteTemporaryDimacs();
		final DimacsWriter dWriter = new DimacsWriter(cnf);
		final String dimacsContent = dWriter.write();
		writeContentToFile(TEMPORARY_DIMACS_PATH, dimacsContent);
	}

	public static void createTemporaryRandomizedDimacs(CNF cnf, Random random) {
		cnf = cnf.randomize(random);
		final DimacsWriter dWriter = new DimacsWriter(cnf);
		final String dimacsContent = dWriter.write();
		writeContentToFile(TEMPORARY_DIMACS_PATH, dimacsContent);
	}

	private static void deleteTemporaryDimacs() {
		final File dimacs = new File(TEMPORARY_DIMACS_PATH);
		try {
			Files.deleteIfExists(dimacs.toPath());
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void writeContentToFile(String path, String content) {
		try {
			final BufferedWriter writer = new BufferedWriter(new FileWriter(path));
			writer.write(content);
			writer.close();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

}