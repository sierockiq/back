package com.quentin.sierocki.legume.back.domain.entity;

import com.quentin.sierocki.legume.back.exception.fonctionnal.FunctionnalException;
import com.quentin.sierocki.legume.back.globals.Constants;

public enum CommandStatus {
	IN_PROGRESS, CANCELED, DELIVERED;

	public static CommandStatus getStatusOrNull(String status) throws FunctionnalException {
		for (CommandStatus me : CommandStatus.values()) {
			if (me.name().equalsIgnoreCase(status))
				return me;
		}
		throw new FunctionnalException(Constants.STATUS_COMMAND_INCONNU,"CommandStatus->getStatusOrNull","Le status envoyé n'existe pas : " + status);
	}
}
