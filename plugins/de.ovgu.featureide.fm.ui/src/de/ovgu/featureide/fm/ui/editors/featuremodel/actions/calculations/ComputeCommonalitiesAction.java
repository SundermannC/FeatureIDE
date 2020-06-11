/* FeatureIDE - A Framework for Feature-Oriented Software Development
 * Copyright (C) 2005-2017  FeatureIDE team, University of Magdeburg, Germany
 *
 * This file is part of FeatureIDE.
 *
 * FeatureIDE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FeatureIDE is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with FeatureIDE.  If not, see <http://www.gnu.org/licenses/>.
 *
 * See http://featureide.cs.ovgu.de/ for further information.
 */
package de.ovgu.featureide.fm.ui.editors.featuremodel.actions.calculations;

import static de.ovgu.featureide.fm.core.localization.StringTable.COMPUTE_COMMONALITIES;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;

import de.ovgu.featureide.fm.core.FeatureModelAnalyzer;
import de.ovgu.featureide.fm.core.analysis.cnf.CNF;
import de.ovgu.featureide.fm.core.analysis.cnf.formula.FeatureModelFormula;
import de.ovgu.featureide.fm.core.analysis.ddnnf.Ddnnf;
import de.ovgu.featureide.fm.core.analysis.ddnnf.solver.ComparableDsharp;
import de.ovgu.featureide.fm.core.analysis.ddnnf.solver.IComparableSolver;
import de.ovgu.featureide.fm.core.base.IFeature;
import de.ovgu.featureide.fm.core.base.IFeatureModel;
import de.ovgu.featureide.fm.core.color.FeatureColor;
import de.ovgu.featureide.fm.core.color.FeatureColorManager;
import de.ovgu.featureide.fm.core.io.manager.IFeatureModelManager;
import de.ovgu.featureide.fm.ui.FMUIPlugin;
import de.ovgu.featureide.fm.ui.editors.featuremodel.actions.AFeatureModelAction;;

/**
 * Action to compute the commonality of all features and color them according to it
 *
 * @author Chico Sundermann
 */
public class ComputeCommonalitiesAction extends AFeatureModelAction {

	public static final String ID = "de.ovgu.featureide.computecommonalities";

	/**
	 * @param title
	 * @param id
	 * @param featureModelManager
	 */
	public ComputeCommonalitiesAction(IFeatureModelManager featureModelManager) {
		super(COMPUTE_COMMONALITIES, ID, featureModelManager);
		setImageDescriptor(FMUIPlugin.getDefault().getImageDescriptor("icons/thread_obj.gif"));
	}

	@Override
	public void run() {
		final IFeatureModel model = featureModelManager.getVarObject();
		final FeatureModelFormula formula = featureModelManager.getVariableFormula();
		final FeatureModelAnalyzer analyzer = formula.getAnalyzer();
		if (!FeatureColorManager.hasColorScheme(model, "Commonalities")) {
			FeatureColorManager.newColorScheme(model, "Commonalities");
			FeatureColorManager.setActive(model, "Commonalities");
		}
		if (!FeatureColorManager.isCurrentColorScheme(model, "Commonalities")) {
			FeatureColorManager.setActive(model, "Commonalities");
		}
		final CNF cnf = formula.getCNF();

		final IComparableSolver solver = new ComparableDsharp(8000); // TODO: fix memory
		try {
			solver.executeSolver(cnf, 60, true);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
		final Ddnnf ddnnf = new Ddnnf();
		ddnnf.readDdnnfFileAndSaveCoreDead(IComparableSolver.DDNNF_TEMP_PATH);
		final List<BigInteger> commonalities = ddnnf.getCommonalities();
		for (int i = 1; i <= commonalities.size(); i++) {
			final BigDecimal commonality =
				new BigDecimal(commonalities.get(i - 1)).divide(new BigDecimal(ddnnf.getNumberOfSolutions()), 2, RoundingMode.HALF_UP);
			final IFeature feature = model.getFeature(cnf.getVariables().getName(i));
			analyzer.getFeatureProperties(feature).setCommonality(commonality.doubleValue());
			FeatureColorManager.setColor(feature, selectFeatureColor(commonality));
			FeatureColorManager.notifyColorChange(feature);
		}

	}

	private FeatureColor selectFeatureColor(BigDecimal commonality) {
		if (commonality.compareTo(BigDecimal.ONE) == 0) {
			return FeatureColor.Dark_Green;
		}
		if (commonality.compareTo(BigDecimal.valueOf(0.75)) >= 0) {
			return FeatureColor.Light_Green;
		}

		if (commonality.compareTo(BigDecimal.valueOf(0.5)) >= 0) {
			return FeatureColor.Cyan;
		}
		if (commonality.compareTo(BigDecimal.valueOf(0.25)) >= 0) {
			return FeatureColor.Yellow;
		}
		if (commonality.compareTo(BigDecimal.ZERO) == 0) {
			return FeatureColor.Red;
		}
		return FeatureColor.Orange;

	}

}
