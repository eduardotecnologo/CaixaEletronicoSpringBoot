package com.caixacore.observer;

import com.caixacore.model.Nota;

public interface CaixaEletronicoObserver {
	public void notificarSaque(Nota nota);
}
