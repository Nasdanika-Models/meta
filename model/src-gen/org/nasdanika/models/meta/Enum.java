/**
 */
package org.nasdanika.models.meta;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Enum</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.nasdanika.models.meta.Enum#getLiterals <em>Literals</em>}</li>
 * </ul>
 *
 * @see org.nasdanika.models.meta.MetaPackage#getEnum()
 * @model
 * @generated
 */
public interface Enum extends Classifier {
	/**
	 * Returns the value of the '<em><b>Literals</b></em>' containment reference list.
	 * The list contents are of type {@link org.nasdanika.models.meta.EnumLiteral}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Literals</em>' containment reference list.
	 * @see org.nasdanika.models.meta.MetaPackage#getEnum_Literals()
	 * @model containment="true"
	 * @generated
	 */
	EList<EnumLiteral> getLiterals();

} // Enum
