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
