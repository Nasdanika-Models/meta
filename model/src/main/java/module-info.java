import org.nasdanika.capability.CapabilityFactory;
import org.nasdanika.models.meta.capability.MetaEPackageResourceSetCapabilityFactory;

module org.nasdanika.models.meta {
	
	exports org.nasdanika.models.meta;
	exports org.nasdanika.models.meta.impl;
	exports org.nasdanika.models.meta.util;
//	exports org.nasdanika.models.meta.loader;
	
	requires transitive org.eclipse.emf.ecore;
	requires transitive org.eclipse.emf.common;
	requires transitive org.nasdanika.models.nxcore;
	
	provides CapabilityFactory with 
		MetaEPackageResourceSetCapabilityFactory;
	
}