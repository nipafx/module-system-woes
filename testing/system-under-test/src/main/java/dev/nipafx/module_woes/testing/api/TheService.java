package dev.nipafx.module_woes.testing.api;

import dev.nipafx.module_woes.testing.internal.TheStringer;

public class TheService {

	private TheStringer delegate = new TheStringer();

	public String method(int number) {
		TheDoubler service = new TheDoubler();
		return delegate.doIt(service.somethingCool(number));
	}

}
