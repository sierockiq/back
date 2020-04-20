package com.quentin.sierocki.legume.back.domain.entity;

import com.quentin.sierocki.legume.back.exception.fonctionnal.FunctionnalException;
import com.quentin.sierocki.legume.back.globals.Constants;

public enum ProductStatus {
	AVAILABLE,UNAVAILABLE;
	
	public static ProductStatus getStatusOrNull(String status) throws FunctionnalException {
		for (ProductStatus me : ProductStatus.values()) {
			if (me.name().equalsIgnoreCase(status))
				return me;
		}
		throw new FunctionnalException(Constants.STATUS_PRODUCT_INCONNU,"ProductStatus->getStatusOrNull | ","Le status envoyé n'existe pas : " + status);
	}
	
}
