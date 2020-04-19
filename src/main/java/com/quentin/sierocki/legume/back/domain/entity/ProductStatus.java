package com.quentin.sierocki.legume.back.domain.entity;

import com.quentin.sierocki.legume.back.exception.fonctionnal.FunctionnalException;

public enum ProductStatus {
	AVAILABLE,UNAVAILABLE;
	
	public static ProductStatus getStatusOrNull(String status) throws FunctionnalException {
		for (ProductStatus me : ProductStatus.values()) {
			if (me.name().equalsIgnoreCase(status))
				return me;
		}
		throw new FunctionnalException("ProductStatus->getStatusOrNull | ","Le status envoyé n'existe pas : " + status);
	}
	
}
