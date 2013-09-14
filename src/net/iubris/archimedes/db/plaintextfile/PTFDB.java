/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - maximilianus@gmail.com .
 * 
 * PTFDB.java is part of 'archimedes'.
 * 
 * 'archimedes' is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * 'archimedes' is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with 'archimedes'; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA
 ******************************************************************************/
/*
 * Massimiliano Leone - k0smik0@logorroici.org - 2009, GPL license
 * 
 * This is annotation for any class which can used as model 
 * for a plain text file database, rows based.
 * 
 * Tipically, something as:
 * 
 * ATTRIBUTE_1		ATTRIBUTE_2		ATTRIBUTE_3			
 * attr_1_value1	attr_2_value_1	attr_3_value_1
 * attr_1_value2	attr_2_value_2	attr_3_value_2
 * attr_1_value3	attr_2_value_3	attr_3_value_3
 * ..				..				..
 * 
 * So, exploiting Generics and Reflection, attribute order 
 * (such as columns position) can be automagically set via
 * some annotation value.
 * 
 * And this is: we have a "order" annotation, int type.
 * We specify "@PTFDB{position=1}" for field which we want as 
 * first column attribute, and so on.
 * 
 * And that's all folkies :D
 */
package net.iubris.archimedes.db.plaintextfile;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface PTFDB {
	
	public int position();

}
