/**
 * Copyright (C) 2010-2016 Gordon Fraser, Andrea Arcuri and EvoSuite
 * contributors
 *
 * This file is part of EvoSuite.
 *
 * EvoSuite is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3.0 of the License, or
 * (at your option) any later version.
 *
 * EvoSuite is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with EvoSuite. If not, see <http://www.gnu.org/licenses/>.
 */
package org.evosuite.symbolic.vm.string;

import java.util.ArrayList;
import java.util.Collections;

import org.evosuite.symbolic.expr.Expression;
import org.evosuite.symbolic.expr.Operator;
import org.evosuite.symbolic.expr.str.StringMultipleExpression;
import org.evosuite.symbolic.expr.str.StringValue;
import org.evosuite.symbolic.vm.NonNullExpression;
import org.evosuite.symbolic.vm.ReferenceExpression;
import org.evosuite.symbolic.vm.SymbolicEnvironment;
import org.evosuite.symbolic.vm.SymbolicFunction;
import org.evosuite.symbolic.vm.SymbolicHeap;

public final class ReplaceFirst extends SymbolicFunction {

	private static final String REPLACE_FIRST = "replaceFirst";

	public ReplaceFirst(SymbolicEnvironment env) {
		super(env, Types.JAVA_LANG_STRING, REPLACE_FIRST,
				Types.STR_STR_TO_STR_DESCRIPTOR);
	}

	@Override
	public Object executeFunction() {

		// receiver
		NonNullExpression symb_receiver = this.getSymbReceiver();
		String conc_receiver = (String) this.getConcReceiver();
		// regex argument
		ReferenceExpression symb_regex = this.getSymbArgument(0);
		String conc_regex = (String) this.getConcArgument(0);
		// replacement argument
		ReferenceExpression symb_replacement = this.getSymbArgument(1);
		String conc_replacement = (String) this.getConcArgument(1);
		// return value
		String conc_ret_val = (String) this.getConcRetVal();
		ReferenceExpression symb_ret_val = this.getSymbRetVal();

		StringValue stringReceiverExpr = env.heap.getField(
				Types.JAVA_LANG_STRING, SymbolicHeap.$STRING_VALUE,
				conc_receiver, symb_receiver, conc_receiver);

		if (symb_regex instanceof NonNullExpression
				&& symb_replacement instanceof NonNullExpression) {

			NonNullExpression non_null_symb_regex = (NonNullExpression) symb_regex;
			StringValue regexExpr = env.heap.getField(Types.JAVA_LANG_STRING,
					SymbolicHeap.$STRING_VALUE, conc_regex,
					non_null_symb_regex, conc_regex);

			NonNullExpression non_null_symb_replacement = (NonNullExpression) symb_replacement;
			StringValue replacementExpr = env.heap.getField(
					Types.JAVA_LANG_STRING, SymbolicHeap.$STRING_VALUE,
					conc_replacement, non_null_symb_replacement,
					conc_replacement);

			if (symb_ret_val instanceof NonNullExpression) {
				NonNullExpression non_null_symb_ret_val = (NonNullExpression) symb_ret_val;

				StringMultipleExpression symb_value = new StringMultipleExpression(
						stringReceiverExpr, Operator.REPLACEFIRST, regexExpr,
						new ArrayList<Expression<?>>(Collections
								.singletonList(replacementExpr)),
						conc_ret_val);

				env.heap.putField(Types.JAVA_LANG_STRING,
						SymbolicHeap.$STRING_VALUE, conc_ret_val,
						non_null_symb_ret_val, symb_value);
			}

		}
		return symb_ret_val;
	}

}
