package impl;

import test.sonar.api.MyService;

public abstract class AbstractService implements MyService {
	public String echo(String txt) {
		return "echo " + txt;
	}

	public abstract String echoReverse(String txt);

}
